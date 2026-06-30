package app.vaj.goal.infrastructure.persistence;
import app.vaj.goal.domain.*;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;
@Repository
public class ReadingGoalRepositoryImpl implements ReadingGoalRepository {
    private final JpaReadingGoalRepository jpa;
    public ReadingGoalRepositoryImpl(JpaReadingGoalRepository jpa) { this.jpa = jpa; }
    @Override public ReadingGoal save(ReadingGoal g) { jpa.save(toEntity(g)); return g; }
    @Override public Optional<ReadingGoal> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }
    @Override public List<ReadingGoal> findByUserId(UUID userId) { return jpa.findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(userId).stream().map(this::toDomain).toList(); }

    private ReadingGoal toDomain(ReadingGoalEntity e) {
        try {
            var c = ReadingGoal.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            ReadingGoal g = c.newInstance(e.getId());
            set(g,"userId",e.getUserId()); set(g,"type",GoalType.valueOf(e.getGoalType().name()));
            set(g,"target",e.getTarget()); set(g,"currentProgress",e.getCurrentProgress());
            set(g,"startDate",e.getStartDate()); set(g,"endDate",e.getEndDate());
            set(g,"status",GoalStatus.valueOf(e.getStatus().name()));
            set(g,"createdAt",e.getCreatedAt()); set(g,"updatedAt",e.getUpdatedAt());
            return g;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }
    private ReadingGoalEntity toEntity(ReadingGoal g) {
        ReadingGoalEntity e = new ReadingGoalEntity();
        e.setId(g.getId()); e.setUserId(g.getUserId());
        e.setGoalType(ReadingGoalEntity.TypeJpa.valueOf(g.getType().name()));
        e.setTarget(g.getTarget()); e.setCurrentProgress(g.getCurrentProgress());
        e.setStartDate(g.getStartDate()); e.setEndDate(g.getEndDate());
        e.setStatus(ReadingGoalEntity.StatusJpa.valueOf(g.getStatus().name()));
        e.setCreatedAt(g.getCreatedAt()!=null?g.getCreatedAt():java.time.Instant.now());
        e.setUpdatedAt(g.getUpdatedAt()!=null?g.getUpdatedAt():java.time.Instant.now());
        e.setCreatedBy(g.getUserId()); e.setVersion(g.getVersion()!=null?g.getVersion():0L); e.setDeleted(g.isDeleted());
        return e;
    }
    private void set(Object t,String n,Object v) {
        try { Field f=t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); }
        catch(Exception e) { try { Field f=t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); } catch(Exception ignored) {} }
    }
}