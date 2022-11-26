package com.kcs.soup.common.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // common error
    IllegalArguement(4000,"입력값이 올바르지 않습니다."),
    UnAuthenticatedUser(4000,"인증되지 않은 유저입니다."),
    UnAuthorizedUser(4000, "접근 권한이 없는 유저입니다."),

    // member error
    IdAlreadyExist(4001,"사용 중인 ID입니다."),
    IllegalBirthday(4001,"생년월일이 올바르지 않습니다."),
    PasswordConfirm(4001,"비밀번호가 일치하지 않습니다."),
    NoSuchMemberExist(4001, "로그인에 실패했습니다."),

    // jwt error
    NoAccessToken(4002,"jwt access token 값이 없습니다."),
    NoRefreshToken(4002, "jwt refresh token 값이 없습니다."),
    JwtValidation(4002, "jwt access token이 유효하지 않습니다."),
    ExpiredAccessJwt(4002,"jwt access token이 만료되었습니다." ),
    ExpiredRefreshJwt(4002, "jwt refresh token이 만료되었습니다."),

    // search error
    NoSuchThemeExist(4003,"해당 테마를 찾을 수 없습니다."),
    ElasticSearch(4003,"검색어에 해당하는 상품을 찾을 수 없습니다."),

    // admin error
    FailToSaveBanner(4004, "배너 저장에 실패했습니다."),

    // database error
    ElasticsearchStatus(4005,"Elasticsearch 연결 실패"),
    RedisStatus(4005, "Redis 연결 실패");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
