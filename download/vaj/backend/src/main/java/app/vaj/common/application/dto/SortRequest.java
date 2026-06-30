package app.vaj.common.application.dto;

public record SortRequest(
        String field,
        String direction
) {
    public static SortRequest asc(String field) {
        return new SortRequest(field, "ASC");
    }

    public static SortRequest desc(String field) {
        return new SortRequest(field, "DESC");
    }

    public boolean isAscending() {
        return "ASC".equalsIgnoreCase(direction);
    }
}