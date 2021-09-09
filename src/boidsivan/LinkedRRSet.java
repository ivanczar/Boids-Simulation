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

    public LinkedRRSet<E> retain(E start, E end) throws IllegalArgumentException {
        LinkedRRSet<E> notRetained = new LinkedRRSet<>();
//        if (!(start.equals(null) || end.equals(null))) {
//
//            if (start.compareTo(end) > 0) { // makes sure start > end
//                throw new IllegalArgumentException();
//            }
//
//        } else if (!(this.contains(start) || this.contains(end))) {
//            throw new IllegalArgumentException();
//        }
// NOTE: nodes traversed using seths look forward method, where it stops one node before target
        if (this.contains(start) && this.contains(end) || start == null || end == null) {

            Node<E> currentNode = this.firstNode; // create a node which starts at first element of set

            if (start != null) {
                notRetained.add(currentNode.element);
                while (currentNode.next != null && !(currentNode.next.element.compareTo(start) == 0)) //move through set following links untill it finds "start" argument
                {
                    currentNode = currentNode.next; // moves up a node

                    notRetained.add(currentNode.element); //while moving up nodes, add thea current node to the set to be returned

                }

                firstNode = currentNode.next; // removes links from left of "start" node
            }
            if (end != null) {
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
            }

        } else {
            throw new IllegalArgumentException("Start and/or end not contained in Set");
        }
        return notRetained;

    }

    public LinkedRRSet<E> remove(E start, E end) throws IllegalArgumentException {
        LinkedRRSet<E> elemRemoved = new LinkedRRSet<>();
        
        // NOTE: nodes traversed using seths look forward method, where it stops one node before target
        if (this.contains(start) && this.contains(end) || start == null || end == null) { // checks thAt input is valid
            Node<E> currentNode = firstNode; // create node which start at (references) first node
            
            if (start != null || end == null) { 
                
                while (currentNode.next != null && !(currentNode.next.element.compareTo(start) == 0)) {// moves current node to "start" node

                    currentNode = currentNode.next;

                }
            }
            Node<E> tempNode1 = currentNode; // creates a copy of current node to come back to delete

            if (end == null) { //adds all elements after start to return list and then nullifies nodes to right of start
                while (currentNode.next != null) { 

                    currentNode = currentNode.next; // moves up a node

                    elemRemoved.add(currentNode.element);

                }
                tempNode1.next = null;
            }

            if (end != null) { // maybe wrong
                while (currentNode.next != null && !(currentNode.next.element.compareTo(end) == 0)) { // moves upto "end" node adding elements to return list

                    currentNode = currentNode.next; // moves up a node

                    elemRemoved.add(currentNode.element);
                }
            }

            tempNode1.next = currentNode.next; // link  "start" and "end" nodes (nullifying nodes inbetween)
            
            if (start == null) {
                elemRemoved.add(firstNode.element);

                firstNode = firstNode.next;

            }
        } else {
            throw new IllegalArgumentException("Start and/or end not contained in Set");
        }
        return elemRemoved;
    }

    public static void main(String[] args) {

        LinkedRRSet<Integer> mySet = new LinkedRRSet();

        mySet.add(1);
        mySet.add(2);
        mySet.add(3);
        mySet.add(4);
        mySet.add(5);
        mySet.add(6);
        mySet.add(7);

        retainTest(mySet, null, 4);
        retainTest(mySet, 2, 6);
        retainTest(mySet, 6, 7);
        retainTest(mySet, 4, null);
        retainTest(mySet, null , null);
        //  retainTest(mySet, 10, 2); // 10 not in set, throws exception for set user to handle
        System.out.println("==========================================");

        removeTest(mySet, 3, 6);
        removeTest(mySet, 2, 3);
        removeTest(mySet, null, 6);
        removeTest(mySet, 3, null);
      //  removeTest(mySet, 6, 8); // 10 not in set, throws exception for set user to handle
    }

    public static void retainTest(LinkedRRSet<Integer> mySet, Integer start, Integer end) {
        LinkedRRSet<Integer> set = new LinkedRRSet();

        for (Integer i : mySet)//copy set
        {
            set.add(i);
        }

        System.out.println("SET: " + set);
        System.out.println("RETAIN(" + start + "," + end + ")");
        System.out.println("RETURNED SET: " + set.retain(start, end));
        System.out.println("SET: " + set);
        System.out.println("-------------------------------------------");

    }

    public static void removeTest(LinkedRRSet<Integer> mySet, Integer start, Integer end) {
        LinkedRRSet<Integer> set = new LinkedRRSet();

        for (Integer i : mySet)//copy set
        {
            set.add(i);
        }

        System.out.println("SET: " + set);
        System.out.println("REMOVE(" + start + "," + end + ")");
        System.out.println("RETURNED SET: " + set.remove(start, end));
        System.out.println("SET: " + set);
        System.out.println("-------------------------------------------");

    }

}
