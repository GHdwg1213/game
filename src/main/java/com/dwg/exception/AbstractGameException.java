package com.dwg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class AbstractGameException extends RuntimeException {

    /**
     * Error errorData.
     */
    private Object errorData;

    public AbstractGameException(String message) {
        super(message);
    }

    public AbstractGameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Http status code不用删啊 哦哦哦 主要从  controller service  我点的那几个看
     *
     * @return {@link HttpStatus}
     */
    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public AbstractGameException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
