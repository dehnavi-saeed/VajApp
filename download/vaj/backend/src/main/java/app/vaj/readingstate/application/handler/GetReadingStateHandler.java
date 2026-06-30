package app.vaj.readingstate.application.handler;

import app.vaj.readingstate.application.dto.ReadingStateResponse;
import app.vaj.readingstate.infrastructure.persistence.JpaReadingStateRepository;
import app.vaj.readingstate.infrastructure.persistence.ReadingStateEntity;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GetReadingStateHandler {

    private final JpaReadingStateRepository repo;

    public GetReadingStateHandler(JpaReadingStateRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public ReadingStateResponse handle(UUID bookId) {
        ReadingStateEntity entity = repo.findByBookId(bookId)
                .orElseThrow(() -> new EntityNotFoundException("ReadingState", bookId));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ReadingStateResponse> handleByUser(UUID userId) {
        return repo.findByUserIdOrderByLastReadAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private ReadingStateResponse toResponse(ReadingStateEntity e) {
        return new ReadingStateResponse(
                e.getId(), e.getBookId(), e.getUserId(),
                e.getCurrentPage(), e.getTotalPagesRead(),
                e.getProgressPercentage(), e.getTotalReadingMinutes(),
                e.getLastSessionId(), e.getLastReadAt(),
                e.getEstimatedFinishDate(), e.getStatus(), e.getUpdatedAt()
        );
    }
}