package com.kcs.soup.api.admin.service;

import com.kcs.soup.api.admin.dto.ThemeCategoryDto;
import com.kcs.soup.api.admin.dto.ThemeCreateRequest;
import com.kcs.soup.api.admin.dto.ThemeFindResponse;
import com.kcs.soup.api.admin.reporitory.BannerRepository;
import com.kcs.soup.api.admin.reporitory.ThemeCategoryRepository;
import com.kcs.soup.api.admin.reporitory.ThemeRepository;
import com.kcs.soup.api.member.repository.MemberRepository;
import com.kcs.soup.common.exceptions.NoSuchThemeExistException;
import com.kcs.soup.entity.mysql.Banner;
import com.kcs.soup.entity.mysql.Member;
import com.kcs.soup.entity.mysql.Theme;
import com.kcs.soup.entity.mysql.ThemeCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


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
            themeCategoryRepository.save(ThemeCategory.of(dto, theme));
        }
        if ((image != null) && (!image.isEmpty())) {
            bannerRepository.save(Banner.of(image, theme));
        }
    }

    public List<Theme> findCollections() {
        return themeRepository.findAll();
    }

    public List<ThemeCategoryDto> findCollection(Long themeIdx) {
        return themeCategoryRepository.findByThemeIdx(themeIdx)
                .stream()
                .map(ThemeCategoryDto::from)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = false)
    public void deleteCollection(Long themeIdx) {
        themeRepository.deleteById(themeIdx);
    }

    public ThemeFindResponse findByIdx(Long themeIdx) {
        return themeRepository.findById(themeIdx)
                .orElseThrow(NoSuchThemeExistException::new)
                .toDto();
    }
}
