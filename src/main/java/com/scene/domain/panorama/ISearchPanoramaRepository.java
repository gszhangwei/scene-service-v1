package com.scene.domain.panorama;

import com.scene.domain.core.IRepository;
import org.springframework.stereotype.Component;

@Component
public interface ISearchPanoramaRepository extends IRepository {
    Panorama save(Panorama panorama);

    PanoramaService.PanoramaSearchResult findAllByNameAndIsDeletedFalse(String name, int pageNum, int pageSize);
}
