/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boidsivan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 *
 * @author Ivan
 */
//ArrayList generic now specified to hold Pancake objects 
// Extending ArrayList to used its methods to create methods for a pancake stack
public class PancakeStack extends ArrayList<Pancake> {

    public PancakeStack() {
        super();
    }

    public Pancake getPancake(int index) {
        return this.get(index);
    }

    // wrapper method wraps around arraylist add method to give stack like push functionality
    public void push(Pancake pan) {
        super.add(pan); // adds to index 0, i.e pushes to bottom of stack
    }

    public Pancake pop() {
        return super.remove(size() - 1); // removes and return last element i.e top element of stack
    }

    public Pancake peek() {
        return super.get(size() - 1); // returns top element of stack
    }

    public int findLargest(int offset) {
        int largestSize = 0;
        int largestSizeIndex = 0;

        for (int i = offset; i < this.size(); i++) {
            if (this.getPancake(i).getSize() > largestSize) {
                largestSize = this.getPancake(i).getSize();
                largestSizeIndex = i;
            }
        }
        return largestSizeIndex;

    }

    public void flip(int index) {

        Queue<Pancake> temp = new LinkedList();

        for (int i = size() - 1; i >= index; i--) {
            System.out.println("removed");
            temp.add(this.pop());

        }
        for (Pancake p : temp) {
            this.push(p);
        }

    }

    public Iterator<Pancake> iterator() {
        return new PancakeIterator<Pancake>(0);
    }
    
    private class PancakeIterator<Pancake> implements Iterator<Pancake> {

        private int currentIndex; // next node to use for the iterator

        // constructor which accepts a reference to first node in list
        // and prepares an iterator which will iterate through the
        // entire linked list
        public PancakeIterator(int bottom) {

            currentIndex = bottom;
        }

        // returns whether there is still another element
        public boolean hasNext() {
            return getPancake(currentIndex++) != null;
        }

        // returns the next element or throws a NoSuchElementException
        // it there are no further elements
        public Pancake next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return (Pancake) getPancake(currentIndex++);
        }

        // remove method not supported by this iterator
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        PancakeStack stack = new PancakeStack();
        for (int i = 1; i < 20; i++) {
            stack.push(new Pancake(i));
        }

        System.out.println(stack);

        stack.flip(10);
        System.out.println(stack);

    }
}
