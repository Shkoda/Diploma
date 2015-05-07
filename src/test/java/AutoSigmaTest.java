import com.shkoda.corrector.SigmaCorrector;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.sums.SigmaCheckSum;
import com.shkoda.utils.Formatter;
import org.junit.Before;
import org.junit.Test;

import static com.shkoda.generator.MessageGenerator.generateZeroMessage;
import static com.shkoda.generator.MessageGenerator.hasNext;
import static com.shkoda.generator.MessageGenerator.next;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class AutoSigmaTest {
    @Test
    public void test() throws Exception {
        boolean ok = true;
        for (int length = 16; ok && length < 64 ; length++) {
            boolean[] message = generateZeroMessage(length);
            int[] errors = MessageGenerator.generateSortedDifferentNumbers(length, 4);

            while (ok = solve(message, errors)) {
                if (hasNext(message))
                    message = next(message);
                else break;
            }
        }

    }

    private boolean solve(boolean[] message, int[] errors) {
        boolean[] badMessage = MessageGenerator.invertBits(message, errors);

        SigmaCheckSum correctSum = new SigmaCheckSum(message);
        SigmaCheckSum badSum = new SigmaCheckSum(badMessage);

        SigmaCheckSum delta = correctSum.delta(badSum);

        System.out.println(Formatter.toString(message, correctSum, badMessage, badSum, delta, errors));

        QuadraResult solution = SigmaCorrector.solve(badMessage, correctSum, delta);

        if (solution == null) {
            System.err.println(Formatter.toString(message, correctSum, badMessage, badSum, delta, errors));
            return false;
        }
        return true;
    }
}



























