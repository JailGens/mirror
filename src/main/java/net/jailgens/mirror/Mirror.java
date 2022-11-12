package net.jailgens.mirror;

import org.checkerframework.checker.lock.qual.NewObject;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.lang.reflect.Proxy;

/**
 * Reflects types.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface Mirror {

    /**
     * Creates a new mirror builder.
     *
     * @return the newly created builder.
     * @since 0.0.0
     */
    @SideEffectFree
    static @NewObject @NonNull Builder builder() {

        return new MirrorImpl.BuilderImpl();
    }

    /**
     * Reflects the specified type.
     *
     * @param cls the type definition.
     * @param <T> the type of the type definition.
     * @return the type definition.
     * @throws NullPointerException if {@code cls} is {@code null}.
     * @since 0.0.0
     */
    <T extends @NonNull Object> @NonNull TypeDefinition<@NonNull T> reflect(
            @NonNull Class<@NonNull T> cls);

    /**
     * Creates a proxy for the specified type.
     *
     * @param type the type.
     * @param handler the handler.
     * @return the proxy.
     * @param <T> the type of the type.
     * @throws IllegalArgumentException if any restrictions defined in {@link Proxy} are
     * violated.
     * @throws NullPointerException if {@code type} or {@code handler} is {@code null}.
     * @since 0.2.0
     */
    <T extends @NonNull Object> @NonNull T createProxy(@NonNull Class<T> type,
                                                       @NonNull InvocationHandler<T> handler);

    /**
     * A {@link Mirror} builder.
     *
     * @since 0.0.0
     */
    interface Builder {

        /**
         * Sets the class loader to use.
         * <p>
         * If the specified class loader is {@code null}, or none is specified, the system class
         * loader will be used.
         *
         * @param classLoader the class loader to use.
         * @return this builder (for chaining).
         * @since 0.2.0
         */
        @NonNull @This Builder classLoader(@Nullable ClassLoader classLoader);

        /**
         * Builds the mirror.
         *
         * @return the built mirror.
         * @since 0.0.0
         */
        @NonNull Mirror build();
    }
}
