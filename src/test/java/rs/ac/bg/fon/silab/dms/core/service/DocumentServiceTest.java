/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.Document;
import rs.ac.bg.fon.silab.dms.core.model.DocumentES;
import rs.ac.bg.fon.silab.dms.core.repository.DocumentRepository;
import rs.ac.bg.fon.silab.dms.core.repository.es.DocumentESRepository;

public class DocumentServiceTest {

    @Test(expected = DMSErrorException.class)
    public void getDocument_documentNotExists_throwException() {
        Long documentId = 12L;
        DocumentRepository documentRepositoryMock = mock(DocumentRepository.class);

        when(documentRepositoryMock.findOne(documentId)).thenReturn(null);

        DocumentService testee = new DocumentService(documentRepositoryMock, null, null);
        testee.getDocument(documentId);
    }

    @Test
    public void getDocument_documentExists_returnsIt() {
        Long documentId = 1L;
        DocumentRepository documentRepositoryMock = mock(DocumentRepository.class);

        Document document = new Document();
        when(documentRepositoryMock.findOne(documentId)).thenReturn(document);

        DocumentService testee = new DocumentService(documentRepositoryMock, null, null);

        Document returnedDoc = testee.getDocument(documentId);

        assertEquals(document, returnedDoc);
    }

    @Test
    public void createDocument_returnsIt() {
        Document document = new Document();
        DocumentRepository documentRepositoryMock = mock(DocumentRepository.class);
        DocumentESRepository documentESRepositoryMock = mock(DocumentESRepository.class);
        
        when(documentRepositoryMock.saveAndFlush(document)).thenReturn(document);
        when(documentESRepositoryMock.save(Matchers.any(DocumentES.class))).thenReturn(new DocumentES(document));

        DocumentService testee = new DocumentService(documentRepositoryMock, null, documentESRepositoryMock);
        Document createdDocument = testee.createDocument(document);

        assertEquals(document, createdDocument);
    }
}