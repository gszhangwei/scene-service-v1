package com.scene.domain.panorama;

import com.scene.domain.core.DomainService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hashids.Hashids;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@AllArgsConstructor
public class PanoramaService implements DomainService {

    private final int ID_LENGTH = 7;

    private final PanoramaRepository panoramaRepository;

    private final SearchPanoramaRepository searchPanoramaRepository;

    private final String salt;

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

    public Panorama get(String url) {
        return panoramaRepository.findOneByUrl(url).get(0);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PanoramaSearchResult {
        private Long count;
        private List<Panorama> panoramas;
    }
}
