package net.jailgens.mirror;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionsTest {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {}

    @Test
    void When_CreateAnnotationValues_Then_ReturnsAllAnnotations() {

        final AnnotationValues annotationValues = Reflections.createAnnotationValues(TestAnnotation.class);

        assertEquals(AnnotationValues.builder()
                .annotate(Documented.class)
                .value(AnnotationElement.value(Retention.class), RetentionPolicy.RUNTIME)
                .build(), annotationValues);
    }
}
