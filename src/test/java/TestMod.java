import com.shkoda.utils.Formatter;
import org.junit.Test;

import java.text.Format;

/**
 * Created by Nightingale on 10.05.2015.
 */
public class TestMod {
    @Test
    public void test() throws Exception {
        for (int i = 0; i< 16; i++){
            System.out.println(String.format("%d (%s) :: %d %d %d",
                    i,
                    Formatter.toBinaryString(i),
                    i%3,
                    i%4,
                    i%5));
        }

    }
}
