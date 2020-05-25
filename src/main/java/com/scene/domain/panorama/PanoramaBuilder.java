package com.scene.domain.panorama;

import com.scene.domain.core.Builder;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PanoramaBuilder implements Builder<Panorama> {
    abstract String getName();
    abstract List<SceneBuilder> getSceneBuilders();

    @Override
    public Panorama build() {
        return new Panorama(
                getName(),
                getSceneBuilders().stream().map(SceneBuilder::build).collect(Collectors.toList())
        );
    }
}
