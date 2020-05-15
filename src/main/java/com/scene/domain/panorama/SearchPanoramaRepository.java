package com.scene.domain.panorama;

import com.scene.domain.core.Repository;
import org.springframework.stereotype.Component;

@Component
public interface SearchPanoramaRepository extends Repository {
    Panorama save(Panorama panorama);

    PanoramaService.PanoramaSearchResult findAllByNameAndIsDeletedFalse(String name, int pageNum, int pageSize);
}
