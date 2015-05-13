//import com.shkoda.corrector.DoubleErrorCorrector;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//import static com.shkoda.generator.MessageGenerator.generateMessage;
//import static com.shkoda.generator.MessageGenerator.invertBits;
//import static com.shkoda.sum.CheckSum.count;
//
///**
// * Created by Nightingale on 10.04.2015.
// */
//public class TestDoubleErrors {
//    private static final Random random = new Random();
//
//    @Test
//    public void test() throws Exception {
//        for (int length = 7; length < 500; length++) {
//
//            for (int i = 0; i < 100; i++) {
//                boolean[] message = generateMessage(length);
//                List<Integer> sum = count(message);
//
//                int first = random.nextInt(length) + 1;
//                int second = random.nextInt(length) + 1;
//
//                while (second == first)
//                    second = random.nextInt(length) + 1;
//
//                boolean[] badMessage = invertBits(message, first, second);
//                List<Integer> badSum = count(badMessage);
//
//                boolean[] fixed = null;
//                try {
//
//                    fixed = DoubleErrorCorrector.solve(badMessage, sum);
//
//                } catch (Exception e) {
//                    logError(message, badMessage, fixed, badSum, sum, delta, first, second);
//                    System.out.println("---------------------");
//                    e.printStackTrace();
//                    return;
//                }
//
//                if (!Arrays.equals(fixed, message)) {
//                    logError(message, badMessage, fixed, badSum, sum, delta, first, second);
//                    return;
//                }
//
//
//            }
//
//        }
//    }
//
//
//}
