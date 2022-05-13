package com.ed1.article.structures.List;

public class DoubleLinkedList<T> implements InterfaceDoubleLinkedList<T> {

  Node first;
  Node last;
  int size;

  /**
   * Node
   */
  public class Node {

    T data;
    Node prev;
    Node next;

    public Node(T data) {
      this.data = data;
      this.prev = null;
      this.next = null;
    }
  }

  @Override
  public T getFirst() {
    if (first == null) {
      System.out.println("Lista Vazia!!!");
      return null;
    }
    return first.data;
  }

  @Override
  public T getLast() {
    if (last == null) {
      System.out.println("Lista Vazia!!!");
      return null;
    }
    return last.data;
  }

  @Override
  public T removeFirst() {
    Node responseNode = first;

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

  @Override
  public T removeLast() {
    Node response = last;

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

  @Override
  public void addFirst(T element) {
    Node elementNode = new Node(element);

    if (first == null) {
      first = elementNode;
      last = elementNode;
    } else {
      elementNode.next = first;
      first.prev = elementNode;
      first = elementNode;
    }
  }

  @Override
  public void addLast(T element) {
    Node elementNode = new Node(element);

    if (last == null) {
      first = elementNode;
      last = elementNode;
    } else {
      last.next = elementNode;
      elementNode.prev = last;
      last = elementNode;
    }
  }
}
