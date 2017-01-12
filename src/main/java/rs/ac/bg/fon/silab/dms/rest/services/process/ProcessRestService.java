/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.process;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.CompanyProcess;
import rs.ac.bg.fon.silab.dms.core.service.CompanyService;
import rs.ac.bg.fon.silab.dms.core.service.ProcessService;
import rs.ac.bg.fon.silab.dms.rest.services.process.dto.ProcessRequest;
import rs.ac.bg.fon.silab.dms.rest.services.process.dto.ProcessResponse;
import rs.ac.bg.fon.silab.dms.rest.services.process.dto.TreeResponse;

/**
 *
 * @author stefan
 */
@RestController
@RequestMapping("/processes")
public class ProcessRestService {

    @Autowired
    private ProcessService processService;

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<ProcessResponse> create(RequestEntity<ProcessRequest> request) throws BadRequestException {
        ProcessRequest processRequest = request.getBody();
        CompanyProcess companyProcess = processService.createProcess(createProcessFromRequest(processRequest));

        ProcessResponse processResponse = new ProcessResponse(companyProcess);
        return ResponseEntity.ok(processResponse);
    }

    @GetMapping
    public ResponseEntity<List<TreeResponse>> getAll() {
        List<CompanyProcess> companyProcesses = processService.getAllMainProcesses(); // should get Main Processes by company param 
        
        List<TreeResponse> treeResponses = companyProcesses.stream()
                .map(e -> new TreeResponse(e))
                .collect(Collectors.toList());
        return ResponseEntity.ok(treeResponses);
    }

    private CompanyProcess createProcessFromRequest(ProcessRequest processRequest) throws BadRequestException {
        CompanyProcess companyProcess = new CompanyProcess();
        Company company = companyService.getProcess(1l); // from logged user get company
        companyProcess.setCompany(company);
        companyProcess.setName(processRequest.getName());
        if (processRequest.getParentProcess() != null) {
            CompanyProcess parentProcess = processService.getProcess(processRequest.getParentProcess());
            if (parentProcess.isPrimitive()) {
                throw new BadRequestException("You can create only activity from primitive process.");
            }
            companyProcess.setParentProcess(parentProcess);
        }
        companyProcess.setPrimitive(processRequest.isPrimitive());
        return companyProcess;
    }

}
