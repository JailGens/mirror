package net.jailgens.mirror;

/**
 * A parameter.
 *
 * @author Sparky983
 * @param <T> the type of the parameter.
 * @since 0.0.0
 */
public interface Parameter<T> extends Annotated, Member, Typed<T> {}
