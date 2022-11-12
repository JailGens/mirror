package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class MirrorTest {

    Mirror mirror;

    @BeforeEach
    void setUp() {

        mirror = Mirror.builder().classLoader(MirrorTest.class.getClassLoader()).build();
    }

    @SuppressWarnings("unchecked")
    @Test
    void Given_Mirror_When_CreateProxy_Then_ReturnsProxy() throws Throwable {

        final InvocationHandler<AnnotationElement> invocationHandler = new InvocationHandler<>() {
            @Override
            public <R> @NonNull R invoke(@NonNull AnnotationElement receiver,
                                         @NonNull Method<@NonNull AnnotationElement, R> method,
                                         @Nullable Object @NonNull ... args) throws Throwable {

                return (R) "test";
            }
        };
        final InvocationHandler<AnnotationElement> spy = spy(invocationHandler);
        final AnnotationElement proxy = mirror.createProxy(AnnotationElement.class, spy);

        final String annotationType = proxy.getAnnotationType();

        verify(spy).invoke(
                proxy,
                mirror.reflect(AnnotationElement.class)
                        .getMethods()
                        .stream()
                        .filter((method) -> method.getName().equals("getAnnotationType"))
                        .findFirst()
                        .orElseThrow(AssertionError::new));
    }

    @Test
    void Given_NonInterfaceClass_When_CreateProxy_Then_ThrowsException() {

        assertThrows(IllegalArgumentException.class,
                () -> mirror.createProxy(Exception.class, Mockito.mock(InvocationHandler.class)));
    }
}
