package properties;

import com.shkoda.generator.IndexGenerator;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.utils.MathUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 10.05.2015.
 */
public class HowManyDividablePermutations {
    @Test
    public void test() throws Exception {
        boolean[] message = MessageGenerator.generateZeroMessage(64);

        List<int[]> allQuadraErrors = IndexGenerator.generateAllQuadraErrors(64);

        List<Integer> constBits = Arrays.asList(5);

        List<int[]> zero = allQuadraErrors.stream()
                .filter(HowManyDividablePermutations::sumZero)
                .filter(HowManyDividablePermutations::allDividableByThree)
//                .filter(error-> sameConstBits(error,constBits))
                .collect(Collectors.toList());

        zero.forEach(z -> System.out.println(Arrays.toString(z) +"\t\t"+sum(z)));


    }

    private static int sum(int[] errors){
        int sum = 0;
        for (int error : errors) {
            sum+=error;
        }
        return sum;
    }

    private static boolean sameConstBits(int[] errors, List<Integer> positions) {
        List<Integer> values = positions.stream()
                .map(position -> MathUtils.bitOnPosition(errors[0], position))
                .collect(Collectors.toList());

        for (int i = 1; i < errors.length; i++) {
            int index = i;
            List<Integer> current = positions.stream()
                    .map(position -> MathUtils.bitOnPosition(errors[index], position))
                    .collect(Collectors.toList());

            for (int k = 0; k < values.size(); k++) {
                if (values.get(k).intValue() != current.get(k).intValue())
                    return false;
            }

        }

        return true;

    }

    private static boolean sumZero(final int[] errors) {
        int sum = 0;
        for (int error : errors) {
            sum ^= error;
        }
        return sum == 0;
    }

    private static boolean allDividableByThree(int[] errors) {
        for (int error : errors) {
            if (error % 3 != 0)
                return false;


        }
        return true;
    }


}
