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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.model.CompanyProcess;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.ProcessService;
import rs.ac.bg.fon.silab.dms.core.service.UserService;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;

import rs.ac.bg.fon.silab.dms.rest.services.tree.dto.TreeResponse;

/**
 * @author stefan
 */
@RestController
@RequestMapping("/tree")
public class TreeRestService {

    @Autowired
    private ProcessService processService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getAll(@RequestHeader("X-Authorization") String token) {
        User authenticatedUser = userService.getAuthenticatedUser(token);

        List<TreeResponse> treeResponses = processService.getAllMainProcessesForCompany(authenticatedUser.getCompany().getId()).stream()
                .filter(companyProcess -> !companyProcess.isPrimitive())
                .map(e -> new TreeResponse(e))
                .collect(Collectors.toList());
        return ResponseEntity.ok(createSuccessResponse(treeResponses));
    }

}
