package solutions.squared;

import com.shkoda.corrector.SigmaCorrector;
import com.shkoda.corrector.SquaredSumCorrector;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.sums.SigmaCheckSum;
import com.shkoda.structures.sums.SquaredCheckSum;
import com.shkoda.utils.Formatter;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SingleSquaredTest {
    private boolean[] message;
    private int[] errors;

    @Before
    public void setUp() throws Exception {
        message = MessageGenerator.generateMessage(
                new int[]{0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0});
        errors = new int[]{1, 2, 4, 6};
    }

    @Test
    public void test() throws Exception {
        boolean[] badMessage = MessageGenerator.invertBits(message, errors);

        SquaredCheckSum correctSum = new SquaredCheckSum(message);
        SquaredCheckSum badSum = new SquaredCheckSum(badMessage);

        SquaredCheckSum delta = correctSum.delta(badSum);

        System.out.println(Formatter.toString(message, correctSum, badMessage, badSum, delta, errors));

        QuadraResult result = SquaredSumCorrector.solve(badMessage, correctSum, badSum, delta);
        System.out.println("Result :: "+result);

    }
}



























