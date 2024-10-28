package ru.artamonov.task2;

import ru.artamonov.task2.util.SortUtil;

import java.util.*;


public class CustomArrayList<E> implements CustomList<E> {

	private static final int DEFAULT_CAPACITY = 10;

	private E[] innerArray;

	private int size;

	private int modCount;

	@SuppressWarnings("unchecked")
	public CustomArrayList() {
		innerArray = (E[]) new Object[DEFAULT_CAPACITY];
	}

	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		modCount++;
		if (size == innerArray.length) {
			innerArray = grow(1);
		}
		System.arraycopy(innerArray, index,
				innerArray, index + 1,
				size - index);
		innerArray[index] = element;
		size++;
	}

	private void rangeCheckForAdd(int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		@SuppressWarnings("unchecked")
		E[] newArray = (E[]) c.toArray();
		modCount++;
		int newArraySize = newArray.length;
		if (newArraySize == 0) {
			return false;
		}
		if (newArraySize > innerArray.length - size) {
			innerArray = grow(newArraySize);
		}
		System.arraycopy(newArray, 0, innerArray, size, newArraySize);
		size += newArraySize;
		return true;
	}

	@Override
	public void clear() {
		modCount++;
		for (int to = size, i = size = 0; i < to; i++)
			innerArray[i] = null;
	}

	@Override
	public E get(int index) {
		Objects.checkIndex(index, size);
		return innerArray[index];
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E remove(int index) {
		Objects.checkIndex(index, size);
		E oldValue = innerArray[index];
		fastRemove(index);
		return oldValue;
	}

	@Override
	public boolean remove(Object o) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			if(Objects.equals(o, innerArray[i])) {
				index = i;
			}
		}
		if (index != -1) {
			fastRemove(index);
			return true;
		}
		return false;
	}

	private void fastRemove(int index) {
		modCount++;
		System.arraycopy(innerArray, index + 1,
				innerArray, index, size - index);
		size--;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void sort(Comparator<? super E> c) {
		SortUtil.quickSort(innerArray, 0, size - 1, c);
	}

	private E[] grow(int minCapacity) {
		int oldCapacity = innerArray.length;
		int newCapacity = oldCapacity + Math.max(oldCapacity >> 1, minCapacity) + 1;
		@SuppressWarnings("unchecked")
		E[] newArray = (E[]) new Object[newCapacity];
		System.arraycopy(innerArray, 0, newArray, 0, size);
		return newArray;
	}


	@Override
	public Iterator<E> iterator() {
		return new ArrayIterator();
	}

	private class ArrayIterator implements Iterator<E> {
		private int currentIndex = 0;
		private final int expectedModCount = modCount;
		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public E next() {
			checkForComodification();
			return innerArray[currentIndex++];
		}

		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0; i < size; i++) {
			builder.append(innerArray[i]);
			if(i < size - 1) {
				builder.append(", ");
			}
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CustomArrayList<?> that = (CustomArrayList<?>) o;
		return Objects.deepEquals(innerArray, that.innerArray);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(innerArray);
	}
}
