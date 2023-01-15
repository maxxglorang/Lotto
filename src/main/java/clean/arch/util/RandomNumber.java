package clean.arch.util;

import java.security.SecureRandom;
import java.util.*;

public record RandomNumber(
        int min,
        int max
) {
    private Integer pick() {
        SecureRandom sr = new SecureRandom();
        return sr.nextInt(max - min + 1) + min;
    }

    public List<Integer> generator(int numberOfCount) {
        Set<Integer> nums = new HashSet<>();
        while (nums.size() != numberOfCount) {
            nums.add(pick());
        }
        var list = new ArrayList<>(nums);
        Collections.sort(list);
        return list;
    }
}
