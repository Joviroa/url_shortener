package com.joviroa.url_shortener.service;

import java.util.Optional;

import com.joviroa.url_shortener.dto.UrlDTO;
import com.joviroa.url_shortener.dto.UrlRequest;
import com.joviroa.url_shortener.entity.UrlEntity;
import com.joviroa.url_shortener.exception.UrlException;

public interface IUrlService {

    public Optional<UrlEntity> get(String shortCode) throws UrlException;

    public UrlDTO create(UrlRequest request) throws UrlException;

    public UrlDTO update(UrlRequest request) throws UrlException;

    public void delete(String shortCode) throws UrlException;

    public Integer getStatistics(String shortCode) throws UrlException;

}
