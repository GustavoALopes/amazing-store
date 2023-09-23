package com.developerjorney.core.persistence.observable.interfaces;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BasePublisher implements IPublisher {

    private final ConcurrentHashMap<Class, Collection<Class>> subscribers;

    public BasePublisher() {
        this.subscribers = new ConcurrentHashMap<>();
    }

    @Override
    public <TSubscriber extends ISubscriber<TSubject>, TSubject> void subscriber(
            final Class<TSubscriber> subscriber,
            final Class<TSubject> subject
    ) {
        this.addSubjectIfNotExists(subject);
        this.addSubjectToSubscriber(subscriber, subject);
    }

    @Override
    public <TSubject> void publisher(final TSubject subject) {
        final var subjectType = subject.getClass();

        this.subscribers.get(subjectType).forEach(subscriber -> {
            this.instanceSubscriber(subscriber).update(subject);
        });
    }

    protected abstract <TSubject> ISubscriber<TSubject> instanceSubscriber(
            final Class<TSubject> subscriber
    );

    private <TSubscriber extends ISubscriber<TSubject>, TSubject> void addSubjectToSubscriber(
            final Class<TSubscriber> subscriber,
            final Class<TSubject> subject
    ) {
        this.subscribers.get(subject).add(subscriber);
    }

    private <TSubject> void addSubjectIfNotExists(
            final Class<TSubject> subject
    ) {
        if(this.subscribers.containsKey(subject)) {
            return;
        }

        this.subscribers.putIfAbsent(subject, new HashSet<>());
    }
}
