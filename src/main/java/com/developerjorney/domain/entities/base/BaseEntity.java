package com.developerjorney.domain.entities.base;

import com.developerjorney.core.patterns.validation.models.ValidateResult;
import com.developerjorney.domain.entities.base.valueobjects.InfoAuditVO;
import com.developerjorney.domain.entities.base.valueobjects.InfoValidateResultVO;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.util.UUID;
import java.util.function.Supplier;

@MappedSuperclass
public class BaseEntity {

    @Id
    protected UUID id;

    private InfoAuditVO infoAudit;

    @Transient
    private final transient InfoValidateResultVO infoValidateResultVO;

    public BaseEntity() {
        this.infoValidateResultVO = new InfoValidateResultVO();
    }

    protected  <T> T createNew(final String createdBy) {
        this.id = UUID.randomUUID();
        this.infoAudit = InfoAuditVO.createNew(createdBy);

        return (T)this;
    }

    protected InfoAuditVO getInfoAudit() {
        return this.infoAudit.clone();
    }

    protected boolean valid(final Supplier<ValidateResult> validation) {
        final var result = validation.get();
        return this.internalValid(result);
//        if(result instanceof ValidateResult valResult) {
//            return this.internalValid(valResult);
//        } else if (result instanceof InfoValidateResult infoValResult) {
//            return this.internalValid(infoValResult);
//        }
//
//        return false;
    }

    private boolean internalValid(final InfoValidateResultVO result) {
        result.getMessage().forEach(message -> this.infoValidateResultVO.addMessage(message));
        return this.infoValidateResultVO.isValid();
    }

    private boolean internalValid(final ValidateResult result) {
        result.getMessages().forEach(message -> this.infoValidateResultVO.addMessage(message));
        return this.infoValidateResultVO.isValid();
    }
}
