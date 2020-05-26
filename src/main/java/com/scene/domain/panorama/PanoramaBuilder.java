package com.scene.domain.panorama;

import com.scene.domain.core.Builder;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PanoramaBuilder implements Builder<Panorama> {
    public abstract String getName();
    public abstract List<? extends SceneBuilder> getSceneBuilders();

    @Override
    public Panorama build() {
        return new Panorama(
                getName(),
                getSceneBuilders().stream().map(SceneBuilder::build).collect(Collectors.toList())
        );
    }
}
