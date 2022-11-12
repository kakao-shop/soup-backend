package com.example.soup.api.admin;

import com.example.soup.api.admin.dto.ThemeCategoryDto;
import com.example.soup.api.admin.dto.ThemeCreateRequest;
import com.example.soup.api.entity.mariadb.Member;
import com.example.soup.api.entity.mariadb.Theme;
import com.example.soup.api.entity.mariadb.ThemeCategory;
import com.example.soup.api.member.jwt.JwtTokenProvider;
import com.example.soup.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    private final ThemeRepository themeRepository;

    private final ThemeCategoryRepository themeCategoryRepository;

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public void createCollection(ThemeCreateRequest themeCreateRequest) {
        Theme theme = themeRepository.save(themeCreateRequest.toThemeEntity());
        for (ThemeCategoryDto collectionDto :
                themeCreateRequest.getCategoryList()) {
            themeCategoryRepository.save(collectionDto.toThemeCategoryEntity(theme));
        }
    }

    public List<Theme> findCollections() {
        return themeRepository.findAll();
    }

    public List<ThemeCategoryDto> findCollection(Long themeIdx) {
        return themeCategoryRepository.findByThemeIdx(themeIdx)
                .stream()
                .map(ThemeCategory::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCollection(Long themeIdx) {
        themeCategoryRepository.deleteAllByThemeIdx(themeIdx);
        themeRepository.deleteById(themeIdx);
    }

}
