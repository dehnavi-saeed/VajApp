package app.vaj.dashboard.application.dto;

import java.util.List;
import java.util.UUID;

public record DashboardResponse(
    long totalBooks,
    long currentlyReading,
    long completedBooks,
    long totalNotes,
    long totalHighlights,
    long totalBookmarks,
    long activeGoals,
    long readingStreak,
    List<RecentlyReadBook> recentlyRead
) {
    public record RecentlyReadBook(UUID id, String title, String status) {}
}