package com.example.demo.src.image;


import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.image.model.PostImagesRes;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/images")
public class ImageController {
    private final ImageService imageService;

    // 이미지 업로드
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostImagesRes> uploadImage(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<String> urls = imageService.uploadImages(files);
        return new BaseResponse<>(new PostImagesRes(urls));
    }
}
