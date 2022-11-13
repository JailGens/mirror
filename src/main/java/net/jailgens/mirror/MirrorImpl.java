package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.returnsreceiver.qual.This;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;
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

        private static final @NonNull Mirror INSTANCE = new MirrorImpl(true, DEFAULT_CLASS_LOADER);
    }

    private static final @NonNull ClassLoader DEFAULT_CLASS_LOADER = ClassLoader.getSystemClassLoader();
    private static final @NonNull Object @NonNull [] EMPTY_OBJECT_ARRAY = new Object[0];

    private final @NonNull Map<
            @NonNull Class<? extends @NonNull Object>,
            @NonNull TypeDefinition<? extends @NonNull Object>
            > typeDefinitionCache;
    private final boolean cachingEnabled;

    private final @NonNull ClassLoader classLoader;

    private MirrorImpl(final boolean cache, final @NonNull ClassLoader classLoader) {

        if (cache) {
            this.typeDefinitionCache = new WeakHashMap<>();
        } else {
            this.typeDefinitionCache = Map.of();
        }

        this.cachingEnabled = cache;
        this.classLoader = classLoader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends @NonNull Object> @NonNull TypeDefinition<@NonNull T> reflect(
            final @NonNull Class<@NonNull T> cls) {

        final TypeDefinition<?> cachedTypeDefinition = typeDefinitionCache.get(cls);

        if (cachedTypeDefinition != null) {
            return (TypeDefinition<T>) cachedTypeDefinition;
        }

        final TypeDefinition<T> typeDefinition = new TypeDefinitionImpl<>(this, cls);

        if (cachingEnabled) {
            typeDefinitionCache.put(cls, typeDefinition);
        }

        return typeDefinition;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends @NonNull Object> @NonNull T createProxy(
            final @NonNull Class<@NonNull T> type,
            final @NonNull InvocationHandler<T> handler) {

        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(handler, "handler cannot be null");

        final TypeDefinition<T> typeDefinition = reflect(type);

        return (T) Proxy.newProxyInstance(classLoader,
                new Class<?>[] {type},
                (proxy, method, args) -> handler.invoke((T) proxy,
                        new MethodImpl<>(typeDefinition, method),
                        args == null ?
                                EMPTY_OBJECT_ARRAY :
                                args));
    }

    static final class BuilderImpl implements Builder {

        private ClassLoader classLoader;
        private boolean cache = true;

        @Override
        public @NonNull @This Builder classLoader(@Nullable ClassLoader classLoader) {

            this.classLoader = classLoader;
            return this;
        }

        @Override
        public @NonNull @This Builder cache(boolean cache) {

            this.cache = cache;
            return this;
        }

        @Override
        public @NonNull Mirror build() {

            if (classLoader == null && cache) {
                return Instance.INSTANCE;
            }

            return new MirrorImpl(cache, classLoader == null ? DEFAULT_CLASS_LOADER : classLoader);
        }
    }
}
