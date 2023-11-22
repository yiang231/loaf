package com.xjdl.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 */
public class ObjectMapperUtils {
    private boolean createXmlMapper = false;

    public static ObjectMapperUtils xml() {
        return new ObjectMapperUtils().createXmlMapper(true);
    }

    public static ObjectMapperUtils json() {
        return new ObjectMapperUtils();
    }

    private ObjectMapperUtils createXmlMapper(boolean b) {
        this.createXmlMapper = b;
        return this;
    }

    public ObjectMapper build() {
        if (this.createXmlMapper) {
            return XmlMapper.builder().defaultUseWrapper(true).build();
        }
        return JsonMapper.builder().build();
    }
}
