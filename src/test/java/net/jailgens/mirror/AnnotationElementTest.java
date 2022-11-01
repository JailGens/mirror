package net.jailgens.mirror;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnnotationElementTest {

    @SuppressWarnings({"EqualsWithItself", "ConstantConditions"})
    @Test
    void Given_AnnotationElement_When_CompareWithSelf_Then_ReturnsTrue() {

        final AnnotationElement annotationElement = AnnotationElement.of("annotationType", "name");

        final boolean equal = annotationElement.equals(annotationElement);

        assertTrue(equal);
    }

    @Test
    void Given_AnnotationElement_When_CompareWithObjectOfDifferentType_Then_ReturnsFalse() {

        final AnnotationElement annotationElement = AnnotationElement.of("annotationType", "name");

        final boolean equal = annotationElement.equals(new Object());

        assertFalse(equal);
    }
}
