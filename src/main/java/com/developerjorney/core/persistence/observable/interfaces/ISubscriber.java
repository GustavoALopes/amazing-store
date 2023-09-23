package com.developerjorney.core.persistence.observable.interfaces;

public interface ISubscriber<TSubject> {

    <TSubject> void update(final TSubject subject);
}
