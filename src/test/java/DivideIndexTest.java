import com.shkoda.generator.IndexGenerator;
import com.shkoda.utils.Formatter;
import com.shkoda.utils.MathUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.shkoda.generator.MessageGenerator.generateMessage;
import static com.shkoda.generator.MessageGenerator.invertBits;
import static com.shkoda.sum.CheckSum.countWithSquared;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class DivideIndexTest {
    @Test
    public void test() throws Exception {

        Set<IndexGenerator.Indexes> indexes = IndexGenerator.generateNumbersWithDifferenceInTwoBits(1, 256);
        List<IndexGenerator.Indexes> collect = indexes.stream()
                .filter(index -> index.countDividable(3) > 1)
                .filter(index -> {
                    int[] ints = index.get();
                    return !((ints[0] % 3 == 0 && ints[3] % 3 == 0) || (ints[1] % 3 == 0 && ints[2] % 3 == 0));

                })
                .collect(Collectors.toList());

        collect.forEach(System.out::println);


    }
}
