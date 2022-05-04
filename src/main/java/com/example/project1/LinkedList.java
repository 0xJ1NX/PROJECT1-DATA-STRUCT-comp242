package com.example.project1;


import java.util.Iterator;

public class LinkedList<T extends Comparable<T>> {

    private Node<T> head;

    public void insertSort(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            Node<T> previous = null;
            while (current != null && current.getData().compareTo(data) < 0) {
                previous = current;
                current = current.getNext();
            }
            if (previous == null) {
                newNode.setNext(head);
                head = newNode;
            } else {
                newNode.setNext(current);
                previous.setNext(newNode);
            }
        }
    }

    public void delete(T data) {
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null && current.getData().compareTo(data) < 0) {
            previous = current;
            current = current.getNext();
        }
        if (current != null && current.getData().compareTo(data) == 0) {
            if (previous == null) {
                head = current.getNext();
            } else {
                previous.setNext(current.getNext());
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        while (current != null) {
            sb.append(current.getData());
            current = current.getNext();
        }
        return sb.toString();
    }



    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    public int size() {
        int size = 0;
        Node<T> current = head;
        while (current != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current;
        public LinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T data = current.getData();
            current = current.getNext();
            return data;
        }

    }
}
