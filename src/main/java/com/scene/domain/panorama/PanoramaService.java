package com.scene.domain.panorama;

import com.scene.domain.core.DomainService;
import lombok.AllArgsConstructor;
import org.hashids.Hashids;

@AllArgsConstructor
public class PanoramaService implements DomainService {

    private final int ID_LENGTH = 7;

    private final PanoramaRepository panoramaRepository;

    private final String salt;

    public String create(PanoramaBuilder panoramaBuilder) {
        Panorama panorama = panoramaBuilder.build();
        panorama = panoramaRepository.save(panorama);
        panorama = panorama.withShortUrl(new Hashids(this.salt, ID_LENGTH).encode(panorama.getId()));
        panoramaRepository.save(panorama);
        return panorama.getShortUrl();
    }

}
