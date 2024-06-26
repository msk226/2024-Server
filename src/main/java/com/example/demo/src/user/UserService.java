package com.example.demo.src.user;



import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.Constant.UserStatus;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.specification.EntitySpecification;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.common.Constant.UserStatus.NEEDS_CONSENT;
import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.entity.BaseEntity.State.INACTIVE;
import static com.example.demo.common.response.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    //POST
    public PostUserRes createUser(PostUserReq postUserReq) {
        //중복 체크

        if (userRepository.existsByEmail(postUserReq.getEmail())) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(encryptPwd);
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        User saveUser = userRepository.save(postUserReq.toEntity());
        return new PostUserRes(saveUser.getId(), jwtService.createJwt(saveUser.getId()));

    }

    public PostUserRes createOAuthUser(User user, SocialLoginType socialLoginType) {
        User saveUser = userRepository.save(user);
        saveUser.setSocialLoginType(socialLoginType);

        // JWT 발급
        String jwtToken = jwtService.createJwt(saveUser.getId());
        return new PostUserRes(saveUser.getId(), jwtToken);

    }

    public void modifyUserName(Long userId, PatchUserReq patchUserReq) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.updateUser(patchUserReq);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.deleteUser();
    }

    public void setUserInfo(Long userId, PostUserInfoReq postUserInfoReq){
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        user.updateUserInfo(postUserInfoReq);
    }

    @Transactional(readOnly = true)
    public List<GetUserRes> getUsers() {
        List<GetUserRes> getUserResList = userRepository.findAllByState(ACTIVE).stream()
                .map(GetUserRes::new)
                .collect(Collectors.toList());
        return getUserResList;
    }

    @Transactional(readOnly = true)
    public List<GetUserRes> getUsersByEmail(String email) {
        if (!userRepository.existsByEmailAndState(email, ACTIVE)) {
            throw new BaseException(NOT_FIND_USER);
        }
        List<GetUserRes> getUserResList = userRepository.findAllByEmailAndState(email, ACTIVE).stream()
                .map(GetUserRes::new)
                .collect(Collectors.toList());
        return getUserResList;
    }


    @Transactional(readOnly = true)
    public GetUserRes getUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserRes(user);
    }

    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        Optional<User> result = userRepository.findByEmailAndState(email, ACTIVE);
        if (result.isPresent()) return true;
        return false;
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) {
        User user = userRepository.findByEmail(postLoginReq.getEmail())
            .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        if (user.getState() != ACTIVE) {
            throw new BaseException(NOT_ACITVE_USER);
        }
        if (user.getUserStatus() == UserStatus.SUSPENDED){
            throw new BaseException(RESTRICTED_USER);
        }
        if (user.getUserStatus() == UserStatus.WITHDRAWN){
            throw new BaseException(DELETE_USER);
        }
        if (user.getUserStatus() == NEEDS_CONSENT){
            throw new BaseException(BaseResponseStatus.NEEDS_CONSENT);
        }

        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){
            Long userId = user.getId();
            String jwt = jwtService.createJwt(userId);
            return new PostLoginRes(userId,jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public GetUserRes getUserByEmail(String email) {
        User user = userRepository.findByEmailAndState(email, ACTIVE).orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.updateLastLoginAt();
        return new GetUserRes(user);
    }

    public boolean validateJoinUser(PostUserReq postUserReq){
        Optional<User> user = userRepository.findByEmailAndState(postUserReq.getEmail(), ACTIVE);
        return user.isPresent();
    }

    public PostUserAgreeRes modifyUserAgree(Long userId, PostUserAgreeReq postUserAgreeReq) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        if (!postUserAgreeReq.isTermsLocationAgree() || !postUserAgreeReq.isTermsDataPolicyAgree() || !postUserAgreeReq.isTermsOfUseAgree()) {
            user.setUserStatus(NEEDS_CONSENT);
        }

        user.updateUserAgree(postUserAgreeReq);



        return new PostUserAgreeRes(userId);
    }

    public void checkAllConsentRenewalDate(){
        List<User> users = userRepository.findAllByState(ACTIVE);
        for (User user : users) {
            if (user.getConsentRenewalDate().plusYears(1L).isAfter(LocalDateTime.now())) {
                user.setUserStatus(NEEDS_CONSENT);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<GetAllUserRes> getUserDetailForAdmin(GetAllUserReq getAllUserReq){

        Map<String, Object> userDetail = new HashMap<>();
        if (getAllUserReq.getName() != null) {
            userDetail.put("name", getAllUserReq.getName());
        }
        if (getAllUserReq.getNickname() != null) {
            userDetail.put("email", getAllUserReq.getNickname());
        }
        if (getAllUserReq.getPhoneNum() != null) {
            userDetail.put("phoneNum", getAllUserReq.getPhoneNum());
        }
        if (getAllUserReq.getStatus() != null) {
            userDetail.put("status", getAllUserReq.getStatus());
        }
        if (getAllUserReq.getCreatedAt() != null) {
            LocalDate date = LocalDate.parse(getAllUserReq.getCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime createdAt = date.atTime(0, 0, 0);
            userDetail.put("createdAt", createdAt);
        }

        return userRepository.findAll(EntitySpecification.searchUser(userDetail)).stream()
                .map(GetAllUserRes::new)
                .collect(Collectors.toList());
    }

    public void setAdmin(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.setAdmin();
    }

    @Transactional(readOnly = true)
    public void isAdmin(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        if (user.isAdmin() == false){
            throw new BaseException(UNAUTHORIZED_USER);
        }
    }
    public void stopUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.setUserStatus(UserStatus.SUSPENDED);
    }

    @Transactional(readOnly = true)
    public GetUserAllDetailRes getUserAllDetail(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserAllDetailRes(user);
    }
}
