package com.developerjorney.domain.entities.base.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.Instant;

@Embeddable
public class InfoAuditVO {

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdAt")
    private Instant createdAt;

    @Column(name = "updateBy")
    private String updateBy;

    @Column(name = "updateAt")
    private Instant updateAt;

    @Column(name = "deletedAt")
    private Instant deletedAt;

    private InfoAuditVO(final String createdBy, final Instant createdAt) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public InfoAuditVO(
            final String createdBy,
            final Instant createdAt,
            final String updateBy,
            final Instant updateAt
    ) {
        this(createdBy, createdAt);
        this.updateBy = updateBy;
        this.updateAt = updateAt;
    }

    public InfoAuditVO(
        final String createdBy,
        final Instant createdAt,
        final String updateBy,
        final Instant updateAt,
        final Instant deletedAt
    ) {
        this(createdBy, createdAt, updateBy, updateAt);
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
                this.updateBy,
                this.updateAt,
                this.deletedAt
        );
    }
}
