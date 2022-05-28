package com.ed1.article.structures.List;

public class Node<T> {

  T data;
  Node<T> prev;
  Node<T> next;

  public Node(T data) {
    this.data = data;
    this.prev = null;
    this.next = null;
  }

  public Node(Node<T> node) {
    this.data = node.data;
    this.prev = null;
    this.next = null;
  }
}
