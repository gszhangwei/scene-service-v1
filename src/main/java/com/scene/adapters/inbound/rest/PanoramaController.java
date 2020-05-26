package com.scene.adapters.inbound.rest;

import com.scene.adapters.inbound.rest.input.PanoramaInputDTO;
import com.scene.application.PanoramaApplicationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/panoramas")
public class PanoramaController {
    @Autowired
    private PanoramaApplicationService panoramaService;
    @Value("${server-domain}")
    private String urlPrefix;

    @PostMapping
    public String createPanorama(@Valid @RequestBody PanoramaInputDTO panoramaInputDTO) {
        return this.urlPrefix + this.panoramaService.create(panoramaInputDTO);
    }
}
