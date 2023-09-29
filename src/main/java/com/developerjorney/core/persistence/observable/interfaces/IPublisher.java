package com.developerjorney.core.persistence.observable.interfaces;

public interface IPublisher {

    <TSubscriber extends ISubscriber<TSubject>, TSubject> void subscriber(
            final Class<TSubscriber> subscriber,
            final Class<TSubject> subject
    );

    <TSubject> void publisher(final TSubject subject);

}
