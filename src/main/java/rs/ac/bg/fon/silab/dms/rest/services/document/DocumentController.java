/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.document;

import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.*;
import rs.ac.bg.fon.silab.dms.core.service.ActivityService;
import rs.ac.bg.fon.silab.dms.core.service.DocumentService;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentDescriptorRequest;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentRequest;
import rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentResponse;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import static rs.ac.bg.fon.silab.dms.rest.services.document.dto.DocumentResponse.getDocumentResponseList;

@RestController
@RequestMapping("/documents")
public class DocumentController {


    private static final String UPLOADS_DIRECTORY = "uploads";

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    
    @PostMapping
    public ResponseEntity create(@RequestHeader("X-Authorization") String token, @RequestBody DocumentRequest documentRequest) throws DMSErrorException {
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);

        Document document = documentService.createDocument(createDocumentFromRequest(documentRequest, authenticatedUser.getCompany()));
        List<DocumentDescriptorAssociation> associations = createDescriptorsAssotiations(document, documentRequest);
        documentService.addDescriptors(associations);
        document.setDocumentDescriptorAssociationList(associations);

        DocumentResponse documentResponse = new DocumentResponse(document);
        return ResponseEntity.ok(createSuccessResponse(documentResponse));
    }

    @GetMapping
    public ResponseEntity getAll(@RequestHeader("X-Authorization") String token,
            @RequestParam(name = "search_expression", required = false, defaultValue = "") String searchExpression,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);
        List<DocumentES> documents = documentService.getAllFor(authenticatedUser.getCompany().getId(), searchExpression, new PageRequest(page, size));
        return ResponseEntity.ok(createSuccessResponse(documents));
    }

    @GetMapping(value = "/activities/{activityId}")
    public ResponseEntity getAllForActivity(@PathVariable("activityId") Long activityId) {

        List<Document> allInputDocumentsForActivity = documentService.getAllInputDocumentsForActivity(activityId);
        List<Document> allOutputDocumentsForActivity = documentService.getAllOutputDocumentsForActivity(activityId);


        Map<String, List<DocumentResponse>> response = new HashMap<>();
        response.put("inputs", getDocumentResponseList(allInputDocumentsForActivity));
        response.put("outputs", getDocumentResponseList(allOutputDocumentsForActivity));
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getOne(@RequestHeader("X-Authorization") String token, @PathVariable("id") Long id) {
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);
        Document document = documentService.getDocument(id);

        if (!document.getDocumentType().getCompany().equals(authenticatedUser.getCompany())) {
            throw new DMSErrorException("Your company does not have specified document.");
        }

        DocumentResponse documentResponse = new DocumentResponse(document);
        return ResponseEntity.ok(createSuccessResponse(documentResponse));
    }


    @RequestMapping(value = "/file/{fileID}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("fileID") String fileName,
            HttpServletResponse response) {
        try {
            String filepath = Paths.get(UPLOADS_DIRECTORY, getFullFileName(fileName)).toString();
            InputStream inputStream = new FileInputStream(filepath);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.setContentType("application/pdf");
            response.flushBuffer();
        } catch (IOException e) {
            throw new DMSErrorException("File doesn't exist.");
        }
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam(name = "document", required = false) MultipartFile uploadfile) {
        if (uploadfile == null) {
            throw new DMSErrorException("Please provide a file.");
        }
        String filename = uploadfile.getOriginalFilename();
        try {
            String directory = "uploads";
            String filepath = Paths.get(directory, filename).toString();

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        } catch (Exception e) {
            throw new DMSErrorException(e.getMessage());
        }

        return ResponseEntity.ok((createSuccessResponse(filename)));
    }

    private Document createDocumentFromRequest(DocumentRequest documentRequest, Company company) {
        Document document = new Document();
        DocumentType documentType = documentTypeService.getDocumentType(documentRequest.getId());

        if (!company.equals(documentType.getCompany())) {
            throw new DMSErrorException("Your company does not have specified document type.");
        }

        document.setDocumentType(documentType);
        document.setCompany(company);
        document.setFileName(documentRequest.getFileName());
        Activity activity = activityService.getActivity(documentRequest.getActivityId());

        if (documentRequest.isInput()) {
            document.setInputForActivity(activity);
        } else {
            document.setOutputForActivity(activity);
        }

        return document;
    }

    private List<DocumentDescriptorAssociation> createDescriptorsAssotiations(Document document, DocumentRequest documentRequest) throws DMSErrorException {
        boolean validDescriptors = document.getDocumentType().getDescriptors().stream()
                .map(Descriptor::getId)
                .collect(Collectors.toList())
                .containsAll(documentRequest.getDescriptors().stream()
                        .map(DocumentDescriptorRequest::getId)
                        .collect(Collectors.toList()));
        if (!validDescriptors) {
            throw new DMSErrorException("Invalid descriptors.");
        }
        return documentRequest.getDescriptors().stream()
                .map(e -> new DocumentDescriptorAssociation(
                        document, document.getDocumentType().getDescriptors().stream()
                        .filter(d -> d.getId().equals(e.getId()))
                        .findFirst().orElse(null), e.getValue())).collect(Collectors.toList());

    }

    private String getFullFileName(String fileName) throws IOException {
        Path path = Paths.get(UPLOADS_DIRECTORY);
        File fileStore = new File(path.toString());
        Optional<File> retval = Arrays.stream(fileStore.listFiles())
                .filter(file -> file.getName().startsWith(fileName + "."))
                .findFirst();

        if (!retval.isPresent()) {
            throw new DMSErrorException("No such file.");
        }
        return retval.get().getName();
    }

}
