package com.developerjorney.core.patterns.result;

import com.developerjorney.core.patterns.notification.interfaces.INotification;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class ProcessResult<T> {

    private final T data;

    private final Result result;

    private final Collection<INotification> messages;

    private ProcessResult(
            final T data,
            final Result result
    ) {
        this.data = data;
        this.result = result;
        this.messages = new HashSet<>();
    }

    public T getData() {
        return this.data;
    }

    public boolean wasSuccess() {
        return this.isSuccess() || this.isPartialSuccess();
    }

    public boolean isSuccess() {
        return Objects.equals(this.result, Result.SUCCESS);
    }

    public boolean isPartialSuccess() {
        return Objects.equals(this.result, Result.PARTIAL_SUCCESS);
    }

    public boolean isFail() {
        return Objects.equals(this.result, Result.FAIL);
    }

    public ProcessResult<T> addMessage(final INotification notification) {
        this.messages.add(notification);
        return this;
    }

    public static <T> ProcessResult<T> success() {
        return success(null);
    }

    public static <T> ProcessResult<T> success(
            final T data
    ) {
        return new ProcessResult(data, Result.SUCCESS);
    }


    public static <T> ProcessResult<T> partialSuccess() {
        return partialSuccess(null);
    }

    public static <T> ProcessResult<T> partialSuccess(
            final T data
    ) {
        return new ProcessResult(data, Result.PARTIAL_SUCCESS);
    }

    public static <T> ProcessResult<T> fail() {
        return fail(null);
    }

    public static <T> ProcessResult<T> fail(
            final T data
    ) {
        return new ProcessResult(data, Result.FAIL);
    }

    public enum Result {
        SUCCESS,
        PARTIAL_SUCCESS,
        FAIL
    }
}
