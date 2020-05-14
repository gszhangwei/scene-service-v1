package com.scene.adapters.outbound.esstorage.panorama;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PanoramaEsRepo extends ElasticsearchRepository<PanoramaEPO, Long> {
    public Page<PanoramaEPO> findAllByNameAndIsDeletedFalse(String name, Pageable pageable);
}
