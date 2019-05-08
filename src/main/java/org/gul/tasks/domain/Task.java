package org.gul.tasks.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    public Task(UUID uuid, Status status) {
        this.id = uuid;
        this.status = status;
    }

    public Task() {
    }

    public UUID getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }
}