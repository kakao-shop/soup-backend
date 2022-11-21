package com.kcs.soup.entity.mysql;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kcs.soup.api.admin.dto.BannerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
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

    private String path;

    private Long size;

    @Column(length = 20)
    private String contentType;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "theme_idx")
    private Theme theme;

    public static Banner of(MultipartFile image, Theme theme) throws IOException {
        String absolutePath = new File("").getAbsolutePath() + File.separator;
        String idx = String.valueOf(UUID.randomUUID());
        String extensionType = image.getContentType().split("image/")[1];
        String path = absolutePath + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "images" + File.separator + image.getOriginalFilename() + "_" + idx + "." + extensionType;
        File file = new File(path);
        file.mkdirs();
        image.transferTo(file);
        return Banner.builder()
                .idx(idx)
                .title(image.getOriginalFilename())
                .path(path)
                .size(image.getSize())
                .contentType(image.getContentType())
                .theme(theme)
                .build();
    }

    public BannerDto toBannerDto(){
        return BannerDto.builder()
                .title(this.title)
                .path(this.path)
                .contentType(this.contentType)
                .build();
    }

}
