/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.com.cn.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.springframework.util.StringUtils;

/**
 *
 * @author Lion Weng
 */
public class JsonUtils {

    public static String toJson(Object object) {
        String result = "";
        if (null == object) {
            return result;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            result = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Object fromJson(String requestStr, Class clazz) {
        if (StringUtils.isEmpty(requestStr)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Object object = null;
        try {
            object = objectMapper.readValue(requestStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    public static Object fromJson(String requestStr, Class collectionClazz, Class<?>... elementClazzes) {
        if (StringUtils.isEmpty(requestStr)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Object object = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);

            object = objectMapper.readValue(requestStr, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * JsonNode转换为Java泛型对象，可以是各种类型，此方法最为强大。用法看测试用例。
     *
     * @param <T>
     *            JsonNode
     * @param tr
     *            TypeReference,例如: new TypeReference< List<FamousUser> >(){}
     * @return List对象列表
     */
    public static <T> T jsonNode2GenericObject(String response, TypeReference<T> tr) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        // 当范序列化出现未定义字段时候，不出现错误
        DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
        deserializationConfig = deserializationConfig.without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        deserializationConfig = deserializationConfig.with(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.setConfig(deserializationConfig);
        if (response == null || "".equals(response)) {
            return null;
        }
        try {
            return (T) mapper.readValue(response, tr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    public static JsonNode getNode(String json, String nodeName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        // 当范序列化出现未定义字段时候，不出现错误
        DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
        deserializationConfig = deserializationConfig.without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        deserializationConfig = deserializationConfig.with(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.setConfig(deserializationConfig);
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
            return node.get(nodeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;
    }
}
