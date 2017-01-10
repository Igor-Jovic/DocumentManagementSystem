package rs.ac.bg.fon.silab.dms.util;

public class StringUtils {

    public static boolean isStringEmptyOrNull(String string) {
        return string == null || string.trim().isEmpty();
    }
}
