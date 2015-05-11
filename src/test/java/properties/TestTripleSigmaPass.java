package properties;

import com.shkoda.corrector.SigmaCorrector;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.sums.CheckSum;
import com.shkoda.structures.sums.SigmaCheckSum;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Nightingale on 10.05.2015.
 */

public class TestTripleSigmaPass {
    /**
     * Original :: 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
     * Damaged  :: 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
     * <p>
     * Control sums :: {0 | 0 0 | 0 0 0 | 0 0 0 0 0  || 0 0 0 0 0 }
     * Bad sums     :: {0 | 0 0 | 4 0 197 | 10 5 10 5 0  || 10 5 10 5 0 }
     * Delta sums   :: {0 | 0 0 | 4 0 197 | 10 5 10 5 0  || 10 5 10 5 0 }
     * <p>
     * Real bad positions  :: [3, 6, 9, 12]	[0011 0110 1001 1100 ]
     */
//doesn't work
    public static void main(String[] args) {
        SigmaCheckSum sum = new SigmaCheckSum();
        sum.oneBitIndexesXor = 0;

        sum.count3 = 4;
        sum.mod3 = 0;
        sum.multMod3 = 197;

        sum.oneBitOnPositionXor = Arrays.asList(10, 5, 10, 5);

        boolean[] badMessage = MessageGenerator.generateMessage(new int[]{0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0});
        QuadraResult solve = SigmaCorrector.solve(badMessage, new SigmaCheckSum(), sum);

        System.out.println(solve);

    }
}
