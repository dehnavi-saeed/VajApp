package app.vaj.common.application.dto;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> items,
        int page,
        int size,
        long totalItems,
        int totalPages
) {
    public static <T> PaginatedResponse<T> of(List<T> items, int page, int size, long totalItems) {
        int totalPages = size > 0 ? (int) Math.ceil((double) totalItems / size) : 0;
        return new PaginatedResponse<>(items, page, size, totalItems, totalPages);
    }
}