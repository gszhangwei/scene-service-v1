package com.scene.domain.panorama;

import com.scene.domain.core.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PanoramaRepository extends Repository {
    Panorama save(Panorama panorama);

    Panorama findById(long id);
}
