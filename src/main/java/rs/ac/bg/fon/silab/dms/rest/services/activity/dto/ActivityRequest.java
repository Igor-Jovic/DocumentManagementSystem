package rs.ac.bg.fon.silab.dms.rest.services.activity.dto;

import rs.ac.bg.fon.silab.dms.util.StringUtils;

import static rs.ac.bg.fon.silab.dms.util.StringUtils.*;

/**
 * Created by igor on 1/18/17.
 */
public class ActivityRequest {
    public long parentProcessId;
    public String name;
    public long inputDocumentTypeId;
    public long outputDocumentTypeId;

    public boolean isValid() {
        return !isStringEmptyOrNull(name);
    }
}
