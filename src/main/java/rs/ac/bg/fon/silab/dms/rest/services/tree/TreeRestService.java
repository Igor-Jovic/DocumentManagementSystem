/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.tree;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.model.CompanyProcess;
import rs.ac.bg.fon.silab.dms.core.service.ProcessService;
import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;
import rs.ac.bg.fon.silab.dms.rest.services.tree.dto.TreeResponse;

/**
 *
 * @author stefan
 */
@RestController
@RequestMapping("/tree")
public class TreeRestService {

    @Autowired
    private ProcessService processService;

    @GetMapping
    public ResponseEntity getAll() {
        List<CompanyProcess> companyProcesses = processService.getAllMainProcesses(); // should get Main Processes by company param 
        
        List<TreeResponse> treeResponses = companyProcesses.stream()
                .map(e -> new TreeResponse(e))
                .collect(Collectors.toList());
        return ResponseEntity.ok(createSuccessResponse(treeResponses));
    }

}
