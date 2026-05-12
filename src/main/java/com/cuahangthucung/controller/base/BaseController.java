package com.cuahangthucung.controller.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

    // Hàm chuẩn hóa dữ liệu trả về thành công
    protected ResponseEntity<Map<String, Object>> resSuccess(Object data, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);
        res.put("data", data);
        return ResponseEntity.ok(res);
    }

    // Hàm chuẩn hóa dữ liệu khi tạo mới thành công (201 Created)
    protected ResponseEntity<Map<String, Object>> resCreated(Object data, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", HttpStatus.CREATED.value());
        res.put("message", message);
        res.put("data", data);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}