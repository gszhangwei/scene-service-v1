package com.scene.application;

import com.scene.domain.panorama.Panorama;
import com.scene.domain.panorama.PanoramaBuilder;
import com.scene.domain.panorama.PanoramaRepository;
import com.scene.domain.panorama.PanoramaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PanoramaApplicationService {

    private final PanoramaService panoramaService;

    public PanoramaApplicationService(PanoramaRepository panoramaRepository, @Value("${file.disclaimer}") String salt) {
        this.panoramaService = new PanoramaService(panoramaRepository, salt);
    }

    @Transactional
    public String create(PanoramaBuilder panoramaBuilder) {
        return this.panoramaService.create(panoramaBuilder);
    }

}
