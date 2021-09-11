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
public class ArrayDoubleEndedQueue<E> implements DoubleEndedQueue<E> {

    private final int INITIAL_CAPACITY = 5;
    protected int numElements = 0;
    protected int front = 0; // indicates start of queue
    protected int rear = 0; // indicates next available position for adding element
    protected E[] elements;

    public ArrayDoubleEndedQueue() {
        numElements = 0;
        elements = (E[]) (new Object[INITIAL_CAPACITY]); // unchecked
    }

    // constructor for creating a new stack which
    // initially holds the elements in the collection c
    public ArrayDoubleEndedQueue(Collection<? extends E> c) {
        this();
        for (E element : c) {
            offerRear(element);
        }
    }

    @Override
    public void offerRear(E element) {
        if (numElements >= elements.length) {

            expandCapacity();
        }
        if (isEmpty()) {
            elements[rear] = element;
            numElements++;
            rear++;
            return;
        }
        if (rear == numElements - 1) {
            rear = 0;
        } else {

            elements[rear] = element;
            numElements++;
            rear++;

        }
        
    }

    @Override
    public void offerFront(E element) {
        if (numElements >= elements.length) {

            expandCapacity();
        }
        if (front == 0) {
            
            front = numElements - 1;
        } else {
            front--;

        }
        elements[front] = element;
        numElements++;
    }

    @Override
    public E pollRear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E pollFront() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E front() {
        if (!isEmpty())
        {
        return elements[front];
        }
        else
            throw new ArrayIndexOutOfBoundsException("Queue is empty!");
    }

    @Override
    public E rear() {
        if (!isEmpty())
        {
        return elements[rear];
        }
        else 
            throw new ArrayIndexOutOfBoundsException("Queue is empty!");
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
        for (int i = 0; i <= numElements; i++) {
            elements[i] = null;
        }
        numElements = 0;
        front = 0;
        rear = 0;
    }

    public void expandCapacity() {

        E[] expandedArr = (E[]) (new Object[elements.length * 2]); // creates a new array double the size

        for (int i = 0; i < elements.length; i++) {
            if (front == -1) {
                front = 0;
            }
            expandedArr[i] = elements[front];

            front++;
            if (front == elements.length) {
                front = 0;
            }
        }

        //reset head and tail to locations in expanded array
        front = 0;
        rear = numElements;

        // change reference of element array to new expanded array
        elements = expandedArr;

    }


    public String toString() {
        String s = "[";
        for (int i = 0; i < elements.length; i++) {
            s += elements[i];
            if (i < elements.length - 1) {
                s += " ,";
            }
        }
        return s + "]";

    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<E>(front);
    }

    private class ArrayIterator<E> implements Iterator<E> {

        private int currentIndex; // next node to use for the iterator

        // constructor which accepts a reference to first node in list
        // and prepares an iterator which will iterate through the
        // entire linked list
        public ArrayIterator(int front) {

            currentIndex = front;
        }

        // returns whether there is still another element
        public boolean hasNext() {
            return elements[currentIndex++] != null;
        }

        // returns the next element or throws a NoSuchElementException
        // it there are no further elements
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return (E) elements[currentIndex++];
        }

        // remove method not supported by this iterator
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        ArrayDoubleEndedQueue queue = new ArrayDoubleEndedQueue();

        queue.offerRear("hi");
        System.out.println(queue);
        queue.offerRear("chu");
        System.out.println(queue);
        queue.offerRear("bi");
        System.out.println(queue);
        queue.offerRear("ste");
        System.out.println(queue);
        queue.offerFront("chi");
        System.out.println(queue);

//        queue.offerFront("awd");
//        System.out.println(queue);
//        queue.offerRear("hi");
//        System.out.println(queue);
//        queue.offerRear("chu");
//        System.out.println(queue);
//        queue.offerRear("awd");
//        System.out.println(queue);
//        System.out.println(queue);
//        queue.offerFront("awd");
    }
}
