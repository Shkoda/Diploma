package properties;

import com.shkoda.generator.IndexGenerator;

import java.util.*;

/**
 * Created by Nightingale on 11.05.2015.
 */
public class FindMinModThreeCoverage {
    public static void main(String[] args) {
        int maxIndex = 32;
        List<int[]> errors = IndexGenerator.generateAllQuadraErrors(maxIndex);
        List<Integer> dividableByThree = dividableByThree(maxIndex);

        List<Integer> coverage = new ArrayList<>();
        Set<Integer> covered = new HashSet<>();

        for (int value : dividableByThree) {
            if (coverage.contains(value) || covered.contains(value)) continue;

            coverage.add(value);

            for (int[] error : errors) {
                for (int index : error) {
                    if (index == value){

                        for (int i : error) {
                            covered.add(i);
                        }
                        break;
                    }
                }
            }
        }

        System.out.println(coverage);
    }

    private static List<Integer> dividableByThree(int max){
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i<= max; i++)
            if (i % 3 == 0)
                res.add(i);
        return res;
    }
}
