package com.example.soup.domain.admin;

import com.example.soup.domain.admin.dto.ThemeCategoryDto;
import com.example.soup.domain.admin.dto.ThemeCreateRequest;
import com.example.soup.domain.entity.mariadb.Member;
import com.example.soup.domain.entity.mariadb.Theme;
import com.example.soup.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    private final ThemeRepository themeRpository;

    private final ThemeCategoryRepository themeCategoryRepository;

    public Page<Member> findMembers(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members;
    }

    public void createCollection(ThemeCreateRequest themeCreateRequest) {
        Theme theme = themeRpository.save(themeCreateRequest.toThemeEntity());
        for (ThemeCategoryDto collectionDto :
                themeCreateRequest.getCategoryList()) {
            themeCategoryRepository.save(collectionDto.toThemeCategoryEntity(theme));
        }
    }
}
