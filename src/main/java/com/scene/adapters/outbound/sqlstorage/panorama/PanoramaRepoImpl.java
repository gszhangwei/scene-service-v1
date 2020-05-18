package com.scene.adapters.outbound.sqlstorage.panorama;

import com.scene.domain.panorama.PanoramaRepository;
import com.scene.domain.panorama.Panorama;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class PanoramaRepoImpl implements PanoramaRepository {

    private final PanoramaJpaRepo panoramaJpaRepo;

    public PanoramaRepoImpl(@Autowired PanoramaJpaRepo panoramaJpaRepo) {
        this.panoramaJpaRepo = panoramaJpaRepo;
    }

    @Override
    public Panorama save(Panorama panorama) {
        return panoramaJpaRepo.save(PanoramaPO.of(panorama)).toDomainObject();
    }

    @Override
    public Panorama findById(long id) {
        return Objects.requireNonNull(panoramaJpaRepo.findById(id).orElse(null)).toDomainObject();
    }
}
