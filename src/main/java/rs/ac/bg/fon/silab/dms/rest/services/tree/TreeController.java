/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.service.ProcessService;
import rs.ac.bg.fon.silab.dms.rest.services.tree.dto.TreeResponse;
import rs.ac.bg.fon.silab.dms.security.TokenAuthenticationService;

import java.util.List;
import java.util.stream.Collectors;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;

@RestController
@RequestMapping("/tree")
public class TreeController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @GetMapping
    public ResponseEntity getAll(@RequestHeader("X-Authorization") String token) {
        User authenticatedUser = tokenAuthenticationService.getAuthenticatedUser(token);

        List<TreeResponse> treeResponses = processService.getAllMainProcessesForCompany(authenticatedUser.getCompany().getId()).stream()
                .filter(companyProcess -> !companyProcess.isPrimitive())
                .map(TreeResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(createSuccessResponse(treeResponses));
    }

}
