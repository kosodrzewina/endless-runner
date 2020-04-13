package com.company;

public class GeometricFigureList {
    private Node head;

    public void add(GeometricFigure geometricFigure) {
        if (head == null)
            head = new Node(geometricFigure);
        else {
            Node temp = head;

            while (temp.next != null)
                temp = temp.next;

            temp.next = new Node(geometricFigure);
        }
    }

    public int size() {
        int size = 0;
        Node temp = head;

        while (temp != null) {
            temp = temp.next;
            size++;
        }

        return size;
    }

    public GeometricFigure getAt(int position) throws IndexOutOfBoundsException {
        Node temp = head;
        int currentPosition = 0;

        while (temp != null && currentPosition < position) {
            temp = temp.next;
            currentPosition++;
        }

        if (currentPosition == position && temp != null)
            return temp.geometricFigure;
        else
            throw new IndexOutOfBoundsException();
    }

    public void deleteFrom(int i) {
        Node temp = head;
        int currentPosition = 0;

        while (temp != null && currentPosition < i) {
            temp = temp.next;
            currentPosition++;
        }

        head = temp.next;
    }

    public Node getHead() {
        return head;
    }
}
