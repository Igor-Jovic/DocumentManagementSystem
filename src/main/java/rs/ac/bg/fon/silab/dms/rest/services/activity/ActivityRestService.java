package rs.ac.bg.fon.silab.dms.rest.services.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Activity;
import rs.ac.bg.fon.silab.dms.core.model.CompanyProcess;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;
import rs.ac.bg.fon.silab.dms.core.service.ActivityService;
import rs.ac.bg.fon.silab.dms.core.service.DocumentTypeService;
import rs.ac.bg.fon.silab.dms.core.service.ProcessService;
import rs.ac.bg.fon.silab.dms.rest.model.ApiResponse;
import rs.ac.bg.fon.silab.dms.rest.services.activity.dto.ActivityRequest;
import rs.ac.bg.fon.silab.dms.rest.services.activity.dto.ActivityResponse;

@RestController
@RequestMapping("/activities")
public class ActivityRestService {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private ProcessService processService;

    @PostMapping
    public ResponseEntity createActivity(@RequestBody ActivityRequest activityRequest) throws BadRequestException {
        if (!activityRequest.isValid()) {
            throw new BadRequestException("Activity must have name, parent process, input and output document");
        }
        Activity activity = activityRequestToActivity(activityRequest);
        activityService.createActivity(activity);
        return ResponseEntity.ok(ApiResponse.createSuccessResponse(activityToActivityResponse(activity)));
    }

    private Activity activityRequestToActivity(ActivityRequest activityRequest) throws BadRequestException {
        DocumentType inputDocument = documentTypeService.getDocumentType(activityRequest.inputDocumentTypeId);
        DocumentType outputDocument = documentTypeService.getDocumentType(activityRequest.outputDocumentTypeId);
        CompanyProcess process = processService.getProcess(activityRequest.parentProcessId);

        return new Activity(inputDocument, outputDocument, process, activityRequest.name);
    }

    private ActivityResponse activityToActivityResponse(Activity activity) {
        return new ActivityResponse(activity.getName());
    }
}
