package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * A type definition.
 *
 * @author Sparky983
 * @param <T> The type.
 * @since 0.0.0
 */
public interface TypeDefinition<T extends @NonNull Object> extends Annotated {

    /**
     * Returns whether the type definition defines a class.
     * <p>
     * This includes enums and records.
     *
     * @return whether this type definition defines a class.
     * @since 0.0.0
     */
    @Pure
    boolean isClass();

    /**
     * Returns whether the type definition defines an interface.
     * <p>
     * This includes annotations.
     *
     * @return whether this type definition defines an interface.
     * @since 0.0.0
     */
    @Pure
    boolean isInterface();

    /**
     * Gets this type definition's name.
     *
     * @return this type definition's name.
     * @since 0.0.0
     */
    @Pure
    @NonNull String getName();

    /**
     * Gets all this class' modifiers.
     *
     * @return all this class' modifiers.
     * @since 0.0.0
     */
    @Pure
    @NonNull Set<@NonNull Modifier> getModifiers();

    /**
     * Gets the raw type of this type definition.
     *
     * @return the raw type of this type definition.
     * @since 0.0.0
     */
    @Pure
    @NonNull Class<@NonNull T> getRawType();

    /**
     * Gets the fields of this type definition.
     *
     * @return the fields of this type definition.
     * @since 0.0.0
     */
    @Pure
    @NonNull Collection<@NonNull Field<@NonNull T, ? extends @NonNull Object>> getFields();

    /**
     * Gets this type's constructors.
     *
     * @return the type's constructors.
     * @since 0.0.0
     */
    @Pure
    @NonNull Collection<@NonNull Constructor<@NonNull T>> getConstructors();

    /**
     * Gets this type's methods.
     *
     * @return this type's methods.
     * @since 0.0.0
     */
    @Pure
    @NonNull Collection<@NonNull Method<@NonNull T, ? extends @Nullable Object>> getMethods();

    /**
     * Gets this type's members.
     *
     * @return the type's members.
     * @since 0.0.0
     */
    @Pure
    @NonNull Collection<@NonNull Member<@NonNull T>> getMembers();

    /**
     * Gets this type's inner types.
     *
     * @return the type's inner types.
     * @since 0.4.0
     */
    @Pure
    default @NonNull Collection<@NonNull TypeDefinition<? extends @NonNull Object>> getInnerTypes() {

        return List.of();
    }

    /**
     * Gets this member's declaring type.
     *
     * @return this member's declaring type.
     * @since 0.4.0
     */
    @Pure
    default @Nullable TypeDefinition<? extends @NonNull Object> getDeclaringType() {

        return null;
    }

    /**
     * Gets this member's raw declaring type.
     *
     * @return this member's raw declaring type.
     * @since 0.4.0
     */
    @Pure
    default @Nullable Class<? extends @NonNull Object> getRawDeclaringType() {

        return null;
    }
}
