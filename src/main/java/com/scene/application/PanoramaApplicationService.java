package com.scene.application;

import com.scene.domain.panorama.PanoramaRepository;
import com.scene.domain.panorama.SearchPanoramaRepository;
import com.scene.domain.panorama.Panorama;
import com.scene.domain.panorama.PanoramaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PanoramaApplicationService {

    private final PanoramaRepository panoramaRepository;

    private final SearchPanoramaRepository searchPanoramaRepository;

    private final PanoramaService panoramaService;

    private final String salt;

    public PanoramaApplicationService(PanoramaRepository panoramaRepository, SearchPanoramaRepository searchPanoramaRepository, @Value("${file.disclaimer}") String salt) {
        this.panoramaRepository = panoramaRepository;
        this.searchPanoramaRepository = searchPanoramaRepository;
        this.salt = salt;
        this.panoramaService = new PanoramaService(this.panoramaRepository, this.searchPanoramaRepository, salt);
    }

    @Transactional
    public String createPanorama(Panorama panorama) {
        return this.panoramaService.createPanorama(panorama);
    }

    public PanoramaService.PanoramaSearchResult searchPanoramas(String name, int pageNumber, int pageSize) {
        return this.panoramaService.searchPanoramas(name, pageNumber, pageSize);
    }

    public void deletePanorama(long panoramaId) {
        this.panoramaService.deletePanorama(panoramaId);
    }

    public String updatePanorama(long panoramaId, Panorama updatedPanorama) {
        return this.panoramaService.updatePanorama(panoramaId, updatedPanorama);
    }

    public Panorama get(String url) {
        return this.panoramaService.get(url);
    }

}
