package com.scene.adapters.inbound.rest.output;

import com.scene.domain.panorama.Scene;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SceneDetailOutputDTO {
    private long id;
    private String name;
    private String type;
    private boolean isDefault;
    private Map<String, PhotoDetailOutputDTO> photos;

    public static SceneDetailOutputDTO of(Scene scene) {
        Map<String, PhotoDetailOutputDTO> photoDetailOutputDTOMap = scene.getPhotos().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, photoMap -> PhotoDetailOutputDTO.of(photoMap.getValue())));
        return new SceneDetailOutputDTO(scene.getId(), scene.getName(), scene.getType().toString(), scene.getIsInitialShow(), photoDetailOutputDTOMap);
    }
}
