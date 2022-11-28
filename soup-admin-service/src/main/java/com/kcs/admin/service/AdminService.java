package com.kcs.admin.service;

import com.kcs.admin.dto.ThemeCategoryDto;
import com.kcs.admin.dto.request.ThemeCreateRequest;
import com.kcs.admin.dto.response.ThemeFindResponse;
import com.kcs.admin.reporitory.BannerRepository;
import com.kcs.admin.reporitory.MemberRepository;
import com.kcs.admin.reporitory.ThemeCategoryRepository;
import com.kcs.admin.reporitory.ThemeRepository;
import common.entity.mysql.Banner;
import common.entity.mysql.Member;
import common.entity.mysql.Theme;
import common.entity.mysql.ThemeCategory;
import common.exception.NoSuchThemeExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    private final ThemeRepository themeRepository;

    private final ThemeCategoryRepository themeCategoryRepository;

    private final BannerRepository bannerRepository;

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void createCollection(ThemeCreateRequest themeCreateRequest, MultipartFile image) throws IOException {
        Theme theme = themeRepository.save(Theme.from(themeCreateRequest.getTitle()));
        for (ThemeCategoryDto dto :
                themeCreateRequest.getCategoryList()) {
            themeCategoryRepository.save(ThemeCategory.of(dto.getMainCategory(), dto.getSubCategory(), theme));
        }
        if ((image != null) && (!image.isEmpty())) {
            bannerRepository.save(Banner.of(image, theme));
        }
    }

    public List<Theme> findCollections() {
        return themeRepository.findAll();
    }


    @Transactional(readOnly = false)
    public void deleteCollection(Long themeIdx) {
        themeRepository.deleteById(themeIdx);
    }

    public ThemeFindResponse findByIdx(Long themeIdx) {
        Theme theme = themeRepository.findById(themeIdx)
                .orElseThrow(NoSuchThemeExistException::new);
        return ThemeFindResponse.from(theme);
    }
}
