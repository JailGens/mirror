package net.jailgens.mirror;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModifierTest {

    @Test
    void Given_Modifier_When_ToString_Then_ReturnsKeyWord() {

        final String publicString = Modifier.PUBLIC.toString();
        final String privateString = Modifier.PRIVATE.toString();
        final String protectedString = Modifier.PROTECTED.toString();
        final String staticString = Modifier.STATIC.toString();
        final String finalString = Modifier.FINAL.toString();
        final String abstractString = Modifier.ABSTRACT.toString();
        final String synchronizedString = Modifier.SYNCHRONIZED.toString();
        final String nativeString = Modifier.NATIVE.toString();
        final String strictfpString = Modifier.STRICTFP.toString();
        final String transientString = Modifier.TRANSIENT.toString();
        final String volatileString = Modifier.VOLATILE.toString();

        assertEquals("public", publicString);
        assertEquals("private", privateString);
        assertEquals("protected", protectedString);
        assertEquals("static", staticString);
        assertEquals("final", finalString);
        assertEquals("abstract", abstractString);
        assertEquals("synchronized", synchronizedString);
        assertEquals("native", nativeString);
        assertEquals("strictfp", strictfpString);
        assertEquals("transient", transientString);
        assertEquals("volatile", volatileString);
    }

    @Test
    void Given_Modifier_When_Is_Then_ReturnsTrueIfIsModifier() {

        final int allModifiers = Integer.MAX_VALUE;

        final boolean isPublic = Modifier.PUBLIC.in(allModifiers);
        final boolean isPrivate = Modifier.PRIVATE.in(allModifiers);
        final boolean isProtected = Modifier.PROTECTED.in(allModifiers);
        final boolean isStatic = Modifier.STATIC.in(allModifiers);
        final boolean isFinal = Modifier.FINAL.in(allModifiers);
        final boolean isAbstract = Modifier.ABSTRACT.in(allModifiers);
        final boolean isSynchronized = Modifier.SYNCHRONIZED.in(allModifiers);
        final boolean isNative = Modifier.NATIVE.in(allModifiers);
        final boolean isStrictfp = Modifier.STRICTFP.in(allModifiers);
        final boolean isTransient = Modifier.TRANSIENT.in(allModifiers);
        final boolean isVolatile = Modifier.VOLATILE.in(allModifiers);

        assertTrue(isPublic);
        assertTrue(isPrivate);
        assertTrue(isProtected);
        assertTrue(isStatic);
        assertTrue(isFinal);
        assertTrue(isAbstract);
        assertTrue(isSynchronized);
        assertTrue(isNative);
        assertTrue(isStrictfp);
        assertTrue(isTransient);
        assertTrue(isVolatile);
    }

    @Test
    void Given_Modifier_When_Is_Then_ReturnsFalseWhenIsNotModifier() {

        final int allModifiers = Integer.MIN_VALUE;

        final boolean isPublic = Modifier.PUBLIC.in(allModifiers);
        final boolean isPrivate = Modifier.PRIVATE.in(allModifiers);
        final boolean isProtected = Modifier.PROTECTED.in(allModifiers);
        final boolean isStatic = Modifier.STATIC.in(allModifiers);
        final boolean isFinal = Modifier.FINAL.in(allModifiers);
        final boolean isAbstract = Modifier.ABSTRACT.in(allModifiers);
        final boolean isSynchronized = Modifier.SYNCHRONIZED.in(allModifiers);
        final boolean isNative = Modifier.NATIVE.in(allModifiers);
        final boolean isStrictfp = Modifier.STRICTFP.in(allModifiers);
        final boolean isTransient = Modifier.TRANSIENT.in(allModifiers);
        final boolean isVolatile = Modifier.VOLATILE.in(allModifiers);

        assertFalse(isPublic);
        assertFalse(isPrivate);
        assertFalse(isProtected);
        assertFalse(isStatic);
        assertFalse(isFinal);
        assertFalse(isAbstract);
        assertFalse(isSynchronized);
        assertFalse(isNative);
        assertFalse(isStrictfp);
        assertFalse(isTransient);
        assertFalse(isVolatile);
    }
}
