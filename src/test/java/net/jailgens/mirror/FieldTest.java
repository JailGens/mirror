package net.jailgens.mirror;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
class FieldTest {

    Mirror mirror;

    @SuppressWarnings("unchecked")
    <R extends @NonNull Object, T extends @NonNull Object>
    @NonNull Field<@NonNull R, @NonNull T> reflectField(
            final @NonNull Class<@NonNull R> declaringClass, final @NonNull String name) {

        return (Field<R, T>) mirror.reflect(declaringClass)
                .getFields()
                .stream()
                .filter((field) -> field.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Field \"" + name + "\" not found"));
    }

    @BeforeEach
    void setUp() {

        mirror = Mirror.builder().build();
    }

    @Test
    void Given_InstanceField_When_GetOnInstance_Then_ReturnValueOfField() {

        class TestClass {

            final String field = "value";
        }
        final Field<TestClass, String> field = reflectField(TestClass.class, "field");

        final String value = field.get(new TestClass());

        assertEquals("value", value);
    }

    @Test
    void Given_InstanceField_When_GetOnNull_Then_Throws() {

        class TestClass {

            final String field = "value";
        }
        final Field<TestClass, String> field = reflectField(TestClass.class, "field");

        assertThrows(NullPointerException.class, () -> field.get(null));
    }

    @Test
    void Given_StaticField_When_GetOnNull_Then_ReturnValueOfField() {

        class TestClass {

            static final String FIELD = "value";
        }
        final Field<TestClass, String> field = reflectField(TestClass.class, "FIELD");

        final String value = field.get(null);

        assertEquals("value", value);
    }

    @Test
    void Given_StaticField_When_GetOnInstance_Then_ReturnValueOfField() {

        class TestClass {

            private static final String FIELD = "value";
            // field is private to remove instantiation of utility class warning
        }
        final Field<TestClass, String> field = reflectField(TestClass.class, "FIELD");

        final String value = field.get(new TestClass());

        assertEquals("value", value);
    }

    @Test
    void Given_FinalField_When_Set_Then_Throws() {

        class TestClass {

            static final String FIELD = "value";
        }
        final Field<TestClass, String> field = reflectField(TestClass.class, "FIELD");

        assertThrows(IllegalStateException.class, () -> field.set(null, "new value"));
        assertEquals("value", TestClass.FIELD);
        assertEquals("value", field.get(null));
    }

    @Test
    void Given_InstanceField_When_SetOnInstance_Then_SetsField() {

        class TestClass {

            String field = "value";
        }
        final Field<TestClass, String> field = reflectField(TestClass.class, "field");
        final TestClass instance = new TestClass();

        field.set(instance, "new value");

        assertEquals("new value", instance.field);
    }

    @Test
    void Given_InstanceField_When_SetOnNull_Then_Throws() {

        class TestClass {

            String field = "value";
        }
        final Field<TestClass, String> field = reflectField(TestClass.class, "field");

        assertThrows(NullPointerException.class, () -> field.set(null, "new value"));
    }

    static class StaticFieldTestClass {

        private static String field = "value";
        // field is private to remove instantiation of utility class warning
    }

    @Test
    void Given_StaticField_When_SetOnNull_Then_SetsField() {

        final Field<StaticFieldTestClass, String> field =
                reflectField(StaticFieldTestClass.class, "field");

        field.set(null, "new value");

        assertEquals("new value", StaticFieldTestClass.field);
    }

    @Test
    void Given_StaticField_When_SetOnInstance_Then_SetsField() {

        final Field<StaticFieldTestClass, String> field =
                reflectField(StaticFieldTestClass.class, "field");

        field.set(new StaticFieldTestClass(), "new value");

        assertEquals("new value", StaticFieldTestClass.field);
    }

    @Test
    void Given_Field_When_GetName_Then_ReturnsName() {

        class TestClass {

            String fieldName;
        }
        final Field<?, ?> field = mirror.reflect(TestClass.class)
                .getFields()
                .stream()
                .findFirst()
                .orElseThrow(AssertionError::new);

        final String fieldName = field.getName();

        assertEquals("fieldName", fieldName);
    }

    static class ModifiersTestClass {

        public static volatile String volatileField;
        private final String finalField = "value";
        protected transient String protectedField;
    }

    @Test
    void Given_Field_When_GetModifiers_Then_ReturnsModifiers() {

        final Field<ModifiersTestClass, String> volatileField =
                reflectField(ModifiersTestClass.class, "volatileField");
        final Field<ModifiersTestClass, String> finalField =
                reflectField(ModifiersTestClass.class, "finalField");
        final Field<ModifiersTestClass, String> protectedField =
                reflectField(ModifiersTestClass.class, "protectedField");

        final Set<Modifier> volatileFieldModifiers = volatileField.getModifiers();
        final Set<Modifier> finalFieldModifiers = finalField.getModifiers();
        final Set<Modifier> protectedFieldModifiers = protectedField.getModifiers();

        assertEquals(Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.VOLATILE),
                volatileFieldModifiers);
        assertEquals(Set.of(Modifier.PRIVATE, Modifier.FINAL), finalFieldModifiers);
        assertEquals(Set.of(Modifier.PROTECTED, Modifier.TRANSIENT), protectedFieldModifiers);
    }

    @Test
    void Given_Field_When_GetDeclarationType_Then_ReturnsDeclaringType() {

        class TestClass {

            String field;
        }
        final TypeDefinition<TestClass> typeDefinition = mirror.reflect(TestClass.class);
        final Field<TestClass, ?> field = typeDefinition.getFields()
                .stream()
                .findAny()
                .orElseThrow(AssertionError::new);

        assertEquals(typeDefinition, field.getDeclaringType());
    }

    @Test
    void Given_Field_When_GetRawDeclaringType_Then_ReturnsRawDeclaringType() {

        class TestClass {

            String field;
        }
        final Field<TestClass, ?> field = reflectField(TestClass.class, "field");

        final Class<TestClass> rawDeclaringType = field.getRawDeclaringType();

        assertEquals(TestClass.class, rawDeclaringType);
    }

    @Test
    void Given_Field_When_GetType_Then_ReturnsType() {

        class TestClass {

            List<String> field;
        }
        final Field<TestClass, ?> field = reflectField(TestClass.class, "field");

        final ParameterizedType<?> type = field.getType();

        /*
         can't get type argument <String> because of type eraser (well technically we can, but it
         requires a lot of casting and messy code).
         */
        assertEquals(ParameterizedType.of(List.class), type);
    }

    @Test
    void Given_Field_When_GetRawType_Then_ReturnsRawType() {

        class TestClass {

            String field;
        }
        final Field<TestClass, ?> field = reflectField(TestClass.class, "field");

        final Class<?> type = field.getRawType();

        assertEquals(String.class, type);
    }
}
