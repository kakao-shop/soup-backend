package com.example.soup.api.member.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
