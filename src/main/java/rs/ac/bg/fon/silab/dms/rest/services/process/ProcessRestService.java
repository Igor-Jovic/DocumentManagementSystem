/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.CompanyProcess;
import rs.ac.bg.fon.silab.dms.core.service.CompanyService;
import rs.ac.bg.fon.silab.dms.core.service.ProcessService;
import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import rs.ac.bg.fon.silab.dms.rest.services.process.dto.ProcessRequest;
import rs.ac.bg.fon.silab.dms.rest.services.process.dto.ProcessResponse;

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
    public ResponseEntity create(@RequestBody ProcessRequest processRequest) throws BadRequestException {
        CompanyProcess companyProcess = processService.createProcess(createProcessFromRequest(processRequest));

        ProcessResponse processResponse = new ProcessResponse(companyProcess);
        return ResponseEntity.ok(createSuccessResponse(processResponse));
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
