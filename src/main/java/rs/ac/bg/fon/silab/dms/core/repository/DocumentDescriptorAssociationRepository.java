/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.silab.dms.core.model.DocumentDescriptorAssociation;
import rs.ac.bg.fon.silab.dms.core.model.DocumentDescriptorAssociationPK;

/**
 *
 * @author stefan
 */
public interface DocumentDescriptorAssociationRepository extends JpaRepository<DocumentDescriptorAssociation, DocumentDescriptorAssociationPK> {
    
}
