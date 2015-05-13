package solutions.sigma;

import com.shkoda.corrector.SigmaCorrector;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.sums.SigmaCheckSum;
import com.shkoda.utils.Formatter;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SingleSigmaTest {
    private boolean[] message;
    private int[] errors;

    @Before
    public void setUp() throws Exception {
        message = MessageGenerator.generateMessage(
                new int[]{0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0});
        errors = new int[]{3, 6, 9, 12};
    }

    @Test
    public void test() throws Exception {
        boolean[] badMessage = MessageGenerator.invertBits(message, errors);

        SigmaCheckSum correctSum = new SigmaCheckSum(message);
        SigmaCheckSum badSum = new SigmaCheckSum(badMessage);

        SigmaCheckSum delta = correctSum.delta(badSum);

        System.out.println(Formatter.toString(message, correctSum, badMessage, badSum, delta, errors));

        SigmaCorrector.solve(badMessage, correctSum, delta);

    }
}



























