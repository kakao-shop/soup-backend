package com.example.soup.admin;

import com.example.soup.admin.dto.ThemeCategoryDto;
import com.example.soup.admin.dto.ThemeCreateRequest;
import com.example.soup.admin.dto.ThemeFindResponse;
import com.example.soup.admin.dto.ThemesFindResponse;
import com.example.soup.domain.entity.mariadb.Member;
import com.example.soup.domain.entity.mariadb.Theme;
import com.example.soup.domain.entity.mariadb.ThemeCategory;
import com.example.soup.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final MemberRepository memberRepository;

    private final ThemeRepository themeRepository;

    private final ThemeCategoryRepository themeCategoryRepository;

    public Page<Member> findMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public void createCollection(ThemeCreateRequest themeCreateRequest) {
        Theme theme = themeRepository.save(themeCreateRequest.toThemeEntity());
        for (ThemeCategoryDto collectionDto :
                themeCreateRequest.getCategoryList()) {
            themeCategoryRepository.save(collectionDto.toThemeCategoryEntity(theme));
        }
    }

    public ThemesFindResponse findCollections() {
        return new ThemesFindResponse(themeRepository.findAll());
    }

    public List<ThemeCategory> findCollection(Long themeIdx) {
        return themeCategoryRepository.findByThemeIdx(themeIdx);
    }

    @Transactional
    public void deleteCollection(Long themeIdx) {
        themeCategoryRepository.deleteAllByThemeIdx(themeIdx);
        themeRepository.deleteById(themeIdx);
    }
}
