package com.joviroa.url_shortener.dto;

import java.io.Serializable;
import java.util.Date;

import com.joviroa.url_shortener.entity.UrlEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlDTO implements Serializable {
    private Long id;
    private String url;
    private String shortCode;
    private Date createdAt;
    private Date updatedAt;
    private Long accessCount;

    public UrlDTO(UrlEntity entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.shortCode = entity.getShortCode();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.accessCount = entity.getAccessCount();
    }

}
