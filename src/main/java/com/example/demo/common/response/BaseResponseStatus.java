package com.example.demo.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */

    USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),
    RESTRICTED_USER(false, HttpStatus.BAD_REQUEST.value(), "제한된 유저입니다."),
    DELETE_USER(false, HttpStatus.BAD_REQUEST.value(), "탈퇴한 유저입니다."),
    TEST_EMPTY_COMMENT(false, HttpStatus.BAD_REQUEST.value(), "코멘트를 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"중복된 이메일입니다."),
    POST_TEST_EXISTS_MEMO(false,HttpStatus.BAD_REQUEST.value(),"중복된 메모입니다."),
    EXIST_USER(false,HttpStatus.BAD_REQUEST.value(),"이미 존재하는 유저입니다."),
    INVALID_USER_INFO(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 유저 정보입니다."),
    NOT_FOUND_USER(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 유저입니다."),
    USER_ID_NULL(false, HttpStatus.BAD_REQUEST.value(), "유저 ID를 입력해주세요."),
    USER_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 유저입니다."),

    RESPONSE_ERROR(false, HttpStatus.NOT_FOUND.value(), "값을 불러오는데 실패하였습니다."),

    DUPLICATED_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "중복된 이메일입니다."),
    INVALID_MEMO(false,HttpStatus.NOT_FOUND.value(), "존재하지 않는 메모입니다."),
    FAILED_TO_LOGIN(false,HttpStatus.NOT_FOUND.value(),"없는 아이디거나 비밀번호가 틀렸습니다."),
    EMPTY_JWT(false, HttpStatus.UNAUTHORIZED.value(), "JWT를 입력해주세요."),
    INVALID_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,HttpStatus.FORBIDDEN.value(),"권한이 없는 유저의 접근입니다."),
    NOT_FIND_USER(false,HttpStatus.NOT_FOUND.value(),"일치하는 유저가 없습니다."),
    NOT_ACITVE_USER(false,HttpStatus.FORBIDDEN.value(),"탈퇴한 유저입니다."),
    INVALID_OAUTH_TYPE(false, HttpStatus.BAD_REQUEST.value(), "알 수 없는 소셜 로그인 형식입니다."),
    POST_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 게시물입니다."),
    POST_DELETED(false, HttpStatus.FORBIDDEN.value(), "삭제된 게시물입니다."),
    POST_ID_NULL(false, HttpStatus.BAD_REQUEST.value(), "게시물 ID를 입력해주세요."),
    NOT_MATCH_USER(false, HttpStatus.FORBIDDEN.value(), "작성자가 아닙니다."),
    REPORT_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 신고입니다."),
    COMMENT_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 댓글입니다."),
    NOT_MATCH_PAYMENT(false, HttpStatus.FORBIDDEN.value(), "결제자가 아닙니다."),
    INVALID_PAYMENT(false, HttpStatus.BAD_REQUEST.value(), "결제 정보가 일치하지 않습니다."),
    ALREADY_EXIST_PAYMENT(false, HttpStatus.BAD_REQUEST.value(), "이미 존재하는 결제입니다."),
    NOT_FOUND_PAYMENT(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 결제입니다."),
    NOT_FOUND_SUBSCRIBE(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 구독입니다."),
    REPORT_ID_NULL(false, HttpStatus.BAD_REQUEST.value(), "신고 ID를 입력해주세요."),
    COMMENT_ID_NULL(false, HttpStatus.BAD_REQUEST.value(), "댓글 ID를 입력해주세요."),
    INVALID_PATCH_USER_REQ(false, HttpStatus.BAD_REQUEST.value(), "유저 수정 정보가 잘못되었습니다."),
    INVALID_POST_USER_INFO_REQ(false, HttpStatus.BAD_REQUEST.value(), "유저 정보가 잘못되었습니다."),
    NO_AUTHORITY(false, HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    FAILED_TO_POST_REPORT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "신고에 실패 하였습니다."),
    FAILED_TO_UPDATE_POST(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "게시물 수정에 실패 하였습니다."),
    FAILED_TO_POST(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "게시물 작성에 실패 하였습니다."),
    FAILED_TO_LIKE(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "좋아요에 실패 하였습니다."),
    FAILED_TO_DELETE_POST(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "게시물 삭제에 실패 하였습니다."),
    FAILED_TO_DELETE_COMMENT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "댓글 삭제에 실패 하였습니다."),
    FAILED_TO_PAYMENT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "결제에 실패 하였습니다."),
    FAILED_TO_CANCEL_PAYMENT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "결제 취소에 실패 하였습니다."),
    FAILED_TO_SUBSCRIBE(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "구독에 실패 하였습니다."),
    FAILED_TO_CANCEL_SUBSCRIBE(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "구독 취소에 실패 하였습니다."),
    FAILED_TO_POST_IMAGE(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미지 업로드에 실패 하였습니다."),
    FAILED_TO_POST_COMMENT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "댓글 작성에 실패 하였습니다."),
    FAILED_TO_UPDATE_COMMENT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "댓글 수정에 실패 하였습니다."),
    POST_DELETED_BY_REPORT(false, HttpStatus.FORBIDDEN.value(), "신고로 인해 게시물이 삭제되었습니다."),
    NEEDS_CONSENT(false, HttpStatus.FORBIDDEN.value(), "개인정보 동의가 필요합니다."),
    UNAUTHORIZED_USER(false, HttpStatus.UNAUTHORIZED.value(), "권한이 없는 유저입니다."),
    COMMENT_DELETED_BY_REPORT(false, HttpStatus.FORBIDDEN.value(), "신고로 인해 댓글이 삭제되었습니다."),
    COMMENT_DELETED(false, HttpStatus.FORBIDDEN.value(), "삭제된 댓글입니다."),
    INVALID_INPUT_VALUE(false, HttpStatus.BAD_REQUEST.value(), "입력값이 잘못되었습니다."),
    NO_COMMENT(false, HttpStatus.NOT_FOUND.value(), "댓글이 없습니다."),


    /**
     * 500 :  Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 복호화에 실패하였습니다."),


    MODIFY_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저네임 수정 실패"),
    DELETE_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저 삭제 실패"),
    MODIFY_FAIL_MEMO(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"메모 수정 실패"),

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
