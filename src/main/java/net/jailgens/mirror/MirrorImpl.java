package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;

final class MirrorImpl implements Mirror {

    @Override
    public <T extends @NonNull Object> @NonNull TypeDefinition<T> reflect(
            final @NonNull Class<T> cls) {

        return new TypeDefinitionImpl<>(cls);
    }

    static final class BuilderImpl implements Builder {

        @Override
        public @NonNull Mirror build() {

            return new MirrorImpl();
        }
    }
}
