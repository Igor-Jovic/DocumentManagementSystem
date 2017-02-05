package rs.ac.bg.fon.silab.dms.rest.services.activity.dto;

import static org.springframework.util.StringUtils.isEmpty;


public class ActivityRequest {
    public long parentProcessId;
    public String name;
    public long inputDocumentTypeId;
    public long outputDocumentTypeId;

    public boolean isValid() {
        return !isEmpty(name);
    }
}
