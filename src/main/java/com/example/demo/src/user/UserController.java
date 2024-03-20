package com.example.demo.src.user;


import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.oauth.OAuthService;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.user.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


import static com.example.demo.common.response.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/app/users")
public class UserController {

    private final UserService userService;

    private final OAuthService oAuthService;

    private final JwtService jwtService;

    /**
     * 회원가입 API
     * [POST] /app/users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    @Operation(
        summary = "회원 가입 API"
        , description = "# 회원 가입 API 입니다. \n " + "## 이메일, 비밀번호, 이름 등 개인 정보를 입력하세요.")
    public BaseResponse<PostUserRes> createUser(@RequestBody @Valid PostUserReq postUserReq) {
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(USERS_EMPTY_EMAIL);
        }
        // 입력 받은 유저 정보를 통한 유효성 체크
        if (userService.validateJoinUser(postUserReq)) {
            return new BaseResponse<>(EXIST_USER);
        }

        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new BaseResponse<>(postUserRes);
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /app/users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    @Operation(
        summary = "회원 정보 조회 API by Email"
        , description = "# `Param`으로 조회할 회원의 email을 입력하세요. \n")
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        if(Email == null){
            List<GetUserRes> getUsersRes = userService.getUsers();
            return new BaseResponse<>(getUsersRes);
        }
        // Get Users
        List<GetUserRes> getUsersRes = userService.getUsersByEmail(Email);
        return new BaseResponse<>(getUsersRes);
    }

    /**
     * 회원 1명 조회 API
     * [GET] /app/users/:userId
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userId}") // (GET) 127.0.0.1:9000/app/users/:userId
    @Operation(
        summary = "회원 정보 조회 API by userId"
        , description = "# `Path Variable`으로 조회할 회원의 ID을 입력하세요. \n")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") @ExistUser Long userId) {
        GetUserRes getUserRes = userService.getUser(userId);
        return new BaseResponse<>(getUserRes);
    }



    /**
     * 유저정보변경 API
     * [PATCH] /app/users/:userId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}")
    @Operation(
        summary = "회원 정보 수정 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 `userId`를 입력 하고, `Request body`에 수정할 정보를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<String> modifyUserName(@PathVariable("userId") @ExistUser Long userId, @RequestBody @Valid PatchUserReq patchUserReq){
        Long jwtUserId = jwtService.getUserId();
        if(jwtUserId != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        userService.modifyUserName(userId, patchUserReq);

        String result = "수정 완료!!";
        return new BaseResponse<>(result);
    }

    /**
     * 유저정보삭제 API
     * [DELETE] /app/users/:userId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}")
    @Operation(
        summary = "회원 정보 삭제 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 `userId`를 입력 하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<String> deleteUser(@PathVariable("userId") @ExistUser Long userId){
        Long jwtUserId = jwtService.getUserId();
        if(jwtUserId != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        userService.deleteUser(userId);

        String result = "삭제 완료!!";
        return new BaseResponse<>(result);
    }

    /**
     * 로그인 API
     * [POST] /app/users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    @Operation(
        summary = "로그인 API"
        , description = "# 로그인 API입니다. `Request Body`로 `email`와 `password`을 입력 하세요."
    )
    public BaseResponse<PostLoginRes> logIn(@RequestBody @Valid PostLoginReq postLoginReq){
        // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
        // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.

        PostLoginRes postLoginRes = userService.logIn(postLoginReq);
        return new BaseResponse<>(postLoginRes);
    }


    /**
     * 유저 소셜 가입, 로그인 인증으로 리다이렉트 해주는 url
     * [GET] /app/users/auth/:socialLoginType/login
     * @return void
     */
    @Operation(
        summary = "소셜 로그인 리다이렉트 API"
        , description = "# 유저 소셜 가입, 로그인 인증 으로 리다이렉트 해주는 API입니다. `Path Variable`로 소셜 로그인 타입을 입력 하세요. \n" +
        " SocialLoginType은 `GOOGLE`, `NAVER`, `KAKAO` 중 하나를 입력하세요. "
    )
    @GetMapping("/auth/{socialLoginType}/login")
    public void socialLoginRedirect(@PathVariable(name="socialLoginType") String SocialLoginPath) throws IOException {
        SocialLoginType socialLoginType= SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.accessRequest(socialLoginType);
    }


    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginPath (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code API Server 로부터 넘어오는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 java 객체 (access_token, jwt_token, user_num 등)
     */
    @ResponseBody
    @GetMapping(value = "/auth/{socialLoginType}/login/callback")
    @Operation(
        summary = "Social Login API Server 요청에 의한 callback 을 처리 하는 API"
        , description = "# Social Login API Server 요청에 의한 callback 을 처리 하는 API입니다. \n "
        + "`Path Variable`로 소셜 로그인 타입과, `Param`으로 소셜 로그인 API 서버로부터 받은 코드를 입력 하세요.. \n" +
        " SocialLoginType은 `GOOGLE`, `NAVER`, `KAKAO` 중 하나를 입력하세요. "
    )
    public BaseResponse<GetSocialOAuthRes> socialLoginCallback(

            @PathVariable(name = "socialLoginType")  String socialLoginPath,
            @RequestParam(name = "code") String code) throws IOException, BaseException{
        log.info(">> 소셜 로그인 API 서버로부터 받은 code : {}", code);
        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes = oAuthService.oAuthLoginOrJoin(socialLoginType,code);
        return new BaseResponse<>(getSocialOAuthRes);
    }

    /**  개인 정보 동의 내역 갱신 API
     * [PATCH] /app/users/:userId/terms
     * @param userId : 갱신 할 유저의 아이디
     * @return BaseResponse<PostUserAgreeRes>
     */
    @ResponseBody
    @PatchMapping("/{userId}/terms")
    @Operation(
        summary = "개인 정보 동의 내역 갱신 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 `userId`를 입력 하고, `Request body`에 동의 관련 정보를 입력하세요."
        , security = @SecurityRequirement(name = "X-ACCESS-TOKEN")
    )
    public BaseResponse<PostUserAgreeRes> modifyUserAgree(@PathVariable("userId") @ExistUser Long userId, @RequestBody @Valid PostUserAgreeReq postUserAgreeReq){
        Long jwtUserId = jwtService.getUserId();
        if(jwtUserId != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        PostUserAgreeRes postUserAgreeRes = userService.modifyUserAgree(userId, postUserAgreeReq);
        return new BaseResponse<>(postUserAgreeRes);
    }

    /**  소셜 로그인 시, 개인 정보 입력 API
     * [POST] /app/users/:userId/info
     * @param userId : 갱신 할 유저의 아이디
     * @Param postUserInfoReq : 갱신 할 유저의 정보
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/{userId}/info")
    @Operation(
        summary = "소셜 로그인 시, 개인 정보 입력 API"
        , description = "# Path Variable`로 `userId`를 입력 하고, `Request body`에 회원 개인 정보를 입력하세요."
    )
    public BaseResponse<String> UserInfo(@PathVariable @ExistUser Long userId, @RequestBody @Valid PostUserInfoReq postUserInfoReq){
        userService.setUserInfo(userId, postUserInfoReq);
        String result = "입력이 완료되었습니다.";
        return new BaseResponse<>(result);
    }

    /** !관리자용! 테스트 위해 유저에게 어드민 권한 부여 하는 API
     * [GET] /app/users/admin/{userId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/admin/{userId}")
    @Operation(
        summary = "!관리자용! 테스트 위해 유저에게 어드민 권한 부여 하는 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 `userId`를 입력 하세요."
    )
    public BaseResponse<String> setAdmin(@PathVariable("userId") @ExistUser Long userId){
        jwtService.isUserValid(userId);
        userService.setAdmin(userId);
        String result = "어드민 권한 부여 완료!!";
        return new BaseResponse<>(result);
    }


    /** !관리자용! 회원 전체 조회 API
     * [GET] /app/users/admin
     * @return BaseResponse<GetAllUserRes>
     */
    @ResponseBody
    @GetMapping("/admin")
    @Operation(
        summary = "!관리자용! 회원 전체 조회 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Request body`에 조회할 회원의 정보를 입력하세요."
    )
    public BaseResponse<List<GetAllUserRes>> getAllUsers(@RequestBody @Valid GetAllUserReq getAllUserReq){
        userService.isAdmin(jwtService.getUserId());
        List<GetAllUserRes> userDetailForAdmin = userService.getUserDetailForAdmin(getAllUserReq);
        return new BaseResponse<>(userDetailForAdmin);
    }

    // !관리자용! 회원 정지 API
    @ResponseBody
    @PostMapping("/admin/{userId}/stop")
    @Operation(
        summary = "!관리자용! 회원 정지 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 `userId`를 입력 하세요."
    )
    public BaseResponse<String> stopUser(@PathVariable("userId") @ExistUser Long userId){
        userService.isAdmin(jwtService.getUserId());
        userService.stopUser(userId);
        String result = "정지 완료!!";
        return new BaseResponse<>(result);
    }


    // !관리자용! 회원 전체 정보 조회 API
    @ResponseBody
    @GetMapping("/admin/{userId}")
    @Operation(
        summary = "!관리자용! 회원 전체 정보 조회 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다. `Path Variable`로 `userId`를 입력 하세요"
    )
    public BaseResponse<GetUserAllDetailRes> getUserAllDetail(@PathVariable("userId") @ExistUser Long userId){
        userService.isAdmin(jwtService.getUserId());
        GetUserAllDetailRes userAllDetail = userService.getUserAllDetail(userId);
        return new BaseResponse<>(userAllDetail);
    }

    @ResponseBody
    @GetMapping("/admin/logs")
    @Operation(
        summary = "!관리자용! 회원 로그 조회 API"
        , description = "# Header에 `X-ACCESS-TOKEN`이 필요합니다."
    )
    public BaseResponse<String> getUserLogs(){
        userService.isAdmin(jwtService.getUserId());
        String result = "로그 조회 완료!!";
        return new BaseResponse<>(result);
    }
}
