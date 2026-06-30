package app.vaj.search.application.handler;

import app.vaj.book.infrastructure.persistence.BookEntity;
import app.vaj.book.infrastructure.persistence.JpaBookRepository;
import app.vaj.highlight.infrastructure.persistence.HighlightEntity;
import app.vaj.highlight.infrastructure.persistence.JpaHighlightRepository;
import app.vaj.library.infrastructure.persistence.JpaLibraryRepository;
import app.vaj.library.infrastructure.persistence.LibraryEntity;
import app.vaj.note.infrastructure.persistence.JpaKnowledgeNoteRepository;
import app.vaj.note.infrastructure.persistence.KnowledgeNoteEntity;
import app.vaj.search.application.dto.SearchResponse;
import app.vaj.search.application.dto.SearchResultItem;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SearchHandler {

    private final JpaLibraryRepository libraryRepo;
    private final JpaBookRepository bookRepo;
    private final JpaKnowledgeNoteRepository noteRepo;
    private final JpaHighlightRepository highlightRepo;

    public SearchHandler(JpaLibraryRepository libraryRepo,
                         JpaBookRepository bookRepo,
                         JpaKnowledgeNoteRepository noteRepo,
                         JpaHighlightRepository highlightRepo) {
        this.libraryRepo = libraryRepo;
        this.bookRepo = bookRepo;
        this.noteRepo = noteRepo;
        this.highlightRepo = highlightRepo;
    }

    @Transactional(readOnly = true)
    public SearchResponse handle(UUID userId, String query, int page, int size) {
        List<UUID> libraryIds = libraryRepo.findByUserIdAndIsDeletedFalse(userId).stream()
                .map(LibraryEntity::getId)
                .toList();

        List<SearchResultItem> results = new ArrayList<>();

        if (!libraryIds.isEmpty()) {
            List<BookEntity> books = bookRepo.findByLibraryIdInAndTitleContainingIgnoreCaseAndIsDeletedFalse(
                    libraryIds, query, PageRequest.of(0, size));
            for (BookEntity b : books) {
                results.add(new SearchResultItem(b.getId(), "BOOK", b.getTitle(),
                        b.getDescription() != null ? truncate(b.getDescription(), 150) : null,
                        b.getCreatedAt()));
            }
        }

        List<KnowledgeNoteEntity> notes = noteRepo.findByUserIdAndTitleContainingIgnoreCaseAndIsDeletedFalse(
                userId, query, PageRequest.of(0, size));
        for (KnowledgeNoteEntity n : notes) {
            results.add(new SearchResultItem(n.getId(), "NOTE", n.getTitle(),
                    truncate(n.getContent(), 150), n.getCreatedAt()));
        }

        List<KnowledgeNoteEntity> notesByContent = noteRepo.findByUserIdAndContentContainingIgnoreCaseAndIsDeletedFalse(
                userId, query, PageRequest.of(0, size));
        for (KnowledgeNoteEntity n : notesByContent) {
            boolean alreadyAdded = results.stream().anyMatch(r -> r.id().equals(n.getId()));
            if (!alreadyAdded) {
                results.add(new SearchResultItem(n.getId(), "NOTE", n.getTitle(),
                        truncate(n.getContent(), 150), n.getCreatedAt()));
            }
        }

        List<HighlightEntity> highlights = highlightRepo.findByUserIdAndTextSnapshotContainingIgnoreCaseAndIsDeletedFalse(
                userId, query, PageRequest.of(0, size));
        for (HighlightEntity h : highlights) {
            results.add(new SearchResultItem(h.getId(), "HIGHLIGHT", null,
                    truncate(h.getTextSnapshot(), 150), h.getCreatedAt()));
        }

        long totalItems = results.size();

        int fromIndex = page * size;
        if (fromIndex >= results.size()) {
            results = List.of();
        } else {
            int toIndex = Math.min(fromIndex + size, results.size());
            results = results.subList(fromIndex, toIndex);
        }

        return new SearchResponse(query, results, totalItems);
    }

    private String truncate(String text, int maxLen) {
        if (text == null) return null;
        if (text.length() <= maxLen) return text;
        return text.substring(0, maxLen) + "...";
    }
}