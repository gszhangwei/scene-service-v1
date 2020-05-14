package com.scene.adapters.outbound.sqlstorage.panorama;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface PanoramaJpaRepo extends JpaRepository<PanoramaPO, Long> {
    @Query(
            value = "select pa.* from ( select id from `panorama` where `name` like CONCAT('%', :name, '%') and is_deleted = 0 order by `update_time` desc limit :page, :size) p, `panorama` pa where p.id=pa.id",
            nativeQuery = true
    )
    @NotNull
    List<PanoramaPO> searchByName(@Param("name") @Nullable String name, @Param("page") int page, @Param("size") int size);

    @Query(
            value = "select count(1) from `panorama` where `name` like CONCAT('%', :name, '%') and is_deleted = 0",
            nativeQuery = true
    )
    long countByName(@Param("name") @Nullable String name);

    @Query(
            value = "select pa.* from ( select id from `panorama` where is_deleted = 0 order by `update_time` desc limit :page, :size) p, `panorama` pa where p.id=pa.id",
            nativeQuery = true
    )
    @NotNull
    List<PanoramaPO> findByPage(@Param("page") int page, @Param("size") int size);

    @Query("select pa from PanoramaPO pa where pa.panoramaUrl = :url and pa.isDeleted = 0")
    @NotNull
    List<PanoramaPO> findOneByUrl(@Param("url") @NotNull String url);

    @Query(
            value = "select count(1) from `panorama` where is_deleted = 0",
            nativeQuery = true
    )
    long countByPage();
}
