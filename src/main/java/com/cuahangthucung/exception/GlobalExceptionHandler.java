package com.cuahangthucung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Bắt lỗi RuntimeException (Ví dụ: "Không tìm thấy thú cưng" bạn đã ném ra trong Service)
     * Giúp Server vẫn chạy bình thường sau khi gặp lỗi logic.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        errors.put("error", "Lỗi xử lý hệ thống");
        errors.put("message", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Bắt lỗi Validation (Khi bạn gửi JSON thiếu trường hoặc sai định dạng mà có @Valid)
     * Trả về chi tiết từng trường bị lỗi để Frontend dễ hiển thị.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value()); // 400
        response.put("error", "Dữ liệu không hợp lệ");
        response.put("details", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Bắt các lỗi chung khác để đảm bảo trang web không bao giờ hiện trang trắng lỗi (Whitelabel)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errors.put("message", "Đã xảy ra lỗi ngoài ý muốn. Vui lòng thử lại sau.");
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 404);
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}