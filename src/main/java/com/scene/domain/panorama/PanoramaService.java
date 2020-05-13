package com.scene.domain.panorama;

import com.scene.domain.core.IDomainService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class PanoramaService implements IDomainService {

    private final int ID_LENGTH = 7;

    private String salt;

    @Autowired
    private IPanoramaRepository panoramaRepository;

    @Autowired
    private ISearchPanoramaRepository searchPanoramaRepository;

    public String createPanorama(Panorama panorama) {
        Panorama savedPanorama = panoramaRepository.save(panorama);
        Long id = savedPanorama.getId();
        String panoramaUrl = (new Hashids(this.salt, ID_LENGTH)).encode(id);
        savedPanorama.setPanoramaUrl(panoramaUrl);
        this.panoramaRepository.save(savedPanorama);
        return panoramaUrl;
    }

    public PanoramaSearchResult searchPanoramas(String name, int pageNumber, int pageSize) {
        if (isNotBlank(name)) {
            return searchPanoramaRepository.findAllByNameAndIsDeletedFalse(name, pageNumber, pageSize);
        }
        List<Panorama> panoramas = panoramaRepository.findByPage(calculateOffset(pageNumber, pageSize), pageSize);
        return new PanoramaSearchResult(panoramaRepository.countByPage(), panoramas);
    }

    public void deletePanorama(Long panoramaId) {
        Panorama panorama = panoramaRepository.findById(panoramaId);
        if (Objects.nonNull(panorama)) {
            panorama.setIsDeleted(true);
            panorama.getScenes().forEach(scene -> scene.setIsDeleted(true));
            panoramaRepository.save(panorama);
        }
    }

    public String updatePanorama(Long panoramaId, Panorama updatedPanorama) {
        Panorama panorama = panoramaRepository.findById(panoramaId).update(updatedPanorama);
        panoramaRepository.save(panorama);
        return panorama.getPanoramaUrl();
    }


    private int calculateOffset(int pageNumber, int pageSize) {
        int offset = pageSize * (pageNumber - 1);
        return Math.max(offset, 0);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class PanoramaSearchResult {
        private Long count;
        private List<Panorama> panoramas;
    }
}
