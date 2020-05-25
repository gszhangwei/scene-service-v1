package com.scene.domain.panorama;

import com.scene.domain.core.DomainService;
import lombok.AllArgsConstructor;
import org.hashids.Hashids;

@AllArgsConstructor
public class PanoramaService implements DomainService {

    private final int ID_LENGTH = 7;

    private final PanoramaRepository panoramaRepository;

    private final String salt;

    public String createPanorama(Panorama panorama) {
        Panorama savedPanorama = panoramaRepository.save(panorama);
        Long id = savedPanorama.getId();
        String panoramaUrl = (new Hashids(this.salt, ID_LENGTH)).encode(id);
        savedPanorama = savedPanorama.withShortUrl(panoramaUrl);
        this.panoramaRepository.save(savedPanorama);
        return panoramaUrl;
    }

}
