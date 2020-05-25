package com.scene.domain.panorama;

import static java.util.Collections.emptyMap;
import java.util.Map;

public class SceneBuilder {
    private String name;
    private SceneType type;
    private Boolean isDefault;
    private Map<String, Photo> photos;

    public static SceneBuilder sceneBuilder() {
        return new SceneBuilder();
    }

    private SceneBuilder() {
        this.photos = emptyMap();
    }

    public SceneBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public SceneBuilder withType(SceneType type) {
        this.type = type;
        return this;
    }

    public SceneBuilder isDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public SceneBuilder withPhoto(String key, Photo photo) {
        this.photos.put(key, photo);
        return this;
    }

    public Scene build() {
        return new Scene(name, type, isDefault, photos);
    }
}
