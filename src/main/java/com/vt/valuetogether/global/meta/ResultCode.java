package com.vt.valuetogether.global.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    // 성공 0번대
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다"),

    // 글로벌 1000번대
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1000, "알 수 없는 에러가 발생했습니다."),
    ACCESS_DENY(HttpStatus.FORBIDDEN, 1001, "접근 권한이 없습니다."),
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND, 1002, "해당 파일을 찾을 수 없습니다."),
    NULL_FILE_TYPE(HttpStatus.BAD_REQUEST, 1003, "해당 파일의 확장자를 찾을 수 없습니다."),

    // 유저 2000번대
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 2000, "존재하지 않는 유저입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 2001, "잘못된 토큰"),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, 2002, "만료된 토큰"),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 2003, "이메일 전송에 실패하였습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, 2004, "해당 이메일을 찾을 수 없습니다."),
    INVALID_USERNAME_PATTERN(HttpStatus.BAD_REQUEST, 2005, "username 형식에 맞지 않습니다."),
    INVALID_EMAIL_PATTERN(HttpStatus.BAD_REQUEST, 2006, "email 형식에 맞지 않습니다."),
    INVALID_PASSWORD_PATTERN(HttpStatus.BAD_REQUEST, 2007, "password 형식에 맞지 않습니다."),
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, 2008, "중복된 username 입니다."),
    UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, 2009, "인증 완료되지 않은 email 입니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, 2010, "인증코드가 일치하지 않습니다."),
    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, 2011, "올바르지 않은 로그인 접근입니다."),
    INVALID_PROFILE_IMAGE_FILE(HttpStatus.BAD_REQUEST, 2012, "이미지 파일만 업로드 가능합니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, 2013, "로그인이 필요합니다."),

    // 팀 3000번대
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, 3000, "존재하지 않는 팀입니다."),
    INVALID_BACKGROUND_COLOR_PATTERN(HttpStatus.BAD_REQUEST, 3001, "배경 색상 형식에 맞지 않습니다."),
    DUPLICATED_TEAM_NAME(HttpStatus.BAD_REQUEST, 3002, "중복된 팀 이름 입니다."),
    FORBIDDEN_TEAM_LEADER(HttpStatus.FORBIDDEN, 3003, "팀의 리더만 해당 작업을 수행할 수 있습니다."),
    NOT_FOUND_TEAM_ROLE(HttpStatus.NOT_FOUND, 3004, "teamRole 이 존재하지 않습니다."),
    FORBIDDEN_TEAM_ROLE(HttpStatus.FORBIDDEN, 3005, "팀의 멤버만 해당 작업을 수행할 수 있습니다."),
    NOT_FOUND_TEAM_MEMBER(HttpStatus.NOT_FOUND, 3006, "팀의 멤버가 아닙니다."),

    // 카테고리 4000번대
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, 4000, "존재하지 않는 카테고리 입니다."),

    // 카드 5000번대
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND, 5000, "카드 정보를 찾을 수 없습니다."),

    // 작업자 6000번대

    // 체크리스트 7000번대
    NOT_FOUND_CHECKLIST(HttpStatus.NOT_FOUND, 7000, "체크리스트를 찾을 수 없습니다."),
    // 할일 8000대
    NOT_FOUND_TASK(HttpStatus.NOT_FOUND, 8000, "할일 정보를 찾을 수 없습니다."),
    NULL_CONTENT(HttpStatus.BAD_REQUEST, 8001, "할일 내용을 작성해주셔야 합니다.");

    // 댓글 9000대

    private final HttpStatus status;
    private final Integer code;
    private final String message;
}
