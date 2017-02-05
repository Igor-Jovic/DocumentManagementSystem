/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.core.model.Document;


public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> getAllDocumentByCompanyId(Long companyId);

    List<Document> getAllDocumentByInputForActivityId(Long activityId);

    List<Document> getAllDocumentByOutputForActivityId(Long activityId);
}
