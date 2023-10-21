package ru.sberbank.edu.impl;

import ru.sberbank.edu.CustomArray;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomArrayImpl<T> implements CustomArray<T> {

    /**
     * Дефолтный размер массива.
     * Маленькое значение взято из-за удобности тестирования (в ArrayList - 10)
     */
    static final int DEFAULT_CAPACITY = 1;

    /**
     * Буффер массива, в котором будут храниться элементы CustomArrayImpl
     */
    Object[] elements;

    /**
     * Кол-во элементов в массиве
     */
    private int size;

    /**
     * Конструктор при наличии аргумента
     * @param initialCapacity размер массива
     */
    public CustomArrayImpl(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elements = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elements = new Object[0];
        } else {
            throw new IllegalArgumentException("Размер не может быть отрицательным: " +
                    initialCapacity);
        }
    }

    /**
     * Конструктор без аргументов
     * Иницииализируем массив с нулевым разером
     */
    public CustomArrayImpl() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Копирует элементы старого массива в копию с новой мощностью.
     * @param newCapacity - новая мощность массива
     * @return копию изначального массива с бОльшей мощностью.
     */
    private Object[] grow(int newCapacity) {

        Object[] copy = new Object[newCapacity];

        System.arraycopy(elements, 0, copy, 0,
                Math.min(elements.length, newCapacity));

        return copy;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T item) {
        if (item != null) {
            if (size < elements.length) {
                elements[size] = item;
                size += 1;
            } else {
                elements = grow(size + 1);
                add(item);
            }
            return true;
        } else {
            throw new IllegalArgumentException("Value of element can't be null or < 0");
        }
    }

    @Override
    public boolean addAll(T[] items) {
        if (items != null) {
            if (size + items.length <= elements.length) {
                for (T elem: items) {
                    add(elem);
                }
                return true;
            } else {
                elements = grow(size + items.length);
                addAll(items);
            }
        } else {
            throw new IllegalArgumentException("Value of elements can't be null");
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<T> items) {
        try {
            if (items != null || !items.isEmpty()) {
                return addAll((T[]) items.toArray());
            } else {
                throw new IllegalArgumentException("Value of elements can't be null");
            }
        } catch (NullPointerException npe) {
            throw new NullPointerException(npe.getMessage());
        }
    }

    @Override
    public boolean addAll(int index, T[] items) {
        if (index == size) {
            /**
             * Просто вставляю в конец массив
             */
            addAll(items);
            return true;
        } else if (index > size) {
            throw new ArrayIndexOutOfBoundsException("Индекс больше размера массива");
        } else if (items == null) {
            throw new IllegalArgumentException("Массив не можеть быть пустым (null)");
        } else {
            final int newLength = size + items.length;
            Object[] newElements;
            if (newLength < elements.length) {
                newElements = new Object[elements.length];
            } else {
                newElements = new Object[newLength];
            }
            /**
             * Копирую элементы из исходного массива с 0 позиции длинной index в начало нового массива
             */
            System.arraycopy(elements, 0, newElements, 0, index);
            /**
             * Копирую все элементы из items в конец формиующегося массива
             */
            System.arraycopy(items, 0, newElements, index, items.length);
            /**
             * Дозополняю элементами из исходного массива от index до его величины (size).
             */
            System.arraycopy(elements, index, newElements, index+items.length, size - index);
            elements = newElements;
            size += items.length;
            return true;
        }
    }

    @Override
    public T get(int index) {
        if (index < size) {
            return (T) elements[index];
        } else {
            throw new ArrayIndexOutOfBoundsException("Индекс больше размера массива");
        }
    }

    @Override
    public T set(int index, T item) {
        if (index < size) {
            T oldValue = (T) elements[index];
            elements[index] = item;
            return oldValue;
        } else {
            throw new ArrayIndexOutOfBoundsException("Индекс больше размера массива");
        }
    }

    @Override
    public void remove(int index) {
        if (index < size) {
            Object[] newElements = elements;
            System.arraycopy(newElements, index + 1, newElements, index, size() - 1 - index);
            newElements[size = size - 1] = null;
            elements = newElements;
        } else {
            throw new ArrayIndexOutOfBoundsException("Индекс больше размера массива");
        }
    }

    @Override
    public boolean remove(T item) {

        for (int i = 0; i < size; i++) {
            if (elements[i] == item) {
                remove(i);
                return true;
            }
        }

        throw new NoSuchElementException("Этого элемента нет в массиве.");
    }

    @Override
    public boolean contains(T item) {
        for (Object elem: elements) {
            if (elem == item) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == item) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void ensureCapacity(int newElementsCount) {

        if (newElementsCount < size) {
            throw new IllegalArgumentException("Новый размер массива не может быть меньше нынешнего");
        } else {
            elements = grow(newElementsCount);
        }
    }

    @Override
    public int getCapacity() {
        return elements.length;
    }

    @Override
    public void reverse() {
        Object[] newArray = new Object[elements.length];

        for (int i = 0; i < elements.length; i++) {
            newArray[elements.length - 1 - i] = elements[i];
        }

        elements = newArray;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i == size - 1) {
                sb.append("]");
            } else {
                sb.append(", ");
            }
        }

        return  sb.toString();
    }

    @Override
    public Object[] toArray() {
        return grow(size);
    }
}
