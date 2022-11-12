package com.example.soup.api.admin;

import com.example.soup.api.admin.dto.ThemeCategoryDto;
import com.example.soup.api.admin.dto.ThemeCreateRequest;
import com.example.soup.api.admin.dto.ThemeFindResponse;
import com.example.soup.api.admin.dto.ThemesFindResponse;
import com.example.soup.api.entity.mariadb.Member;
import com.example.soup.common.dto.BaseResponse;
import com.example.soup.elastic.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final CollectionService collectionService;

    @GetMapping("/members")
    public ResponseEntity<BaseResponse> findMembers() {
        List<Member> members = adminService.findMembers();
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
        String themeTitle = collectionService.findTitleByIdx(themeIdx);
        List<ThemeCategoryDto> themeList = adminService.findCollection(themeIdx);
        ThemeFindResponse result = new ThemeFindResponse(themeTitle, themeList);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

    @DeleteMapping("/collections/{themeIdx}")
    public ResponseEntity<BaseResponse> deleteCollection(@PathVariable("themeIdx") Long themeIdx) {
        adminService.deleteCollection(themeIdx);
        return ResponseEntity.ok(new BaseResponse(200, "성공"));
    }
}
