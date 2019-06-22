/** 
 * (C) Copyright YOUWAWA Corporation, All Rights Reserved.
 */
package com.pactera.weather.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * <strong>json操作基类。</strong>
 *
 * @author yw
 */
public class JsonUtils {

    public static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    static {
        // 设置默认日期转化格式
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 如果json中的属性在bean中没有定义则忽略转换
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 如果bean是空的，没有任何属性，可以正常转换（转换为"{}"）
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * <strong>json字符串转bean对象。</strong>
     *
     * @param jsonStr
     * @param beanType
     * @return
     * @throws ErrorReturnException
     * @author yw
     */
    public static <T> T readJson2Bean(String jsonStr, Class<T> beanType) {

        try {
            return OBJECT_MAPPER.readValue(jsonStr, beanType);
        } catch (Exception e) {
        	logger.error("Parsing json error", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <strong>json字符串转bean对象集合。</strong>
     *
     * @param jsonStr
     * @param beanType
     * @return
     * @throws ErrorReturnException
     * @author yw
     */
    public static <T> ArrayList<T> readJson2BeanList(String jsonStr, Class<T> beanType) {

        try {
            TypeFactory typeFactory = TypeFactory.defaultInstance();

            return OBJECT_MAPPER.readValue(jsonStr, typeFactory.constructCollectionType(ArrayList.class, beanType));
        } catch (Exception e) {
            logger.error("Parsing json error", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <strong>bean对象转json。</strong>
     *
     * @param bean
     * @return json字符串
     * @throws ErrorReturnException
     * @author yw
     */
    public static String readBean2Json(Object bean) {

        try {
            return OBJECT_MAPPER.writeValueAsString(bean);
        } catch (Exception e) {
        	logger.error("Parsing json error", e);
            e.printStackTrace();
            return null;
        }
    }
}
