/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dms.core.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.Descriptor;
import rs.ac.bg.fon.silab.dms.core.model.DocumentType;
import rs.ac.bg.fon.silab.dms.core.repository.DescriptorRepository;
import rs.ac.bg.fon.silab.dms.core.repository.DocumentTypeRepository;

/**
 * @author stefan
 */
public class DocumentTypeServiceTest {

    @Test(expected = BadRequestException.class)
    public void getDocumentType_documentTypeNotExists_throwBadRequestException() throws BadRequestException {
        Long documentTypeId = 12l;
        DocumentTypeRepository documentRepositoryMock = mock(DocumentTypeRepository.class);

        when(documentRepositoryMock.findOne(documentTypeId)).thenReturn(null);

        DocumentTypeService testee = new DocumentTypeService(documentRepositoryMock, null);
        testee.getDocumentType(documentTypeId);
    }

    @Test(expected = BadRequestException.class)
    public void validateDocumentType_documentTypeExists_throwBadRequestException() throws BadRequestException {
        Company adminCompany = new Company("testCompany");
        String name = "test document type";
        DocumentType documentType = new DocumentType(name, adminCompany);
        DocumentTypeRepository documentRepositoryMock = mock(DocumentTypeRepository.class);

        when(documentRepositoryMock.getByNameAndCompany(documentType.getName(), documentType.getCompany().getId())).thenReturn(documentType);

        DocumentTypeService testee = new DocumentTypeService(documentRepositoryMock, null);

        testee.validateDocumentType(documentType);
    }

    @Test
    public void createDocumentType_validDocumentType_saveDocumentType() throws BadRequestException {
        Company adminCompany = new Company("testCompany");
        String name = "test document type";
        DocumentType documentType = new DocumentType(name, adminCompany);
        List<Descriptor> descriptors = Arrays.asList(new Descriptor("date"), new Descriptor("total"));
        documentType.setDescriptors(descriptors);
        DocumentTypeRepository documentTypeRepositoryMock = mock(DocumentTypeRepository.class);
        DescriptorRepository descriptorRepositoryMock = mock(DescriptorRepository.class);

        when(documentTypeRepositoryMock.getByNameAndCompany(documentType.getName(), documentType.getCompany().getId())).thenReturn(null);

        DocumentTypeService testee = new DocumentTypeService(documentTypeRepositoryMock, descriptorRepositoryMock);
        testee.createDocumentType(documentType);

        for (Descriptor descriptor : descriptors) {
            verify(descriptorRepositoryMock, times(1)).save(descriptor);
        }
        verify(descriptorRepositoryMock, times(1)).flush();
        verify(documentTypeRepositoryMock, times(1)).saveAndFlush(documentType);
    }

    @Test(expected = BadRequestException.class)
    public void createDocumentType_documentTypeExists_doNotSaveDocumentType() throws BadRequestException {
        Company adminCompany = new Company("testCompany");
        String name = "test document type";
        DocumentType documentType = new DocumentType(name, adminCompany);
        List<Descriptor> descriptors = Arrays.asList(new Descriptor("date"), new Descriptor("total"));
        documentType.setDescriptors(descriptors);
        DocumentTypeRepository documentTypeRepositoryMock = mock(DocumentTypeRepository.class);
        DescriptorRepository descriptorRepositoryMock = mock(DescriptorRepository.class);

        when(documentTypeRepositoryMock.getByNameAndCompany(documentType.getName(), documentType.getCompany().getId()))
                .thenReturn(documentType);

        DocumentTypeService testee = new DocumentTypeService(documentTypeRepositoryMock, descriptorRepositoryMock);
        testee.createDocumentType(documentType);

        for (Descriptor descriptor : descriptors) {
            verify(descriptorRepositoryMock, never()).save(descriptor);
        }
        verify(descriptorRepositoryMock, never()).flush();
        verify(documentTypeRepositoryMock, never()).saveAndFlush(documentType);
    }

}
