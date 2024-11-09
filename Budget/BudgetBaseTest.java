package Budget;

//import junit assert equals
import static org.junit.Assert.assertEquals;

import org.junit.Test;

// Swing imports
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Unit test for simple App.
 */
public class BudgetBaseTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldTotal()
    {
        JFrame frame = new JFrame();
        BudgetBase bb = new BudgetBase(frame);
        double value = 0.0;

        //assertEquals(value, bb.calculateTotalIncome() );
    }


    @Test
    public void testJunit() {
        //test junit is working
        String str = "WORKING";
        assertEquals("WORKING", str);
    }


}
