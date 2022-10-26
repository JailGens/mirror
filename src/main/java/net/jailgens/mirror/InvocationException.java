package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * An exception that is thrown if the target threw an exception.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public class InvocationException extends RuntimeException {

    /**
     * Constructs an invocation exception.
     *
     * @since 0.0.0
     */
    public InvocationException() {}

    /**
     * Constructs a new invocation exception with the specified detail message.
     *
     * @param message the detail message.
     * @since 0.0.0
     */
    public InvocationException(final @Nullable String message) {

        super(message);
    }

    /**
     * Constructs a new invocation exception with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause.
     * @since 0.0.0
     */
    public InvocationException(final @Nullable String message, final @Nullable Throwable cause) {

        super(message, cause);
    }

    /**
     * Constructs a new invocation exception with the specified cause.
     *
     * @param cause the cause.
     * @since 0.0.0
     */
    public InvocationException(final @Nullable Throwable cause) {

        super(cause);
    }
}
