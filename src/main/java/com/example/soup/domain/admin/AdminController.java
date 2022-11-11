package com.example.soup.domain.admin;

import com.example.soup.domain.admin.dto.ThemeCreateRequest;
import com.example.soup.domain.admin.dto.ThemeFindResponse;
import com.example.soup.domain.admin.dto.ThemesFindResponse;
import com.example.soup.domain.common.dto.BaseResponse;
import com.example.soup.domain.entity.mariadb.Member;
import com.example.soup.domain.entity.mariadb.ThemeCategory;
import com.example.soup.elastic.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final SearchService searchService;

    @GetMapping("/members")
    public ResponseEntity<BaseResponse> findMembers(@PageableDefault(value = 30) Pageable pageable) {
        Page<Member> members = adminService.findMembers(pageable);
        return ResponseEntity.ok(new BaseResponse(200, "회원 목록입니다.", members));
    }

    @PostMapping("/collections")
    public ResponseEntity<BaseResponse> createCollection(@RequestBody ThemeCreateRequest themeCreateRequest) {
        adminService.createCollection(themeCreateRequest);
        return ResponseEntity.ok(new BaseResponse(200, "테마를 성공적으로 생성했습니다."));
    }

    @GetMapping("/collections")
    public ResponseEntity<BaseResponse> findCollections() {
        ThemesFindResponse result = adminService.findCollections();
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

    @GetMapping("/collections/{themeIdx}")
    public ResponseEntity<BaseResponse> findCollection(@PathVariable("themeIdx") Long themeIdx) {
        String themeTitle = searchService.findTitleByIdx(themeIdx);
        List<ThemeCategory> themeList = adminService.findCollection(themeIdx);
        ThemeFindResponse result = new ThemeFindResponse(themeTitle,themeList);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

    @DeleteMapping("/collections/{themeIdx}")
    public ResponseEntity<BaseResponse> deleteCollection(@PathVariable("themeIdx") Long themeIdx) {
        adminService.deleteCollection(themeIdx);
        return ResponseEntity.ok(new BaseResponse(200, "성공"));
    }


}
