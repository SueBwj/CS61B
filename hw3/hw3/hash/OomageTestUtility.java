package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        double containNumMin = (double) oomages.size() / 50;
        double containNumMax = (double) oomages.size() / 2.5;
        int[] bucket = new int[M];// 因为bucketNum%M，所以一定会比M小
        for (int i = 0; i < oomages.size(); i++) {
            int bucketNum = (oomages.get(i).hashCode() & 0x7FFFFFFF) % M;
                bucket[bucketNum]++;
        }
        for (int i = 0; i < bucket.length; i++) {
            if (bucket[i] > containNumMax || bucket[i] < containNumMin) {
                return false;
            }
        }
        return true;
    }
}
