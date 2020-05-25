package com.scene.domain.panorama;

import static java.util.Collections.emptyList;
import java.util.List;

public class PanoramaBuilder {
    private String name;
    private List<Scene> scenes;

    public static PanoramaBuilder panoramaBuilder() {
        return new PanoramaBuilder();
    }

    private PanoramaBuilder() {
        this.scenes = emptyList();
    }

    public PanoramaBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PanoramaBuilder withScene(Scene scene) {
        this.scenes.add(scene);
        return this;
    }

    public Panorama build() {
        return new Panorama(name, scenes);
    }
}

