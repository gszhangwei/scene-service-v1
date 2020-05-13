package com.scene.domain.panorama;

import com.scene.domain.core.IAggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    private Long id;
    private String name;
    private List<Scene> scenes;
    private Boolean isDeleted;
    private String panoramaUrl;
    private Date createTime;
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
