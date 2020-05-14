package com.scene.adapters.outbound.sqlstorage.panorama;

import com.scene.domain.panorama.IPanoramaRepository;
import com.scene.domain.panorama.Panorama;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class PanoramaRepoImpl implements IPanoramaRepository {

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

    @Override
    public List<Panorama> searchByName(String name, int page, int size) {
        return panoramaJpaRepo.searchByName(name, page, size)
                .stream()
                .map(PanoramaPO::toDomainObject)
                .collect(Collectors.toList());
    }

    @Override
    public long countByName(String name) {
        return panoramaJpaRepo.countByName(name);
    }

    @Override
    public List<Panorama> findByPage(int page, int size) {
        return panoramaJpaRepo.findByPage(page, size)
                .stream()
                .map(PanoramaPO::toDomainObject)
                .collect(Collectors.toList());
    }

    @Override
    public List<Panorama> findOneByUrl(String url) {
        return panoramaJpaRepo.findOneByUrl(url)
                .stream()
                .map(PanoramaPO::toDomainObject)
                .collect(Collectors.toList());
    }

    @Override
    public long countByPage() {
        return panoramaJpaRepo.countByPage();
    }
}
