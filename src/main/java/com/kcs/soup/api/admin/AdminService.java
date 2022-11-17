package com.kcs.soup.api.admin;

import com.kcs.soup.api.admin.dto.ThemeCategoryDto;
import com.kcs.soup.api.admin.dto.ThemeCreateRequest;
import com.kcs.soup.api.entity.mariadb.Member;
import com.kcs.soup.api.entity.mariadb.Theme;
import com.kcs.soup.api.entity.mariadb.ThemeCategory;
import com.kcs.soup.api.member.repository.MemberRepository;
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

    @Transactional(readOnly = false)
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


    @Transactional(readOnly = false)
    public void deleteCollection(Long themeIdx) {
        themeCategoryRepository.deleteAllByThemeIdx(themeIdx);
        themeRepository.deleteById(themeIdx);
    }

}
