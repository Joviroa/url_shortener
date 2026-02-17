package com.joviroa.url_shortener.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joviroa.url_shortener.dto.UrlDTO;
import com.joviroa.url_shortener.dto.UrlRequest;
import com.joviroa.url_shortener.entity.UrlEntity;
import com.joviroa.url_shortener.exception.UrlException;
import com.joviroa.url_shortener.service.IUrlService;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/shortener")
public class UrlController {

    @Autowired
    private IUrlService service;

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Serviço ativo.");
    }

    @GetMapping("/{shortCode}/redirect")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) throws UrlException {
        Optional<UrlEntity> result = service.get(shortCode);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UrlEntity entity = result.get();
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(entity.getUrl()))
                .build();
    }

    @GetMapping("{shortCode}")
    public ResponseEntity<UrlDTO> get(@PathVariable("shortCode") @NotBlank String shortCode) throws UrlException {
        return service.get(shortCode)
            .map(entity -> ResponseEntity.ok(new UrlDTO(entity)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UrlDTO> create(@RequestBody @Validated UrlRequest request) throws UrlException {
        UrlDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<UrlDTO> update(@RequestBody @Validated UrlRequest request) throws UrlException {
        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("{shortCode}")
    public ResponseEntity<Void> delete(@PathVariable("shortCode") @NotBlank String shortCode) throws UrlException {
        service.delete(shortCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<Integer> statistics(@PathVariable("shortCode") @NotBlank String shortCode) throws UrlException {
        Integer accessCount = service.getStatistics(shortCode);
        return ResponseEntity.ok(accessCount);
    }

}
