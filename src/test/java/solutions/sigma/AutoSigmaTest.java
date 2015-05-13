package solutions.sigma;

import com.shkoda.corrector.SigmaCorrector;
import com.shkoda.generator.IndexGenerator;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.sums.SigmaCheckSum;
import com.shkoda.utils.Formatter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.shkoda.generator.MessageGenerator.generateZeroMessage;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class AutoSigmaTest {
    @Test
    public void test() throws Exception {

        for (int length = 16; length < 32; length++) {
            boolean[] message = generateZeroMessage(length);

            List<int[]> errorList = IndexGenerator.generateAllQuadraErrors(length);

            for (int[] errors : errorList) {
                if (solve(message, errors)) {
                    System.out.println("Solved for " + Arrays.toString(errors));
                } else {
                    return;
                }
            }
        }
    }

    private boolean solve(boolean[] message, int[] errors) {
        boolean[] badMessage = MessageGenerator.invertBits(message, errors);

        SigmaCheckSum correctSum = new SigmaCheckSum(message);
        SigmaCheckSum badSum = new SigmaCheckSum(badMessage);

        SigmaCheckSum delta = correctSum.delta(badSum);

//        System.out.println(Formatter.toString(message, correctSum, badMessage, badSum, delta, errors));

        QuadraResult solution = SigmaCorrector.solve(badMessage, correctSum, delta);

        if (solution == null) {
            System.err.println(Formatter.toString(message, correctSum, badMessage, badSum, delta, errors));
            return false;
        }
        return true;
    }
}



























