package com.example.soup.member.service;

import com.example.soup.common.exceptions.IdAlreadyExistException;
import com.example.soup.common.exceptions.NoSuchMemberExistException;
import com.example.soup.domain.entity.Member;
import com.example.soup.domain.entity.MemberTokenInfo;
import com.example.soup.member.repository.MemberRepository;
import com.example.soup.member.dto.request.LoginRequest;
import com.example.soup.member.dto.response.LoginResponse;
import com.example.soup.member.dto.request.MemberCreateRequest;
import com.example.soup.member.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder encoder;

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberAuthService memberAuthService;

    public Member createMember(MemberCreateRequest memberCreateRequest) {
        validateDuplicatedId(memberCreateRequest.getId());
        memberCreateRequest.setPassword(encoder.encode(memberCreateRequest.getPassword()));
        return memberRepository.save(memberCreateRequest.toEntity());
    }

    public void validateDuplicatedId(String id) {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isPresent()) {
            throw new IdAlreadyExistException();
        }
    }

    public LoginResponse login(LoginRequest request) {
        Member findMember = validateMemberExist(request.getId());
        if (!encoder.matches(request.getPassword(), findMember.getPassword())) {
            throw new NoSuchMemberExistException();
        }

        Map<String, Object> claims = Map.of("memberIdx", findMember.getMemberIdx(), "nickname", findMember.getNickname(), "role", findMember.getRole());
        String accessToken = jwtTokenProvider.createToken(claims);
        String refreshToken = UUID.randomUUID().toString();
        MemberTokenInfo memberTokenInfo = findMember.toMemberTokenInfoEntity(accessToken,refreshToken);
        memberAuthService.saveToken(memberTokenInfo);
        return memberTokenInfo.toLoginResponseDto();
    }

    private Member validateMemberExist(String id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(NoSuchMemberExistException::new);
        return findMember;
    }

}
