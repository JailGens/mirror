package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.List;

/**
 * Represents anything that can be executed with arguments.
 *
 * @author Sparky983
 * @param <R> the receiver type. <p> For static members or constructors this is the declaring class.
 * @param <T> the return type.
 * @see java.lang.reflect.Executable
 * @since 0.0.0
 */
public interface Invokable<R extends @NonNull Object, T extends @Nullable Object> extends Annotated, Member<@NonNull R>, Typed<@NonNull T> {

    /**
     * Gets the parameters of this invokable.
     *
     * @return the parameters of this.
     * @since 0.0.0
     */
    @Pure
    @NonNull List<@NonNull Parameter<? extends @NonNull Object>> getParameters();

    /**
     * Gets the return type of this invokable.
     *
     * @return the return type of this invokable.
     */
    @Override
    @NonNull ParameterizedType<@NonNull T> getType();

    /**
     * Gets the return raw type of this invokable.
     *
     * @return the return raw type of this invokable.
     */
    @Override
    @NonNull Class<@NonNull T> getRawType();
}
