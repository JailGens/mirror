package net.jailgens.mirror;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParameterizedTypeTest {

    @SuppressWarnings({"EqualsWithItself", "ConstantConditions"})
    @Test
    void Given_ParameterizedType_When_CompareWithSelf_Then_ReturnsTrue() {

        final ParameterizedType<?> parameterizedType = ParameterizedType.of(String.class);

        final boolean equal = parameterizedType.equals(parameterizedType);

        assertTrue(equal);
    }

    @Test
    void Given_ParameterizedType_When_CompareWithObjectOfDifferentType_Then_ReturnsFalse() {

        final ParameterizedType<?> parameterizedType = ParameterizedType.of(String.class);

        final boolean equal = parameterizedType.equals(new Object());

        assertFalse(equal);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_ParameterizedType_When_CompareWithNull_Then_ReturnsFalse() {

        final ParameterizedType<?> parameterizedType = ParameterizedType.of(String.class);

        final boolean equal = parameterizedType.equals(null);

        assertFalse(equal);
    }

    @Test
    void Given_ParameterizedTypes_When_HashCode_Then_ReturnsSameHashCode() {

        final ParameterizedType<?> parameterizedType1 = ParameterizedType.of(String.class);
        final ParameterizedType<?> parameterizedType2 = ParameterizedType.of(String.class);

        final int hashCode1 = parameterizedType1.hashCode();
        final int hashCode2 = parameterizedType2.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void Given_DifferentParameterizedTypes_When_HashCode_Then_ReturnsDifferentHashCode() {

        final ParameterizedType<?> parameterizedType1 = ParameterizedType.of(String.class);
        final ParameterizedType<?> parameterizedType2 = ParameterizedType.of(Integer.class);

        final int hashCode1 = parameterizedType1.hashCode();
        final int hashCode2 = parameterizedType2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    void When_MapOfStringToString_Then_ReturnsParameterizedTypeOfStringToString() {

        final ParameterizedType<Map<String, Object>> map =
                ParameterizedType.mapOf(String.class, Object.class);

        assertEquals(Map.class, map.getRawType());
        assertEquals(ParameterizedType.of(String.class), map.getTypeArguments().get(0));
        assertEquals(ParameterizedType.of(Object.class), map.getTypeArguments().get(1));
    }

    @Test
    void When_ListOf_Then_ReturnsListOfSpecifiedParameterizedType() {

        final ParameterizedType<List<String>> list = ParameterizedType.listOf(String.class);

        assertEquals(List.class, list.getRawType());
        assertEquals(ParameterizedType.of(String.class), list.getTypeArguments().get(0));
    }

    @Test
    void When_SetOf_Then_ReturnsSetOfSpecifiedParameterizedType() {

        final ParameterizedType<Set<String>> set = ParameterizedType.setOf(String.class);

        assertEquals(Set.class, set.getRawType());
        assertEquals(ParameterizedType.of(String.class), set.getTypeArguments().get(0));
    }

    @Test
    void When_CollectionOf_Then_ReturnsCollectionOfSpecifiedParameterizedType() {

        final ParameterizedType<Collection<String>> set =
                ParameterizedType.collectionOf(String.class);

        assertEquals(Collection.class, set.getRawType());
        assertEquals(ParameterizedType.of(String.class), set.getTypeArguments().get(0));
    }
}
