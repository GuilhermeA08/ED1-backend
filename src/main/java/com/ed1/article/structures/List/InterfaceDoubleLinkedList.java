package com.ed1.article.structures.List;

public interface InterfaceDoubleLinkedList<T> {
  public T getFirst();

  public T getLast();

  public T removeFirst();

  public T removeLast();

  public void addFirst(T element);

  public void addLast(T element);
}
