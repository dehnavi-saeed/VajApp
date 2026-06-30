package app.vaj.collection.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "CollectionItems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionItemEntity {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "CollectionId", nullable = false)
    private UUID collectionId;

    @Column(name = "ItemType", nullable = false, length = 50)
    private String itemType;

    @Column(name = "ItemId", nullable = false)
    private UUID itemId;

    @Column(name = "Order", nullable = false)
    private int order;

    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;
}