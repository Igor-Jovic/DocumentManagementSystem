/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.rest.services.global;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import rs.ac.bg.fon.silab.dms.core.model.CompanyES;
import rs.ac.bg.fon.silab.dms.core.model.UserES;
import rs.ac.bg.fon.silab.dms.core.service.CompanyService;
import rs.ac.bg.fon.silab.dms.core.service.UserService;

import static rs.ac.bg.fon.silab.dms.rest.model.ApiResponse.createSuccessResponse;

@RestController
@RequestMapping("/global")
public class GlobalSearchController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping
    public ResponseEntity getAll(@RequestParam(name = "search_expression", required = false, defaultValue = "") String searchExpression,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size_per_category", required = false, defaultValue = "4") int size) {
        List<UserES> users = userService.getByUsernameLike(searchExpression, new PageRequest(page, size));
        List<CompanyES> companies = companyService.getByNameOrDescription(searchExpression, new PageRequest(page, size));
        Map response = new HashMap(){{
            put("users", users);
            put("companies", companies);
        }};
        return ResponseEntity.ok(createSuccessResponse(response));
    }

}
