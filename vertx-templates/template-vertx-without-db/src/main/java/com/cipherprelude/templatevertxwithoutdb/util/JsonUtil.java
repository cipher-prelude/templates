package com.cipherprelude.templatevertxwithoutdb.util;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectMapper.DefaultTyping;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

public class JsonUtil {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enableDefaultTypingAsProperty(DefaultTyping.JAVA_LANG_OBJECT, "class");
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    }

    public static synchronized String createJsonFromObject(Object obj) throws JsonGenerationException,
            JsonMappingException, IOException {
        return mapper.writeValueAsString(obj);
    }

    public static synchronized <T> T createObjectFromString(String jsonString, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) mapper.readValue(jsonString, clazz);
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T> T createMapFromString(String jsonString) throws JsonParseException,
            JsonMappingException, IOException {
        return (T) mapper.readValue(jsonString, new TypeReference<HashMap<String, String>>() {
        });
    }

}
