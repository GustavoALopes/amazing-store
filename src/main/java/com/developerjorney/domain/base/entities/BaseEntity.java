package com.developerjorney.domain.base.entities;

import com.developerjorney.core.patterns.validation.models.ValidateMessage;
import com.developerjorney.core.patterns.validation.models.ValidateResult;
import com.developerjorney.domain.base.entities.valueobjects.InfoAuditVO;
import com.developerjorney.domain.base.entities.valueobjects.InfoValidateResultVO;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.util.Collection;
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

    public InfoValidateResultVO getInfoValidateResultVO() {
        return this.infoValidateResultVO.deepClone();
    }

    public boolean isValid() {
        return this.infoValidateResultVO.isValid();
    }

    protected <T> boolean valid(final Supplier<T> validation) {
        final var result = validation.get();
        if(result instanceof ValidateResult valResult) {
            return this.internalValid(valResult.getMessages());
        } else if (result instanceof InfoValidateResultVO infoValResult) {
            return this.internalValid(infoValResult.getMessages());
        }

        return false;
    }

    private boolean internalValid(final Collection<ValidateMessage> messages) {
        for(final var message : messages) {
            this.infoValidateResultVO.addMessage(message);
        }
        return this.infoValidateResultVO.isValid();
    }
}
