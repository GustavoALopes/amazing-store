package com.developerjorney.core.usecase;

import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;

public abstract class BaseUseCase<TParams, TOut> {

    private final IUnitOfWork unitOfWork;

    public BaseUseCase(final IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public TOut execute(final TParams params) {
        return this.unitOfWork.execute(() -> {
            return this.executeInternal(params);
        });
    }

    protected abstract TOut executeInternal(final TParams params);
}
