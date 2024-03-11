package com.example.demo.src.image;

import com.example.demo.common.secret.Secret;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    // 이미지 업로드
    public List<String> uploadImages(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();
        // 이미지 업로드 로직
        for (MultipartFile file : files) {
            // 이미지 업로드 로직
            Bucket bucket = StorageClient.getInstance().bucket(Secret.FIREBASE_DATABASE_URL);
            InputStream content = new ByteArrayInputStream(file.getBytes());
            Blob blob = bucket.create(file.getOriginalFilename(), content, file.getContentType());
            urls.add(blob.getMediaLink());
        }
        return urls;
    }
}
