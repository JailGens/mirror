package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Field<R extends @NonNull Object, T extends @Nullable Object> extends Member<@NonNull R>, Typed<T> {
}
