import com.shkoda.generator.IndexGenerator;
import com.shkoda.structures.Indexes;
import com.shkoda.utils.Formatter;
import com.shkoda.utils.MathUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.shkoda.generator.MessageGenerator.generateMessage;
import static com.shkoda.generator.MessageGenerator.invertBits;
import static com.shkoda.sum.CheckSumCounter.countWithThird;

/**
 * Created by Nightingale on 05.05.2015.
 */
public class IndexTest {
    @Test
    public void test() throws Exception {
        int length = 15;
        boolean[] message = generateMessage(length);
        Set<Indexes> indexes = IndexGenerator.generateNumbersWithDifferenceInTwoBits(1, message.length, 1, 2);

//        while (MessageGenerator.hasNext(message)) {
//            List<Integer> sum = count(message);
//        for (int i = 0; i < 10; i++) {

        for (Indexes index : indexes) {
//                boolean[] message = generateMessage(length);
            List<Integer> sum = countWithThird(message);
            int[] errors = index.get();

            boolean[] badMessage = invertBits(message, errors);
            List<Integer> badSum = countWithThird(badMessage);
            List<Integer> delta = MathUtils.xor(sum, badSum);

            System.out.println(String.format("message %s :: errors %16s -- %s :: delta %s",
                    Formatter.toString(message),
                    Arrays.toString(errors),
                    Formatter.toBinaryString(errors),
                    delta));
        }

//            message = MessageGenerator.next(message);


//        }
//        }
    }
}
