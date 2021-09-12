/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boidsivan;

import java.util.ArrayList;

/**
 *
 * @author Ivan
 */

//ArrayList generic now specified to hold Pancake objects 
// Extending ArrayList to used its methods to create methods for a pancake stack
public class PancakeStack extends ArrayList<Pancake> {
    
    public PancakeStack()
    {
        super();
    }
    
    public Pancake getPancake(int index) // 0 should be bottom stack???
    {
        return this.get(index);
    }
    
    // wrapper method wraps around arraylist add method to give stack like push functionality
    public void push(Pancake pan)
    {
        super.add(pan);
    }
    
    public Pancake pop()
    {
        return super.remove(size());
    }
    
    public Pancake peek()
    {
       return super.get(size());
    }
    
    public int findLargest(int offset)
    {
        int largest = 0;
        for (int i = offset ; i< this.size(); i--)
        {
            if (this.getPancake(i).getSize() > largest)
            {
                
            }
        }
    }
    
    
}
