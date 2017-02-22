package rs.ac.bg.fon.silab.dms.testUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonConverter {

    public static <T> T fromJson(String json, Class<T> targetClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, targetClass);
    }
}
