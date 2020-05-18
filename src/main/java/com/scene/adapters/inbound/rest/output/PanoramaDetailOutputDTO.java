package com.scene.adapters.inbound.rest.output;

import com.scene.domain.panorama.Panorama;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PanoramaDetailOutputDTO {
    private long id;
    private String name;
    private List<SceneDetailOutputDTO> scenes;

    public static PanoramaDetailOutputDTO of(Panorama panorama) {
        List<SceneDetailOutputDTO> sceneDetailOutputDTOS = panorama.getScenes().stream().filter(scene -> !scene.getIsDeleted()).map(SceneDetailOutputDTO::of).collect(Collectors.toList());
        return new PanoramaDetailOutputDTO(panorama.getId(), panorama.getName(), sceneDetailOutputDTOS);
    }
}
