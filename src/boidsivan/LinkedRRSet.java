/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boidsivan;

import java.util.Collection;

/**
 *
 * @author Ivan
 */
public class LinkedRRSet<E extends Comparable<E>> extends LinkedSet<E> {

    public LinkedRRSet() {
        super();
    }

    public LinkedRRSet(Collection<? extends E> c) {
        super(c);
    }

    @Override
    public boolean add(E o) {
        if (!this.contains(o)) {
            Node<E> newNode = new Node<E>(o);

            // if LinedSet is empty or element of firstNode is greater than element of argument (places to left of firstNode
            if (firstNode == null || firstNode.element.compareTo(o) > 0) {
                newNode.next = firstNode;
                firstNode = newNode;
            } else //places to right of firstNode or inbetween nodes
            {
                Node<E> currentNode = firstNode; // define a currentNode which references the  firstNode (seths look ahead method)

                while (currentNode.next != null && newNode.element.compareTo(currentNode.next.element) > 0) // loops thru nodes untill next node != null
                {
                    currentNode = currentNode.next; // moves up to the next node
                }
                // creates links when placing inbetween nodes
                newNode.next = currentNode.next;
                currentNode.next = newNode;

            }
            return true;
        } else {
            return false;
        }

    }

    public LinkedRRSet<E> retain(E start, E end) {

        LinkedRRSet<E> notRetained = new LinkedRRSet<>();

        Node<E> currentNode = this.firstNode; // create a node which starts at first element of set

        notRetained.add(currentNode.element);
        while (currentNode.next != null && !(currentNode.next.element.compareTo(start) == 0)) //move through set following links untill it finds "start" argument
        {
            currentNode = currentNode.next; // moves up a node

            notRetained.add(currentNode.element); //while moving up nodes, add thea current node to the set to be returned

        }

        firstNode = currentNode.next; // removes links from left of "start" node

        while (currentNode.next != null && !(currentNode.next.element.compareTo(end) == 0)) //move through set following links untill it finds "start" argument
        {
            currentNode = currentNode.next; // moves up a node

        }


        Node<E> tempNode = currentNode; // creates a temp node with reference to the current node to later nullify
        
        while (currentNode.next != null) //move through set following links untill it finds "end" argument
        {
            currentNode = currentNode.next; // moves up a node

            notRetained.add(currentNode.element);

        }

        tempNode.next = null; // removes links to right of "end" node

        return notRetained;
    }

    public static void main(String[] args) {

        LinkedRRSet<Integer> mySet = new LinkedRRSet();

        mySet.add(1);
        mySet.add(2);
        mySet.add(3);
        mySet.add(4);
        mySet.add(5);
        mySet.add(6); // duplicate should not be added
        mySet.add(7);

        
        int start = 2;
        int end = 6;
        System.out.println("ORIGINAL: " +mySet);

        System.out.println("RETAIN(" + start + "," +end + ")");
        System.out.println("RETURNED: " +mySet.retain(start, end));

        System.out.println("REMAINING: " +mySet);

    }

}
