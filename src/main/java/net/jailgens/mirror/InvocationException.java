package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Objects;

/**
 * An exception that is thrown if the target threw an exception.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public class InvocationException extends RuntimeException {

    /**
     * The target exception.
     */
    private final @NonNull Throwable targetException;

    /**
     * Constructs a new invocation exception with the specified detail message and target exception.
     *
     * @param message the detail message.
     * @param targetException the target exception.
     * @since 0.0.0
     */
    @Pure
    public InvocationException(final @Nullable String message,
                               final @NonNull Throwable targetException) {

        super(message, targetException);
        Objects.requireNonNull(targetException, "targetException cannot be null");
        this.targetException = targetException;
    }

    /**
     * Constructs a new invocation exception with the specified target exception.
     *
     * @param targetException the target exception.
     * @since 0.0.0
     */
    @Pure
    public InvocationException(final @NonNull Throwable targetException) {

        this(null, targetException);
    }

    /**
     * Returns the exception that was thrown by the target during the invocation.
     *
     * @return the exception that was thrown by the target during the invocation.
     * @since 0.0.0
     */
    @Pure
    public @NonNull Throwable getTargetException() {

        return targetException;
    }
}
