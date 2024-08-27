package com.ubio.stockdemo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtDto {
    private String token;
}
