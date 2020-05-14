package com.scene.adapters.outbound.sqlstorage.panorama;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.scene.domain.panorama.PhotoInfo;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class MapConverter implements AttributeConverter<Map<String, PhotoInfo>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, PhotoInfo> attribute) {
        return JSONObject.toJSONString(attribute);
    }

    @Override
    public Map<String, PhotoInfo> convertToEntityAttribute(String dbData) {
        return JSON.parseObject(dbData, new TypeReference<Map<String, PhotoInfo>>(){});
    }
}
