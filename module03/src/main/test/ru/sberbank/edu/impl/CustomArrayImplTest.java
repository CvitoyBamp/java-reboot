package ru.sberbank.edu.impl;

import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.sberbank.edu.impl.CustomArrayImpl.DEFAULT_CAPACITY;

class CustomArrayImplTest {

    CustomArrayImpl<String> customArrayString;
    CustomArrayImpl<Integer> customArrayInteger;

    /**
     * Инциализруем массивы разных типов перед тестами.
     */
    @BeforeEach
     void setUp() {
        customArrayString = new CustomArrayImpl<>();
        customArrayInteger = new CustomArrayImpl<>(10);
    }


    @Test
    @DisplayName("У пустого массива с элементами String размер = 0")
    void getSizeOfStringArray() {
        Assertions.assertThat(customArrayString.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("У пустого массива с элементами Integer размер = 0")
    void getSizeOfIntegerArray() {
        Assertions.assertThat(customArrayInteger.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Размер массива увеличен после добавления элементов (Integer)")
    void getSizeOfIntegerArrayAfterAddingData() {
        customArrayInteger.add(1);
        customArrayInteger.add(2);
        Assertions.assertThat(customArrayInteger.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Размер массива увеличен после добавления элементов (String)")
    void getSizeOfStringArrayAfterAddingData() {
        customArrayString.add("First");
        customArrayString.add("Second");
        Assertions.assertThat(customArrayString.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Нулевой размер массива без значений")
    void isEmpty() {
        Assertions.assertThat(customArrayString.isEmpty()).isTrue();
        Assertions.assertThat(customArrayInteger.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Значения добавлены в массив String")
    void addToStringArray() {
        customArrayString.add("First");
        customArrayString.add("Second");
        Assertions.assertThat(customArrayString.get(0)).isEqualTo("First").isNotNull().isInstanceOf(String.class);
        Assertions.assertThat(customArrayString.get(1)).isEqualTo("Second").isNotNull().isInstanceOf(String.class);
    }

    @Test
    @DisplayName("Значения добавлены в массив Integer")
    void addToIntegerArray() {
        customArrayInteger.add(1);
        customArrayInteger.add(2);
        Assertions.assertThat(customArrayInteger.get(0)).isEqualTo(1).isNotNull().isInstanceOf(Integer.class);
        Assertions.assertThat(customArrayInteger.get(1)).isEqualTo(2).isNotNull().isInstanceOf(Integer.class);
    }

    @Test
    @DisplayName("При добавлении null выбрасывается IllegalArgumentException")
    void tryAddNullValue() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> customArrayInteger.add(null));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> customArrayString.add(null));
    }

    @Test
    @DisplayName("Увеличение мощности массива при добавлении элементов")
    void afterAddingValueCapacityGrowth() {
        customArrayString.add("1");
        customArrayString.add("2");
        Assertions.assertThat(customArrayString.getCapacity()).isEqualTo(2);

        for (int i = 0; i < 15; i++) {
            customArrayInteger.add(i);
        }
        Assertions.assertThat(customArrayInteger.getCapacity()).isEqualTo(15);
    }

    @Test
    @DisplayName("Добавление массива элементов в массив String")
    void addAllToStringArray() {
        String[] strTestData = new String[]{"1", "2", "3", "4"};
        customArrayString.addAll(strTestData);

        Assertions.assertThat(customArrayString.size()).isEqualTo(4);
        Assertions.assertThat(customArrayString.getCapacity()).isEqualTo(4);
        Assertions.assertThat(customArrayString.get(0)).isEqualTo("1");
        Assertions.assertThat(customArrayString.get(1)).isEqualTo("2");
        Assertions.assertThat(customArrayString.get(2)).isEqualTo("3");
        Assertions.assertThat(customArrayString.get(3)).isEqualTo("4");
    }

    @Test
    @DisplayName("Добавление массива элементов в массив Integer")
    void addAllToIntegerArray() {
        Integer[] intTestData = new Integer[]{1, 2};
        customArrayInteger.addAll(intTestData);

        Assertions.assertThat(customArrayInteger.size()).isEqualTo(2);
        Assertions.assertThat(customArrayInteger.getCapacity()).isEqualTo(10);
        Assertions.assertThat(customArrayInteger.get(0)).isEqualTo(1);
        Assertions.assertThat(customArrayInteger.get(1)).isEqualTo(2);
    }

    @Test
    @DisplayName("При добавлении null методом addAll выбрасывается IllegalArgumentException")
    void addAllNullValue() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> customArrayInteger.addAll((Integer[]) null));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> customArrayString.addAll((String[]) null));
    }

    @Test
    @DisplayName("Добавление массива элементов Collection в массив String")
    void addAllToStringArrayCollectionValues() {
        String[] strTestData = new String[]{"1", "2", "3", "4"};
        Collection<String> col = new ArrayList<>();
        col.addAll(List.of(strTestData));
        customArrayString.addAll(col);

        Assertions.assertThat(customArrayString.size()).isEqualTo(4);
        Assertions.assertThat(customArrayString.getCapacity()).isEqualTo(4);
        Assertions.assertThat(customArrayString.get(0)).isEqualTo("1");
        Assertions.assertThat(customArrayString.get(1)).isEqualTo("2");
        Assertions.assertThat(customArrayString.get(2)).isEqualTo("3");
        Assertions.assertThat(customArrayString.get(3)).isEqualTo("4");
    }

    @Test
    @DisplayName("Добавление массива элементов Collection в массив Integer")
    void addAllToIntegerArrayCollectionValues() {
        Integer[] intTestData = new Integer[]{1, 2};
        Collection<Integer> col = new ArrayList<>();
        col.addAll(List.of(intTestData));
        customArrayInteger.addAll(intTestData);

        Assertions.assertThat(customArrayInteger.size()).isEqualTo(2);
        Assertions.assertThat(customArrayInteger.getCapacity()).isEqualTo(10);
        Assertions.assertThat(customArrayInteger.get(0)).isEqualTo(1);
        Assertions.assertThat(customArrayInteger.get(1)).isEqualTo(2);
    }

    @Test
    @DisplayName("Добавление массива элементов в массив String по индексу")
    void addAllToStringArrayByIndex() {
        String[] strTestData = new String[]{"1", "2", "3", "4"};
        customArrayString.add("начало ->");
        customArrayString.add("<- конец");
        customArrayString.addAll(1, strTestData);

        Assertions.assertThat(customArrayString.size()).isEqualTo(6);
        Assertions.assertThat(customArrayString.getCapacity()).isEqualTo(6);
        Assertions.assertThat(customArrayString.get(0)).isEqualTo("начало ->");
        Assertions.assertThat(customArrayString.get(1)).isEqualTo("1");
        Assertions.assertThat(customArrayString.get(2)).isEqualTo("2");
        Assertions.assertThat(customArrayString.get(3)).isEqualTo("3");
        Assertions.assertThat(customArrayString.get(4)).isEqualTo("4");
        Assertions.assertThat(customArrayString.get(5)).isEqualTo("<- конец");
    }

    @Test
    @DisplayName("Добавление массива элементов в массив Integer по индексу")
    void addAllToIntegerArrayByIndex() {
        Integer[] intTestData = new Integer[]{2, 3};
        customArrayInteger.add(1);
        customArrayInteger.add(4);
        customArrayInteger.addAll(1, intTestData);

        Assertions.assertThat(customArrayInteger.size()).isEqualTo(4);
        Assertions.assertThat(customArrayInteger.getCapacity()).isEqualTo(10);
        Assertions.assertThat(customArrayInteger.get(0)).isEqualTo(1);
        Assertions.assertThat(customArrayInteger.get(1)).isEqualTo(2);
        Assertions.assertThat(customArrayInteger.get(2)).isEqualTo(3);
        Assertions.assertThat(customArrayInteger.get(3)).isEqualTo(4);
    }

    @Test
    @DisplayName("Достать элемент по индексу в String-массиве")
    void getElementByIndexStringArray() {
        customArrayString.add("1");
        customArrayString.add("2");
        Assertions.assertThat(customArrayString.get(0)).isEqualTo("1");
        Assertions.assertThat(customArrayString.get(1)).isEqualTo("2");
    }

    @Test
    @DisplayName("Достать элемент по индексу в Integer-массиве")
    void getElementByIndexIntegerArray() {
        customArrayInteger.add(1);
        customArrayInteger.add(2);
        Assertions.assertThat(customArrayInteger.get(0)).isEqualTo(1);
        Assertions.assertThat(customArrayInteger.get(1)).isEqualTo(2);
    }

    @Test
    @DisplayName("ArrayIndexOutOfBoundsException при запросе индекса, который больше размера массива")
    void getElementByIndexOutOfBounds() {
        Assertions.assertThatIndexOutOfBoundsException().isThrownBy(() -> customArrayInteger.get(5));
    }

    @Test
    @DisplayName("Замена значения по индексу в String-массиве")
    void setValueByIndexStringArray() {
        customArrayString.add("1");
        customArrayString.set(0, "Новое значение");
        Assertions.assertThat(customArrayString.get(0)).isEqualTo("Новое значение");
    }

    @Test
    @DisplayName("Замена значения по индексу в Integer-массиве")
    void setValueByIndexIntegerArray() {
        customArrayInteger.add(1);
        customArrayInteger.set(0, 100);
        Assertions.assertThat(customArrayInteger.get(0)).isEqualTo(100);
    }

    @Test
    @DisplayName("Удаление значения по индексу в String-массиве")
    void removeElementByIndexStringArray() {
        customArrayString.add("1");
        customArrayString.add("2");
        customArrayString.add("3");
        customArrayString.remove(1);
        Assertions.assertThat(customArrayString.size()).isEqualTo(2);
        Assertions.assertThat(customArrayString.toString()).isEqualTo("[1, 3]");
    }

    @Test
    @DisplayName("Удаление значения по индексу в Integer-массиве")
    void removeElementByIndexIntegerArray() {
        customArrayInteger.add(1);
        customArrayInteger.add(2);
        customArrayInteger.add(3);
        customArrayInteger.remove(1);
        Assertions.assertThat(customArrayInteger.size()).isEqualTo(2);
        Assertions.assertThat(customArrayInteger.toString()).isEqualTo("[1, 3]");
    }

    @Test
    @DisplayName("Удаление значения по значению в String-массиве")
    void removeElementByNameStringArray() {
        customArrayString.add("1");
        customArrayString.add("2");
        customArrayString.add("3");
        customArrayString.remove("2");
        Assertions.assertThat(customArrayString.size()).isEqualTo(2);
        Assertions.assertThat(customArrayString.toString()).isEqualTo("[1, 3]");
    }

    @Test
    @DisplayName("NoSuchElementException при отсутствии элемента в массиве")
    void removeElementByNameExceptionStringArray() {
        customArrayString.add("1");
        Assertions.assertThatException().isThrownBy(() -> customArrayString.remove("2"));
    }

    @Test
    @DisplayName("Проверка налиия элемента в массиве")
    void elementExistInArrayByName() {
        customArrayString.add("1");
        customArrayString.add("2");
        customArrayString.add("3");
        Assertions.assertThat(customArrayString.contains("1")).isTrue();
        Assertions.assertThat(customArrayString.contains("такого нет")).isFalse();
    }

    @Test
    @DisplayName("Получение индекса по имени элемента")
    void getIndexByName() {
        customArrayString.add("элемент");
        Assertions.assertThat(customArrayString.indexOf("элемент")).isEqualTo(0);
        Assertions.assertThat(customArrayString.indexOf("не существующий элемент")).isEqualTo(-1);
    }

    @Test
    @DisplayName("Увеличение мощности массива")
    void ensureCapacity() {
        customArrayString.add("элемент");
        customArrayString.ensureCapacity(10);

        CustomArrayImpl mc = Mockito.mock(CustomArrayImpl.class);
        Mockito.when(mc.getCapacity()).thenReturn(10);

        Assertions.assertThat(customArrayString.getCapacity()).isEqualTo(10);
    }

    @Test
    @DisplayName("Возвращает мощность массива")
    void getCapacity() {
        Assertions.assertThat(customArrayString.getCapacity()).isEqualTo(1);
        Assertions.assertThat(customArrayInteger.getCapacity()).isEqualTo(10);
    }

    @Test
    @DisplayName("Возвращает элементы массива в обратном порядке")
    void reverse() {
        customArrayString.add("прямой");
        customArrayString.add("порядок");
        customArrayString.reverse();
        Assertions.assertThat(customArrayString.get(0)).isEqualTo("порядок");
        Assertions.assertThat(customArrayString.get(1)).isEqualTo("прямой");
    }

    @Test
    @DisplayName("ToString")
    void testToString() {
        Assertions.assertThat(customArrayString.toString()).isEqualTo("[]");
        customArrayString.add("1");
        customArrayString.add("2");
        customArrayString.add("3");
        Assertions.assertThat(customArrayString.toString()).isEqualTo("[1, 2, 3]");
    }

    @Test
    @DisplayName("toArray")
    void toArray() {
        customArrayString.add("1");
        customArrayString.add("2");
        customArrayString.add("3");
        Object[] arr = customArrayString.toArray();
        Assertions.assertThat(arr).isInstanceOf(Object.class);
        Assertions.assertThat(arr[0]).isEqualTo("1");
        Assertions.assertThat(arr.length).isEqualTo(3);
    }

}