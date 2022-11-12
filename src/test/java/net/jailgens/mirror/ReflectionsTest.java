package net.jailgens.mirror;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionsTest {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface TestAnnotation {

    }

    @Test
    void When_CreateAnnotationValues_Then_ReturnsAllAnnotations() {

        final AnnotationValues annotationValues = Reflections.createAnnotationValues(TestAnnotation.class);

        assertEquals(AnnotationValues.builder()
                .annotate(Documented.class)
                .value(AnnotationElement.value(Retention.class), RetentionPolicy.RUNTIME)
                .value(AnnotationElement.value(Target.class), new ElementType[]{ElementType.ANNOTATION_TYPE})
                .build(), annotationValues);
    }
}
