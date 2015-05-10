import com.shkoda.corrector.SigmaCorrector;
import com.shkoda.generator.IndexGenerator;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.sums.SigmaCheckSum;
import com.shkoda.utils.Formatter;
import com.shkoda.utils.GaluaMath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.shkoda.generator.MessageGenerator.generateZeroMessage;

/**
 * Created by Nightingale on 10.05.2015.
 */
public class TestModThree {
    @Test
    public void test() throws Exception {

        for (int length = 16; length < 32; length++) {
            boolean[] message = generateZeroMessage(length);

            List<int[]> errorList = IndexGenerator.generateAllQuadraErrors(length);

            for (int[] errors : errorList) {
                if (solve(message, errors)) {
//                    System.out.println("Solved for " + Arrays.toString(errors));
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

        if (delta.oneBitIndexesXor != 0)
            return true;

        if (solutionInSigmas(errors, delta.sigma0, delta.sigma1))
            return true;

        List<Integer> dividableByThree = getDividableByThree(errors);
        boolean ok;

        if (dividableByThree.size() == 1) {
            int err = dividableByThree.get(0);
            ok = delta.count3 % 2 == 1
                    && delta.mod3 == err
                    && delta.multMod3 == err;
        } else {
            int sum = dividableByThree.get(0) ^ dividableByThree.get(1);
            int mult = GaluaMath.multiple(dividableByThree.get(0), dividableByThree.get(1));

            ok = delta.count3 % 2 == 0
                    && delta.mod3 == sum
                    && delta.multMod3 == mult;
        }

        if (ok)
            return true;

        System.err.println(Formatter.toString(message, correctSum, badMessage, badSum, delta, errors));
        return false;

    }

    private static boolean solutionInSigmas(int[] errors, int... sigmas){
        for (int error : errors) {
            for (int sigma : sigmas) {
                if (error == sigma)
                    return true;
            }
        }
        return false;
    }

    private static List<Integer> getDividableByThree(int[] errors) {
        List<Integer> result = new ArrayList<>();
        for (int error : errors) {
            if (error % 3 == 0)
                result.add(error);
        }
        return result;
    }


}
