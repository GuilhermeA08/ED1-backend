package com.ed1.article.structures.List;

import java.util.Collection;

public interface InterfaceDoubleLinkedList<T> {
  public T getFirst();

  public T getLast();

  public T removeFirst();

  public T removeLast();

  public void addFirst(T element);

  public void addLast(T element);

  public boolean addAll(Collection<? extends T> collection);
  
  public boolean isEmpty();
}
