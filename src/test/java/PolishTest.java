import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Никита on 21.08.2016.
 */
public class PolishTest {
    @Test
    public void TestDevideByZero(){
        Polish pl = new Polish("1/0");
        Double d = Double.valueOf(1.0/0);
        String ideal = String.format("%.3f", d);
        assertEquals(ideal, pl.getAnswer());
    }
    @Test
    public void Test(){
        Polish pl = new Polish("1/0");
        Double d = Double.valueOf(1.0/0);
        String ideal = String.format("%.3f", d);
        assertEquals(ideal, pl.getAnswer());
    }

}
