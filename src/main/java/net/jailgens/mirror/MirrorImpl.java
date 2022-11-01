package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.WeakHashMap;

final class MirrorImpl implements Mirror {

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

            return new MirrorImpl();
        }
    }
}
