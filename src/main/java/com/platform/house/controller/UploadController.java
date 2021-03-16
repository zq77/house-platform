package com.platform.house.controller;

import com.platform.house.utils.QiNiuUpload;
import com.platform.house.utils.ResponseUtil;
import com.qiniu.util.Auth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UploadController {

    @GetMapping("/api/upload/token")
    public ResponseEntity getToken() {
        Auth auth = Auth.create(QiNiuUpload.ACCESS_KEY, QiNiuUpload.SECRET_KEY);
        String upToken = auth.uploadToken(QiNiuUpload.BUCKET);
        Map<String, String> result = new HashMap<>();
        result.put("token", upToken);
        result.put("key", UUID.randomUUID().toString().replace("-", ""));
        return ResponseUtil.success(result);
    }

    @GetMapping("/api/upload/token/{fileKey}")
    public ResponseEntity getToken(@PathVariable String fileKey) {
        Auth auth = Auth.create(QiNiuUpload.ACCESS_KEY, QiNiuUpload.SECRET_KEY);
        String upToken = auth.uploadToken(QiNiuUpload.BUCKET, fileKey);
        Map<String, String> result = new HashMap<>();
        result.put("token", upToken);
        result.put("key", fileKey);
        return ResponseUtil.success(result);
    }

    @GetMapping("/api/upload/{fileKey}")
    public ResponseEntity getFile(@PathVariable String fileKey) {
        return ResponseUtil.success(QiNiuUpload.QINIU_WEBSITE + fileKey);
    }
}
