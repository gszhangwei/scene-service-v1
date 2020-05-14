package com.scene.adapters.inbound.rest;

import com.scene.adapters.inbound.rest.input.PanoramaInputDTO;
import com.scene.adapters.inbound.rest.input.PanoramaUpdateInputDTO;
import com.scene.adapters.inbound.rest.output.PanoramaDetailOutputDTO;
import com.scene.adapters.inbound.rest.output.PanoramaOutputDTO;
import com.scene.adapters.inbound.rest.output.PanoramaSearchOutputDTO;
import com.scene.application.PanoramaApplicationService;
import com.scene.domain.panorama.Panorama;
import com.scene.domain.panorama.PanoramaService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@RestController
@RequestMapping(value = "/api/v1/panoramas")
public class PanoramaController {
    @Autowired
    private PanoramaApplicationService panoramaService;
    @Value("${server-domain}")
    private String urlPrefix;

    @PostMapping
    public String createPanorama(@Valid @RequestBody PanoramaInputDTO panoramaInputDTO) {
        return this.getUrlPrefix() + this.getPanoramaService().createPanorama(panoramaInputDTO.toDomainObject());
    }

    @PutMapping({"/{panoramaId}"})
    public String updatePanorama(@PathVariable("panoramaId") long panoramaId, @Valid @RequestBody PanoramaUpdateInputDTO panoramaUpdateInputDTO) {
        return this.getUrlPrefix() + this.getPanoramaService().updatePanorama(panoramaId, panoramaUpdateInputDTO.toDomainObject());
    }

    @GetMapping
    public PanoramaSearchOutputDTO searchPanoramaList(@RequestParam(required = false) String name, @RequestParam int pageNumber, @RequestParam int pageSize) {
        PanoramaService.PanoramaSearchResult panoramaSearchResult = panoramaService.searchPanoramas(name, pageNumber, pageSize);
        return new PanoramaSearchOutputDTO(panoramaSearchResult.getCount(),
                panoramaSearchResult.getPanoramas()
                        .stream()
                        .map(panorama -> PanoramaOutputDTO.of(panorama, urlPrefix))
                        .collect(Collectors.toList()));
    }

    @GetMapping({"/{url}"})
    public PanoramaDetailOutputDTO getByUrl(@PathVariable("url") String url) {
        Panorama panorama = panoramaService.get(url);
        if (Objects.isNull(panorama)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return PanoramaDetailOutputDTO.of(panorama);
    }

    @DeleteMapping({"/{panoramaId}"})
    public void deletePanorama(@PathVariable("panoramaId") long panoramaId) {
        this.getPanoramaService().deletePanorama(panoramaId);
    }
}
