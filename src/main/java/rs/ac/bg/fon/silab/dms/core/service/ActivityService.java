package rs.ac.bg.fon.silab.dms.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.core.model.Activity;
import rs.ac.bg.fon.silab.dms.core.repository.ActivityRepository;
import rs.ac.bg.fon.silab.dms.util.StringUtils;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity createActivity(Activity activity) {
        validateActivity(activity);
        return activityRepository.saveAndFlush(activity);
    }

    public Activity getActivity(Long id) throws BadRequestException {
        Activity activity = activityRepository.findOne(id);
        if (activity == null) {
            throw new BadRequestException("Activity does not exists.");
        }
        return activity;
    }

    private void validateActivity(Activity activity) {
        if (activity.getInputDocumentType() == null
                || activity.getOutputDocuments() == null
                || activity.getParentProcess() == null
                || StringUtils.isStringEmptyOrNull(activity.getName())) {
            throw new IllegalStateException("Activity is in a bad state.");
        }
    }

    public boolean checkIfExists(String name, Long processId) throws BadRequestException {
        return activityRepository.findByNameAndParentProcessId(name, processId) != null;
    }

}
