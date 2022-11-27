package com.kcs.soup.api.admin.controller;

import com.kcs.soup.api.admin.dto.ThemeCreateRequest;
import com.kcs.soup.api.admin.dto.ThemeFindResponse;
import com.kcs.soup.api.admin.dto.ThemesFindResponse;
import com.kcs.soup.api.admin.service.AdminService;
import com.kcs.soup.common.dto.BaseResponse;
import com.kcs.soup.common.dto.ErrorCode;
import com.kcs.soup.common.entity.mysql.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/members")
    public ResponseEntity<BaseResponse> findMembers() {
        List<Member> members = adminService.findMembers();
        return ResponseEntity.ok(new BaseResponse(200, "회원 목록입니다.", members));
    }

    @PostMapping("/collections")
    public ResponseEntity<BaseResponse> createCollection(
            @RequestPart(value = "image", required = false) MultipartFile file,
            @RequestPart(value = "themeReq") ThemeCreateRequest themeCreateRequest) {
        try {
            adminService.createCollection(themeCreateRequest, file);
        } catch (IndexOutOfBoundsException | IOException e) {
            return ResponseEntity.badRequest().body(new BaseResponse(ErrorCode.FailToSaveBanner));
        }
        return ResponseEntity.ok(new BaseResponse(200, "테마를 성공적으로 생성했습니다."));
    }

    @GetMapping("/collections")
    public ResponseEntity<BaseResponse> findCollections() {
        ThemesFindResponse result = new ThemesFindResponse(adminService.findCollections());
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

    @GetMapping("/collections/{themeIdx}")
    public ResponseEntity findCollection(@PathVariable("themeIdx") Long themeIdx) throws IOException {
        ThemeFindResponse result = adminService.findByIdx(themeIdx);
        return ResponseEntity.ok(new BaseResponse(200, "성공", result));
    }

    @DeleteMapping("/collections/{themeIdx}")
    public ResponseEntity<BaseResponse> deleteCollection(@PathVariable("themeIdx") Long themeIdx) {
        adminService.deleteCollection(themeIdx);
        return ResponseEntity.ok(new BaseResponse(200, "성공"));
    }
}
