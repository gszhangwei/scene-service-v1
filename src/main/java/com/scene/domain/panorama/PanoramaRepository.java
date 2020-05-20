package com.scene.domain.panorama;

import com.scene.domain.core.Repository;

public interface PanoramaRepository extends Repository {
    Panorama save(Panorama panorama);

    Panorama findById(long id);
}
