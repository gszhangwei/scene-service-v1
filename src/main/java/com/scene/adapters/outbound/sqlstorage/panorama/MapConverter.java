package com.scene.adapters.outbound.sqlstorage.panorama;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.scene.domain.panorama.Photo;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class MapConverter implements AttributeConverter<Map<String, Photo>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Photo> attribute) {
        return JSONObject.toJSONString(attribute);
    }

    @Override
    public Map<String, Photo> convertToEntityAttribute(String dbData) {
        return JSON.parseObject(dbData, new TypeReference<Map<String, Photo>>(){});
    }
}
