package com.developerjorney.core.persistence.observable.interfaces;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
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
        final var subscriberCollection = this.getSubscribers(subject);

        subscriberCollection.forEach(subscriber -> {
            this.instanceSubscriber(subscriber).update(subject);
        });
    }

    private <TSubject> Collection<Class> getSubscribers(final TSubject subject) {
        final var subjectType = subject.getClass();
        final var interfaceType = subject.getClass().getInterfaces().length == 0 ? null : subject.getClass().getInterfaces()[0];

        return Optional.ofNullable(this.subscribers.get(interfaceType))
                .or(() -> Optional.ofNullable(this.subscribers.get(subjectType)))
                .orElse(Collections.emptySet());
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
