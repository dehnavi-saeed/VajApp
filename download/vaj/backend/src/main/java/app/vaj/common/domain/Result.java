package app.vaj.common.domain;

import java.util.List;
import java.util.Optional;

public final class Result<T> {

    private final T value;
    private final List<String> errors;
    private final boolean success;

    private Result(T value, List<String> errors, boolean success) {
        this.value = value;
        this.errors = errors;
        this.success = success;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, List.of(), true);
    }

    public static <T> Result<T> failure(List<String> errors) {
        return new Result<>(null, errors, false);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(null, List.of(error), false);
    }

    public boolean isSuccess() { return success; }

    public Optional<T> getValue() { return Optional.ofNullable(value); }

    public List<String> getErrors() { return errors; }
}