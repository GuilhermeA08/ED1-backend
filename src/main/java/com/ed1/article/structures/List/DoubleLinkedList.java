package com.ed1.article.structures.List;

import java.util.Collection;
import java.util.Iterator;

public class DoubleLinkedList<T> implements Iterable<Node<T>> {

  Node<T> first;
  Node<T> last;
  int size = 0;

  /**
   * Node
   */

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
  }

  public boolean addAll(Collection<? extends T> collection) {
    for (T t : collection) {
      this.addLast(t);
    }
    return true;
  }

  @Override
  public String toString() {
    return "test";
  }

  @Override
  public Iterator<Node<T>> iterator() {
    return new Iterator<Node<T>>() {
      Node<T> node = first;

      @Override
      public boolean hasNext() {
        if (node.next != null) {
          return true;
        }
        return false;
      }

      @Override
      public Node<T> next() {
        node = node.next;
        return node;
      }
    };
  }
}
