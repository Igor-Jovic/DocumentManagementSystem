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
import rs.ac.bg.fon.silab.dms.core.repository.ProcessRepository;

/**
 *
 * @author stefan
 */
public class ProcessServiceTest {

    @Test(expected = DMSErrorException.class)
    public void getProcess_processNotExists_throwBadRequestException() throws DMSErrorException {
        Long processId = 12l;
        ProcessRepository processRepositoryMock = mock(ProcessRepository.class);
        
        when(processRepositoryMock.findOne(processId)).thenReturn(null);
        
        ProcessService testee = new ProcessService(processRepositoryMock);
        testee.getProcess(processId);
    }
    
}
