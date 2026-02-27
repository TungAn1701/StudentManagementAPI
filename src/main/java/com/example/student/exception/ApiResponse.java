package com.example.student.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;

public static <T> ApiResponse<T> success(T data, String message) {
    return ApiResponse.<T>builder()
            .success(true)
            .data(data)
            .message(message)
            .build();
}
public static <T> ApiResponse<T> failure(String message) {
    return ApiResponse.<T>builder()
            .success(false)
            .data(null)
            .message(message)
            .build();
}
public static <T> ApiResponse<T> success( String message) {
    return ApiResponse.<T>builder()
            .success(true)
            .data(null)
            .message(message)
            .build();
}
}
