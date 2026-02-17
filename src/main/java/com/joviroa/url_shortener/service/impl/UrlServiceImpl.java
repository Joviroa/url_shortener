package com.joviroa.url_shortener.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joviroa.url_shortener.dto.UrlDTO;
import com.joviroa.url_shortener.dto.UrlRequest;
import com.joviroa.url_shortener.entity.UrlEntity;
import com.joviroa.url_shortener.exception.UrlException;
import com.joviroa.url_shortener.repository.UrlRepository;
import com.joviroa.url_shortener.service.IUrlService;
import com.joviroa.url_shortener.util.Base62;

@Service
public class UrlServiceImpl implements IUrlService {

    @Autowired
    private UrlRepository repository;

    @Override
    @Transactional
    public Optional<UrlEntity> get(String shortCode) {
        return repository.findByShortCode(shortCode).map(entity -> {
            entity.setAccessCount(entity.getAccessCount() + 1);
            return repository.save(entity);
        });
    }

    @Override
    @Transactional
    public UrlDTO create(UrlRequest request) {
        UrlEntity created = repository.save(new UrlEntity(request.getUrl()));
        created.setShortCode(Base62.fromBase10(created.getId()));
        return new UrlDTO(repository.save(created));
    }

    @Override
    @Transactional
    public UrlDTO update(UrlRequest request) throws UrlException {
        return repository.findByShortCode(request.getShortCode())
            .map(entity -> {
                entity.setUrl(request.getUrl());
                entity.setUpdatedAt(new Date());
                return new UrlDTO(repository.save(entity));
            })
            .orElseThrow(() -> new UrlException("Não foi possível atualizar: URL não encontrada."));
    }

    @Override
    @Transactional
    public void delete(String shortCode) throws UrlException {
        if(repository.existsByShortCode(shortCode)) {
            throw new UrlException("Não foi possível deletar: URL não encontrada.");
        }
        repository.deleteByShortCode(shortCode);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getStatistics(String shortCode) throws UrlException {
        return repository.getStatsByShortCode(shortCode);
    }

}
