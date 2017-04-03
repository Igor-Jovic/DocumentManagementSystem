package rs.ac.bg.fon.silab.dms.core.service;

import org.junit.Test;
import rs.ac.bg.fon.silab.dms.core.exception.DMSErrorException;
import rs.ac.bg.fon.silab.dms.core.model.Activity;
import rs.ac.bg.fon.silab.dms.core.repository.ActivityRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ActivityServiceTest {

    @Test
    public void getActivity_activityExists_returnsIt() {
        Activity activityRetvalMock = mock(Activity.class);
        ActivityRepository activityRepositoryMock = mock(ActivityRepository.class);
        when(activityRepositoryMock.findOne((long) 1)).thenReturn(activityRetvalMock);

        ActivityService testee = new ActivityService(activityRepositoryMock);

        Activity activity = testee.getActivity((long) 1);
        assertEquals(activityRetvalMock, activity);
    }

    @Test(expected = DMSErrorException.class)
    public void getActivity_activityDoesntExist_throwException() {
        ActivityRepository activityRepositoryMock = mock(ActivityRepository.class);
        when(activityRepositoryMock.findOne((long) 1)).thenReturn(null);

        ActivityService testee = new ActivityService(activityRepositoryMock);

        testee.getActivity((long) 1);
    }

}