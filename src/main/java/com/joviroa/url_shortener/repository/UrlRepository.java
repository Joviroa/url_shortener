package com.joviroa.url_shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.joviroa.url_shortener.entity.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, Long>{

    public Optional<UrlEntity> findByShortCode(String shortCode);

    public void deleteByShortCode(String shortCode);

    @Query("SELECT u.accessCount FROM UrlEntity u WHERE u.shortCode = :shortCode")
    public Integer getStatsByShortCode(@Param("shortCode") String shortCode);

    public boolean existsByShortCode(String shortCode);
}
