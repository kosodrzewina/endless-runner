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

    public Node getHead() {
        return head;
    }
}
