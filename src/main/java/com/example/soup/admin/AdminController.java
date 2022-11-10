package com.example.soup.admin;

import com.example.soup.admin.dto.ThemeCreateRequest;
import com.example.soup.common.dto.BaseResponse;
import com.example.soup.domain.entity.mariadb.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/members")
    public ResponseEntity<BaseResponse> findMembers(@PageableDefault(value = 20) Pageable pageable){
        Page<Member> members = adminService.findMembers(pageable);
        return ResponseEntity.ok(new BaseResponse(200,"회원 목록입니다.",members));
    }

    @PostMapping("/collections")
    public ResponseEntity<BaseResponse> createCollection(@RequestBody ThemeCreateRequest themeCreateRequest){
        adminService.createCollection(themeCreateRequest);
        return ResponseEntity.ok(new BaseResponse(200,"테마를 성공적으로 생성했습니다."));
    }
}
