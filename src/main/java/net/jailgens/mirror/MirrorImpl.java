package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * The {@link Mirror} implementation.
 *
 * @author Sparky983
 */
final class MirrorImpl implements Mirror {

    /**
     * A private inner class allowing for lazy singleton initialization.
     */
    private static class Instance {

        private static final Mirror INSTANCE = new MirrorImpl();
    }

    private final Map<Class<?>, TypeDefinition<?>> typeDefinitionCache = new WeakHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends @NonNull Object> @NonNull TypeDefinition<@NonNull T> reflect(
            final @NonNull Class<@NonNull T> cls) {

        final TypeDefinition<?> cachedTypeDefinition = typeDefinitionCache.get(cls);

        if (cachedTypeDefinition != null) {
            return (TypeDefinition<T>) cachedTypeDefinition;
        }

        final TypeDefinition<T> typeDefinition = new TypeDefinitionImpl<>(cls);
        typeDefinitionCache.put(cls, typeDefinition);
        return typeDefinition;
    }

    static final class BuilderImpl implements Builder {

        @Override
        public @NonNull Mirror build() {

            return Instance.INSTANCE;
        }
    }
}
