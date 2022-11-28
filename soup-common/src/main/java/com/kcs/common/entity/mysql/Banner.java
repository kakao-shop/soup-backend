package com.kcs.common.entity.mysql;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banner {

    @Id
    private String idx;

    private String title;

    @Column(length = 500000)
    private String path;

    private Long size;

    @Column(length = 20)
    private String contentType;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "theme_idx")
    private Theme theme;

    public static Banner of(MultipartFile image, Theme theme) throws IOException {
        String idx = String.valueOf(UUID.randomUUID());
        String photoImg = null;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] photoEncode = encoder.encode(image.getBytes());
        photoImg = new String(photoEncode, "UTF8");
        return Banner.builder()
                .idx(idx)
                .title(image.getOriginalFilename())
                .path(photoImg)
                .size(image.getSize())
                .contentType(image.getContentType())
                .theme(theme)
                .build();
    }

}
