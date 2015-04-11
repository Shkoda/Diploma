import com.shkoda.corrector.DoubleErrorCorrector;
import com.shkoda.generator.MessageGenerator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.shkoda.generator.MessageGenerator.generateMessage;
import static com.shkoda.generator.MessageGenerator.invertBits;
import static com.shkoda.sum.CheckSum.count;
import static com.shkoda.utils.Formatter.logError;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class TestTripleErrors {
    private static final Random random = new Random();

    @Test
    public void test() throws Exception {
        for (int length = 7; length < 500; length++) {

            for (int i = 0; i < 100; i++) {
                boolean[] message = generateMessage(length);
                List<Integer> sum = count(message);

                int [] errors = MessageGenerator.generateSortedDifferentNumbers(length, 3);

                boolean[] badMessage = invertBits(message,errors);
                List<Integer> badSum = count(badMessage);

                boolean[] fixed = null;
                try {

                    fixed = DoubleErrorCorrector.fix(badMessage, sum);

                } catch (Exception e) {
                    logError(message, badMessage, fixed, sum, badSum, errors);
                    System.out.println("---------------------");
                    e.printStackTrace();
                    return;
                }

                if (!Arrays.equals(fixed, message)) {
                    logError(message, badMessage, fixed, sum, badSum, errors);
                    return;
                }


            }

        }
    }


}
