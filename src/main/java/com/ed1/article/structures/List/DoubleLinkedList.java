package com.ed1.article.structures.List;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

public class DoubleLinkedList<T> implements InterfaceDoubleLinkedList<T> {

	@JsonIgnore
	Node<T> first;
	
	@JsonIgnore
	Node<T> last;
	
	@JsonIgnore
	int size = 0;

	public T getFirst() {
		if (first == null) {
			System.out.println("Lista Vazia!!!");
			return null;
		}
		return first.data;
	}

	public T getLast() {
		if (last == null) {
			System.out.println("Lista Vazia!!!");
			return null;
		}
		return last.data;
	}
	
	@JsonValue(value = true)
	@SuppressWarnings("unchecked")
	public T[] getAll() {
		Object[] listAll = new Object[size];
		
		Node<T> indexNode = first;
		int indexList = 0;
		
		// Percorrer toda a lista
		if(indexNode != null) {
			do {
				listAll[indexList] = indexNode.data;
				indexList++;
				indexNode = indexNode.next;
			} while(indexNode != null);
		}
		
		return (T[]) listAll;
	}

	public T removeFirst() {
		Node<T> responseNode = first;

		if (first == null) {
			System.out.println("Lista Vazia!!!");
			return null;
		} else {
			if (first == last) {
				first = null;
				last = null;
			} else {
				first = first.next;
				first.prev = null;
			}
		}
		responseNode.next = null;
		size--;
		return responseNode.data;
	}

	public T removeLast() {
		Node<T> response = last;

		if (last == null) {
			System.out.println("Lista Vazia!!!");
			return null;
		} else {
			if (first == last) {
				first = null;
				last = null;
			} else {
				last = last.prev;
				last.next = null;
			}
		}
		size--;
		return response.data;
	}

	public void addFirst(T element) {
		Node<T> elementNode = new Node<T>(element);

		if (first == null) {
			first = elementNode;
			last = elementNode;
		} else {
			elementNode.next = first;
			first.prev = elementNode;
			first = elementNode;
		}
		size++;
	}

	public void addLast(T element) {
		Node<T> elementNode = new Node<T>(element);

		if (last == null) {
			first = elementNode;
			last = elementNode;
		} else {
			last.next = elementNode;
			elementNode.prev = last;
			last = elementNode;
		}
		size++;
	}

	public boolean addAll(Collection<? extends T> collection) {
		for (T t : collection) {
			this.addLast(t);
		}
		return true;
	}

}
