package sample.cafekiosk.learning

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Lists
import com.google.common.collect.Multimap
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class GuavaLearningTest {

    @DisplayName("주어진 개수만큼 List를 파티셔닝한다.")
    @Test
    fun partitionLearningTest1() {
        // given
        val integers = listOf(1, 2, 3, 4, 5, 6)

        // when
        val partition = Lists.partition(integers, 3)

        // then
        assertThat(partition).hasSize(2)
            .isEqualTo(listOf(listOf(1, 2, 3), listOf(4, 5, 6)))
    }

    @DisplayName("주어진 개수만큼 List를 파티셔닝한다.")
    @Test
    fun partitionLearningTest2() {
        // given
        val integers = listOf(1, 2, 3, 4, 5, 6)

        // when
        val partition = Lists.partition(integers, 4)

        // then
        assertThat(partition).hasSize(2)
            .isEqualTo(listOf(listOf(1, 2, 3, 4), listOf(5, 6)))
    }

    @DisplayName("멀티맵 기능 확인")
    @Test
    fun multiMapLearningTest() {
        // given
        val multimap: Multimap<String, String> = ArrayListMultimap.create()
        multimap.put("커피", "아메리카노")
        multimap.put("커피", "카페라떼")
        multimap.put("커피", "카푸치노")
        multimap.put("베이커리", "크루아상")
        multimap.put("베이커리", "식빵")

        // when
        val strings = multimap["커피"]

        // then
        assertThat(strings).hasSize(3)
            .isEqualTo(listOf("아메리카노", "카페라떼", "카푸치노"))
    }

    @DisplayName("멀티맵 기능 확인")
    @TestFactory
    fun multiMapLearningTest2(): Collection<DynamicTest> {
        // given
        val multimap: Multimap<String, String> = ArrayListMultimap.create()
        multimap.put("커피", "아메리카노")
        multimap.put("커피", "카페라떼")
        multimap.put("커피", "카푸치노")
        multimap.put("베이커리", "크루아상")
        multimap.put("베이커리", "식빵")

        return listOf(
            DynamicTest.dynamicTest("1개 value 삭제") {
                // when
                multimap.remove("커피", "카푸치노")

                // then
                val results = multimap["커피"]
                assertThat(results).hasSize(2)
                    .isEqualTo(listOf("아메리카노", "카페라떼"))
            },
            DynamicTest.dynamicTest("1개 key 삭제") {
                // when
                multimap.removeAll("커피")

                // then
                val results = multimap["커피"]
                assertThat(results).isEmpty()
            }
        )
    }
}
