package com.example.demo.src.image;


import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.image.model.PostImagesRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/app/images")
public class ImageController {
    private final ImageService imageService;

    // 이미지 업로드
    @ResponseBody
    @PostMapping("")
    @Operation(
        summary = "이미지 및 동영상 업로드 API"
        , description = "# 이미지 및 동영상 업로드 API 입니다. 파일의 용량은 100MB 이하 입니다. \n."
        + "이미지 업로드 성공 시, 이미지의 URL을 반환 합니다."
    )
    public BaseResponse<PostImagesRes> uploadImage(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<String> urls = imageService.uploadImages(files);
        return new BaseResponse<>(new PostImagesRes(urls));
    }
}
