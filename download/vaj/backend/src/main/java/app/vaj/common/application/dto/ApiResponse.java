package app.vaj.common.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        T data,
        ApiError error,
        ApiMeta meta
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, null);
    }

    public static <T> ApiResponse<T> success(T data, ApiMeta meta) {
        return new ApiResponse<>(true, data, null, meta);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false, null, new ApiError(code, message, null), null);
    }

    public static <T> ApiResponse<T> error(String code, String message, List<ApiFieldError> details) {
        return new ApiResponse<>(false, null, new ApiError(code, message, details), null);
    }

    public record ApiError(String code, String message, List<ApiFieldError> details) {}
    public record ApiFieldError(String field, String message, Object rejectedValue) {}
    public record ApiMeta(int page, int size, long totalItems, int totalPages) {}
}