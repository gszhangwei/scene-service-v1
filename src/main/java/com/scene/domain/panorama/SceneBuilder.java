package com.scene.domain.panorama;

import com.scene.domain.core.Builder;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SceneBuilder implements Builder<Scene> {
    public abstract String getName();
    public abstract SceneType getType();
    public abstract Boolean isDefault();
    public abstract Map<String, ? extends PhotoBuilder> getPhotoBuilders();

    @Override
    public Scene build() {
        return new Scene(
                getName(),
                getType(),
                isDefault(),
                getPhotoBuilders().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().build()))
        );
    }
}
