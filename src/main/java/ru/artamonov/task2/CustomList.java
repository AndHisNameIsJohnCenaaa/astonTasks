package ru.artamonov.task2;


import java.util.Collection;
import java.util.Comparator;

public interface CustomList<E> extends Iterable<E> {
	void add(int index, E element);

	boolean addAll(Collection<? extends E> c);

	void clear();

	E get(int index);

	boolean isEmpty();

	E remove(int index);

	boolean remove(Object o);

	int size();

	void sort(Comparator<? super E> c);

}
