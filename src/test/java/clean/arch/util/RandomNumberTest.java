package clean.arch.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 랜덤 넘버 생성
 * v 중복되지 않은 6개 숫자
 * - 오름차순 정렬
 */
class RandomNumberTest {

    @Test
    void sameNumber() {
        var nums = new RandomNumber(1, 45).generator(6);
        Set<Integer> target = new HashSet<>(nums);
        assertThat(target.size()).isEqualTo(nums.size());
    }

    @Test
    void ascendingCheck() {

    }
}