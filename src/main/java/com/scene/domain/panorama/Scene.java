package com.scene.domain.panorama;

import com.scene.domain.core.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Scene extends Entity<Long> {
    private Long id;
    private String name;
    private SceneType type;
    private Boolean isDefault;
    private Map<String, Photo> photos;

    public Scene(String name, SceneType type, Boolean isDefault, Map<String, Photo> photos) {
        this.name = name;
        this.type = type;
        this.isDefault = isDefault;
        this.photos = photos;
    }
}
