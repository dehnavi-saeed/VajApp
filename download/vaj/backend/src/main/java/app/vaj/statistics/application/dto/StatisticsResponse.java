package app.vaj.statistics.application.dto;

public record StatisticsResponse(
    long totalBooksRead,
    long totalPagesRead,
    long totalMinutesRead,
    long totalHighlights,
    long totalNotes,
    long currentStreak,
    double averageDailyPages
) {}