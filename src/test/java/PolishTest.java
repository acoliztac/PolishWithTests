import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Никита on 21.08.2016.
 */
public class PolishTest {
    @Test
    public void Test(){
        Polish pl = new Polish("5-2/3");
        Double d = Double.valueOf(5 - 2.0/3);
        String ideal = String.format("%.3f", d);

        assertEquals(ideal, pl.getAnswer());
    }
}
