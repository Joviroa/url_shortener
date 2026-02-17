package com.joviroa.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlRequest {

    @NotBlank(message = "Necessário informar a url.")
    private String url;

    private String shortCode;
}
