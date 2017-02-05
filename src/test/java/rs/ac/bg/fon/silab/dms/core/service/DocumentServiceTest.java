/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.service;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.repository.DocumentRepository;

/**
 *
 * @author stefan
 */
public class DocumentServiceTest {

    @Test(expected = DMSErrorException.class)
    public void getDocument_documentNotExists_throwBadRequestException() throws DMSErrorException {
        Long documentId = 12l;
        DocumentRepository documentRepositoryMock = mock(DocumentRepository.class);
        
        when(documentRepositoryMock.findOne(documentId)).thenReturn(null);
        
        DocumentService testee = new DocumentService(documentRepositoryMock, null);
        testee.getDocument(documentId);
    }
}
