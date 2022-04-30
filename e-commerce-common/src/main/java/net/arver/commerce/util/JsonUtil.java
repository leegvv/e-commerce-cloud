package net.arver.commerce.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

/**
 * JsonUtil.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public class JsonUtil {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMapper.class);

    /**
     * 公用mapper.
     */
    private static ObjectMapper mapper;

    /**
     * 禁用默认构造函数.
     */
    private JsonUtil() {
    }

    static {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    /**
     * 将对象转换为json字符串.
     *
     * @param obj 对象
     * @return json字符串
     */
    public static String toJson(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (final JsonProcessingException ex) {
            LOGGER.error("转换为json失败", ex);
            return null;
        }
    }

    /**
     * 将json字符串转换为对象.
     *
     * @param json json字符串
     * @param valueType 类型
     * @param <T> 类型
     * @return 返回对象
     */
    public static <T extends Object> T parse(final String json, final Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (final Exception ex) {
            LOGGER.error("json反序列化失败", ex);
            return null;
        }
    }
    /**
     * 将输入流转换为对象.
     *
     * @param in json输入流
     * @param valueType 类型
     * @param <T> 类型
     * @return 返回对象
     */
    public static <T extends Object> T parse(final InputStream in, final Class<T> valueType) {
        try {
            return mapper.readValue(in, valueType);
        } catch (final Exception ex) {
            LOGGER.error("json反序列化失败", ex);
            return null;
        }
    }

    /**
     * 将json字符串转换为对象.
     *
     * @param json json字符串
     * @param valueType 类型
     * @param <T> 类型
     * @return 返回对象
     */
    public static <T extends Object> T parse(final String json, final TypeReference<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (final Exception ex) {
            LOGGER.error("json反序列化失败", ex);
            return null;
        }
    }

    /**
     * 将输入流转换为对象.
     *
     * @param in json输入流
     * @param valueType 类型
     * @param <T> 类型
     * @return 返回对象
     */
    public static <T extends Object> T parse(final InputStream in, final TypeReference<T> valueType) {
        try {
            return mapper.readValue(in, valueType);
        } catch (final Exception ex) {
            LOGGER.error("json反序列化失败", ex);
            return null;
        }
    }
}