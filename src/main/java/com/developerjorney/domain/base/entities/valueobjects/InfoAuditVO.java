package com.developerjorney.domain.base.entities.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Embeddable
public class InfoAuditVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public InfoAuditVO() {
        this.createdBy = "";
        this.updatedBy = "";
    }

    private InfoAuditVO(final String createdBy, final Instant createdAt) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public InfoAuditVO(
            final String createdBy,
            final Instant createdAt,
            final String updatedBy,
            final Instant updatedAt
    ) {
        this(createdBy, createdAt);
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public InfoAuditVO(
        final String createdBy,
        final Instant createdAt,
        final String updatedBy,
        final Instant updatedAt,
        final Instant deletedAt
    ) {
        this(createdBy, createdAt, updatedBy, updatedAt);
        this.deletedAt = deletedAt;
    }

    public static InfoAuditVO createNew(
            final String createdBy
    ) {
        return new InfoAuditVO(createdBy, Instant.now());
    }

    public static InfoAuditVO update(
        final String createdBy,
        final Instant createdAt,
        final String updateBy
    ) {
        return new InfoAuditVO(
                createdBy,
                createdAt,
                updateBy,
                Instant.now()
        );
    }

    public static InfoAuditVO delete(
        final String createdBy,
        final Instant createdAt,
        final String updateBy
    ) {
        return new InfoAuditVO(
                createdBy,
                createdAt,
                updateBy,
                Instant.now(),
                Instant.now()
        );
    }

    public static InfoAuditVO fill(
        final String createdBy,
        final Instant createdAt,
        final String updateBy,
        final Instant updateAt,
        final Instant deletedAt
    ) {
        return new InfoAuditVO(
                createdBy,
                createdAt,
                updateBy,
                updateAt,
                deletedAt
        );
    }

    public InfoAuditVO clone() {
        return InfoAuditVO.fill(
                this.createdBy,
                this.createdAt,
                this.updatedBy,
                this.updatedAt,
                this.deletedAt
        );
    }
}
