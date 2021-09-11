/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boidsivan;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Ivan
 */
public class LinkedDoubleEndedQueue<E> implements DoubleEndedQueue<E> {

    private int numElements;
    protected Node<E> frontNode;
    protected Node<E> rearNode;

    //constructor default
    public LinkedDoubleEndedQueue() {
        numElements = 0;
        frontNode = null;
        rearNode = null;
    }

    //constructor for holding elements in a collection
    public LinkedDoubleEndedQueue(Collection<? extends E> c) {
        this();
        for (E element : c) {
            offerRear(element);
        }
    }

    protected class Node<E> {

        public E element;
        public Node<E> next;
        public Node<E> prev;

        public Node(E element) {
            this.element = element;
            next = null;
            prev = null;
        }

        public String toString() {
            return (String) this.element;
        }
    }

    @Override
    public void offerRear(E element) {

        Node<E> newNode = new Node(element);
        if (isEmpty()) {
            frontNode = newNode;
            rearNode = newNode;

        } else {

            //adjust links
            newNode.prev = rearNode; // dont lose reference to rear
            rearNode.next = newNode;
            // point to rear
            rearNode = newNode;

        }

        numElements++;
    }

    @Override
    public void offerFront(E element) {
        Node<E> newNode = new Node(element);

        if (isEmpty()) {

            frontNode = newNode;
            rearNode = newNode;

        } else {

            //adjust links
            newNode.next = frontNode; // dont lose reference to front!
            frontNode.prev = newNode;
            // point to front
            frontNode = newNode;
        }
        numElements++;
    }

    @Override
    public E pollFront() {
        Node<E> temp = frontNode; // keeps copy of front to later return
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Queue is empty!");
        } else {
            frontNode = frontNode.next;

            if (frontNode == null) {// if numElements==1
                rearNode = null;
            } else {
                frontNode.prev = null;
            }

            numElements--;
        }
        return temp.element;

    }

    @Override
    public E pollRear() {
        Node<E> temp = rearNode;
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Queue is empty!");
        } else {
            rearNode = rearNode.prev;

            if (rearNode == null) {// if numElements==1
                frontNode = null;
            } else {
                rearNode.next = null;
            }
            numElements--;
        }
        return temp.element;
    }

    @Override
    public E front() {
        if (!isEmpty()) {
            return frontNode.element;
        } else {
            throw new ArrayIndexOutOfBoundsException("Queue is empty!");
        }

    }

    @Override
    public E rear() throws ArrayIndexOutOfBoundsException {

        if (!isEmpty()) {
            return rearNode.element;
        } else {
            throw new ArrayIndexOutOfBoundsException("Queue is empty!");
        }

    }

    @Override
    public boolean isEmpty() {
        return numElements == 0;
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public void clear() {
        frontNode = null;
        numElements = 0;

    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedIterator<E>(frontNode);
    }

    private class LinkedIterator<E> implements Iterator<E> {

        private Node<E> nextNode; // next node to use for the iterator

        // constructor which accepts a reference to first node in list
        // and prepares an iterator which will iterate through the
        // entire linked list
        public LinkedIterator(Node<E> firstNode) {
            nextNode = firstNode; // start with first node in list
        }

        // returns whether there is still another element
        public boolean hasNext() {
            return (nextNode != null);
        }

        // returns the next element or throws a NoSuchElementException
        // it there are no further elements
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = nextNode.element;
            nextNode = nextNode.next;
            return element;
        }

        // remove method not supported by this iterator
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    public String toString() {
        String output = "front<=[";

        Iterator<E> it = this.iterator();

        while (it.hasNext()) {
            output += it.next();
            if (it.hasNext()) {
                output += ",";
            }
        }
        output += "]==rear";
        return output;
    }

    public static void main(String[] args) {
        LinkedDoubleEndedQueue queue = new LinkedDoubleEndedQueue();

        queue.offerFront("Authoritah");
        System.out.println(queue);
        queue.offerFront("My");
        System.out.println(queue);
        queue.offerRear("Butters");
        System.out.println(queue);
        queue.offerFront("Respect");
        System.out.println(queue);
        queue.offerRear("Cartman");
        System.out.println(queue);
        System.out.println("Removing: \""+ queue.pollRear() + "\" from rear...");
        System.out.println(queue);
        System.out.println("Removing: \""+ queue.pollFront() + "\" from front...");
        System.out.println(queue);
        System.out.println("Front is now: \""  + queue.front() + "\" and rear is now: \"" + queue.rear() + "\"");
        
        System.out.println("Clearing queue...");
        queue.clear();
        System.out.println(queue);
        System.out.println("Attempting to poll empty queue...");
        queue.pollFront();

    }
}
