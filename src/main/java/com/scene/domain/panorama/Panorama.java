package com.scene.domain.panorama;

import com.scene.domain.core.IAggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Panorama implements IAggregateRoot {
    @Nullable
    private Long id;
    @Nullable
    private String name;
    @NotNull
    private List<Scene> scenes;
    @Nullable
    private Boolean isDeleted;
    @Nullable
    private String panoramaUrl;
    @Nullable
    private Date createTime;
    @Nullable
    private Date updateTime;

    public Panorama update(Panorama updatedPanorama) {
        Date now = Date.from(Instant.now());
        Map<Long, Scene> originSceneMap = scenes.stream().collect(Collectors.toMap(Scene::getId, Function.identity()));
        Map<Long, Scene> updatingSceneMaps = updatedPanorama.scenes.stream().collect(Collectors.toMap(Scene::getId, Function.identity()));
        List<Scene> newAndUpdatedSceneList = originSceneMap.values()
                .stream()
                .filter(scene -> updatingSceneMaps.containsKey(scene.getId()))
                .map(scene -> scene.update(updatingSceneMaps.get(scene.getId()), now))
                .collect(Collectors.toList());
        List<Scene> deletedSceneList = originSceneMap.values()
                .stream()
                .filter(scene -> !updatingSceneMaps.containsKey(scene.getId()))
                .map(scene -> scene.delete(now))
                .collect(Collectors.toList());
        newAndUpdatedSceneList.addAll(deletedSceneList);
        return new Panorama(id, updatedPanorama.getName(), newAndUpdatedSceneList, isDeleted, panoramaUrl, createTime, now);
    }
}
