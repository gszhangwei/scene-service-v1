package com.scene.adapters.outbound.esstorage.panorama;

import com.scene.domain.panorama.ISearchPanoramaRepository;
import com.scene.domain.panorama.Panorama;
import com.scene.domain.panorama.PanoramaService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Data
public class SearchPanoramaRepoImpl implements ISearchPanoramaRepository {

    @Autowired
    @NotNull
    private PanoramaEsRepo panoramaEsRepo;

    @Autowired
    @NotNull
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Panorama save(Panorama panorama) {
        this.indexExistOrCreate();
        return this.getPanoramaEsRepo().save(PanoramaEPO.of(panorama)).toDomainObject();
    }

    private void indexExistOrCreate() {
        if (!this.getElasticsearchTemplate().indexExists(PanoramaEPO.class)) {
            this.getElasticsearchTemplate().createIndex(PanoramaEPO.class);
            this.getElasticsearchTemplate().putMapping(PanoramaEPO.class);
        }

    }

    @Override
    public PanoramaService.PanoramaSearchResult findAllByNameAndIsDeletedFalse(String name, int pageNum, int pageSize) {
        Page<PanoramaEPO> panoramaEPOS = panoramaEsRepo.findAllByNameAndIsDeletedFalse(name, PageRequest.of(pageNum - 1, pageSize));
        List<Panorama> panoramas = panoramaEPOS.get()
                .map(PanoramaEPO::toDomainObject)
                .collect(Collectors.toList());
        return new PanoramaService.PanoramaSearchResult(panoramaEPOS.getTotalElements(), panoramas);
    }

}
