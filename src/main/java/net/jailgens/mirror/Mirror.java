package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Reflects types.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface Mirror {

    /**
     * Reflects the specified type.
     *
     * @param cls the type definition.
     * @param <T> the type of the type definition.
     * @return the type definition.
     * @since 0.0.0
     */
    <T extends @NonNull Object> @NonNull TypeDefinition<T> reflect(@NonNull Class<T> cls);

    /**
     * A {@link Mirror} builder.
     *
     * @since 0.0.0
     */
    interface Builder {

        /**
         * Builds the mirror.
         *
         * @return the built mirror.
         * @since 0.0.0
         */
        @NonNull Mirror build();
    }
}
