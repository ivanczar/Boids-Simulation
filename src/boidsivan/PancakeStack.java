/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boidsivan;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Ivan
 */
//ArrayList generic now specified to hold Pancake objects 
// Extending ArrayList to used its methods to create methods for a pancake stack
public class PancakeStack extends ArrayList<Pancake> implements Iterable<Pancake> {

    public PancakeStack() {
        super();

        Random rand = new Random();
        ArrayList<Integer> sizes = new ArrayList<>();

        for (int j = 1; j < 21; j++) { // populate list of sizes from 1-20
            sizes.add(j);
        }

        Collections.shuffle(sizes); //shuffle sizes arraylist
        for (int i = 0; i < sizes.size(); i++) { //populate pancakestack

            int size = sizes.get(i);
            int rValue = rand.nextInt(255 - 2);
            int gValue = rand.nextInt(255 - 2);
            int bValue = rand.nextInt(255 - 2);
            Color color = new Color(rValue, gValue, bValue);
            this.push(new Pancake(size, color));
        }

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

            temp.add(this.pop());

        }
        for (Pancake p : temp) {
            this.push(p);
        }

    }

    public synchronized void pancakeSort(PancakeStack stackToSort) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                int largestIndex = 0;

                for (int i = 0; i < stackToSort.size(); i++) {
                    largestIndex = stackToSort.findLargest(i);
                    stackToSort.flip(largestIndex);
                    //   System.out.println("flipped from index " + largestIndex);
                    stackToSort.flip(i);
                    //  System.out.println("flipped from bottom");
                }

            }
        });
        t1.start();
        System.out.println(t1.isAlive());
    }

    @Override
    public Iterator<Pancake> iterator() {
        return new PancakeIterator(this);
    }

    private class PancakeIterator implements Iterator<Pancake> {

        PancakeStack stack;
        int currentIndex;

        public PancakeIterator(PancakeStack stack) {
            this.stack = stack;
            currentIndex = 0;
        }

        // returns whether there is still another element
        @Override
        public boolean hasNext() {

            return currentIndex < stack.size();
        }

        // returns the next element or throws a NoSuchElementException
        // it there are no further elements
        @Override
        public Pancake next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Pancake nextP = (Pancake) getPancake(currentIndex);
            currentIndex++;
            return nextP;
        }

        // remove method not supported by this iterator
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

    }
    
    public static void main(String[] args) {
        PancakeStack stack = new PancakeStack();
        
        System.out.println(stack);
        stack.pancakeSort(stack);
        System.out.println(stack);
    }

}
