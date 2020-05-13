package com.scene.application;

import com.scene.domain.panorama.IPanoramaRepository;
import com.scene.domain.panorama.ISearchPanoramaRepository;
import com.scene.domain.panorama.Panorama;
import com.scene.domain.panorama.PanoramaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PanoramaApplicationService {

    private final IPanoramaRepository iPanoramaRepository;

    private final ISearchPanoramaRepository iSearchPanoramaRepository;

    private final PanoramaService panoramaService;

    private final String salt;

    public PanoramaApplicationService(IPanoramaRepository iPanoramaRepository, ISearchPanoramaRepository iSearchPanoramaRepository, @Value("${file.disclaimer}") String salt) {
        this.iPanoramaRepository = iPanoramaRepository;
        this.iSearchPanoramaRepository = iSearchPanoramaRepository;
        this.salt = salt;
        this.panoramaService = new PanoramaService(this.iPanoramaRepository, this.iSearchPanoramaRepository, salt);
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
