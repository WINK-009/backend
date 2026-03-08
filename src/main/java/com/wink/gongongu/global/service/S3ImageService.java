package com.wink.gongongu.global.service;

import com.wink.gongongu.global.exception.BusinessException;
import com.wink.gongongu.global.exception.S3ErrorCode;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3ImageService {

    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
        "image/jpeg",
        "image/png",
        "image/webp",
        "image/gif"
    );

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.public-base-url}")
    private String publicBaseUrl;

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);

        String extension = extractExtension(file.getOriginalFilename()); //파일 확장자 추출
        String key = createKey(extension); //s3 key 생성

        PutObjectRequest putObjectRequest = PutObjectRequest.builder() //s3에 업로드 요청 객체 생성
            .bucket(bucket)
            .key(key)
            .contentType(file.getContentType())
            .build();

        s3Client.putObject( //실제 업로드
            putObjectRequest,
            RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return getPublicUrl(key);
    }

    public String getPublicUrl(String key) {
        String encodedKey = encodeKey(key); //url 깨지지 않게 인코딩
        return publicBaseUrl + "/" + encodedKey;
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(S3ErrorCode.CANNOT_UPLOAD_EMPTY_FILE);
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new BusinessException(S3ErrorCode.UNSUPPORTED_IMAGE_TYPE);
        }
    }

    private String extractExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new BusinessException(S3ErrorCode.FILE_EXTENSION_NOT_FOUND);
        }

        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
    }

    private String createKey(String extension) {
        return "public/" + UUID.randomUUID() + "." + extension;
    }

    private String encodeKey(String key) {
        return URLEncoder.encode(key, StandardCharsets.UTF_8)
            .replace("+", "%20")
            .replace("%2F", "/");
    }

}
