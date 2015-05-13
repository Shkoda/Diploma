package solutions.triple;

import com.shkoda.corrector.TripleErrorCorrector;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.utils.MathUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.shkoda.generator.MessageGenerator.*;
import static com.shkoda.sum.CheckSumCounter.count;
import static com.shkoda.utils.Formatter.logError;

/**
 * Created by Nightingale on 16.04.2015.
 */
public class TripleOlgaTest {
    @Test
    public void test() throws Exception {
        for (int length = 7; length < 16; length++) {
            boolean[] message = generateZeroMessage(length);

            while (true) {
                List<Integer> sum = count(message);
                int[] errors = MessageGenerator.generateSortedDifferentNumbers(length, 3);

                boolean[] badMessage = invertBits(message, errors);
                List<Integer> badSum = count(badMessage);

                boolean[] fixed = null;
                int[] positions = null;
                List<Integer> delta = MathUtils.xor(sum, badSum);
                try {
                    positions = TripleErrorCorrector.solve(delta).toArray();
                    fixed = MessageGenerator.invertBits(badMessage, positions);
                } catch (Exception e) {
                  logError(message, badMessage, fixed, badSum, sum, delta, errors, positions);
                    System.out.println("---------------------");
                    e.printStackTrace();
                    return;
                }

                if (!Arrays.equals(positions, errors)) {
                    logError(message, badMessage, fixed, badSum, sum, delta, errors, positions);
                    return;
                }

                if (hasNext(message))
                    message = next(message);
                else break;
            }
        }
    }
}
