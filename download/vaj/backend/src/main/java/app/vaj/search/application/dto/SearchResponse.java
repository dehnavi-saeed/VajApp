package app.vaj.search.application.dto;

import java.util.List;

public record SearchResponse(
    String query,
    List<SearchResultItem> results,
    long totalItems
) {}