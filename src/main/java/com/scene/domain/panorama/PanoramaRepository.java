package com.scene.domain.panorama;

import com.scene.domain.core.Repository;
import org.springframework.stereotype.Component;

@Component
public interface PanoramaRepository extends Repository {
    Panorama save(Panorama panorama);

    Panorama findById(long id);
}
