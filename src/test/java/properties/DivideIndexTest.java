package properties;

import com.shkoda.generator.IndexGenerator;
import com.shkoda.structures.Indexes;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class DivideIndexTest {
    @Test
    public void test() throws Exception {

        Set<Indexes> indexes = IndexGenerator.generateNumbersWithDifferenceInTwoBits(1, 1024);
        List<Indexes> collect = indexes.stream()
                .filter(index -> index.countDividable(3) == 2)
                .filter(index -> index.sumDividable(3) % 3 == 1)
                .filter(index -> {
                    int[] ints = index.get();
                    return !(((ints[1] >> 2) & 1) == 0 && ((ints[1] >> 1) & 1) == 1);
                })

//                //все ли одной четности
//                .filter(index -> {
//                    int[] ints = index.get();
//                    return ints[0] % 2 != ints[1] % 2
//                            || ints[1] % 2 != ints[2] % 2
//                            || ints[2] % 2 != ints[3] % 2;
//                })

//                        .filter(index -> index.sumDividable(3) % 3 ==1)
//                                .filter(index->index.get()[1] % 3 != 0)
//                                .filter(index-> index.sumDividable(3) % 3 != 0)
//                .filter(index -> {
//                    int[] ints = index.get();
//                    return !((ints[1] % 3 == 0 && ints[2] % 3 == 0));
//                    return !((ints[0] % 3 == 0 && ints[3] % 3 == 0) || (ints[1] % 3 == 0 && ints[2] % 3 == 0));
//                })
                .collect(Collectors.toList());

        collect.forEach(System.out::println);


    }
}
