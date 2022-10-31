package net.jailgens.mirror;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
}
