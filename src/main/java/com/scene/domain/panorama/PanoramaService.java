package com.scene.domain.panorama;

import com.scene.domain.core.DomainService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hashids.Hashids;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PanoramaService implements DomainService {

    private final int ID_LENGTH = 7;

    private final PanoramaRepository panoramaRepository;

    private final String salt;

    public String createPanorama(Panorama panorama) {
        Panorama savedPanorama = panoramaRepository.save(panorama);
        Long id = savedPanorama.getId();
        String panoramaUrl = (new Hashids(this.salt, ID_LENGTH)).encode(id);
        savedPanorama.setPanoramaUrl(panoramaUrl);
        this.panoramaRepository.save(savedPanorama);
        return panoramaUrl;
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PanoramaSearchResult {
        private Long count;
        private List<Panorama> panoramas;
    }
}
