import com.shkoda.generator.IndexGenerator;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.sums.CheckSum;
import com.shkoda.utils.MathUtils;
import org.junit.Test;

import java.util.ArrayList;
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

        List<int[]> zero = allQuadraErrors.stream()
                .filter(HowManyDividablePermutations::sumZero)
                .filter(HowManyDividablePermutations::allDividableByThree)
                .collect(Collectors.toList());

        for (int i = 0; i < zero.size()-1; i++) {
            int[] first = zero.get(i);
//            new CheckSum()
            for (int k = i+1; k<zero.size() ; k++){
                int[] second = zero.get(k);


            }
        }

        //  .forEach(errors-> System.out.println(Arrays.toString(errors)));

    }

    private static void lala(){

    }

    private static boolean sumZero(final int[] errors) {
        int sum = 0;
        for (int error : errors) {
            sum ^= error;
        }
        return sum == 0;
    }

    private static boolean allDividableByThree(int[] errors){
        for (int error : errors) {
            if (error % 3 != 0)
                return false;


        }
        return true;
    }


}
