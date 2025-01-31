
import kotlin.random.Random
import kotlin.math.max

class Bagging {
    private fun firstFit(items: IntArray, capacity: Int): Int {
        val bagList = mutableListOf<Int>()
        for (i in items) {
            if (bagList.isEmpty()) {
                bagList.add(capacity - i)
            } else {
                var placed = false
                for (j in bagList.indices) {
                    if (bagList[j] >= i) {
                        bagList[j] -= i
                        placed = true
                        break
                    }
                }
                if (!placed) {
                    bagList.add(capacity - i)
                }
            }
        }
        return bagList.size
    }

    private fun bestFit(items: IntArray, capacity: Int): Int {
        val bagList = mutableListOf<Int>()
        for (i in items) {
            if (bagList.isEmpty()) {
                bagList.add(capacity - i)
            } else {
                var minIndex = -1
                var minSpaceLeft = capacity
                for (j in bagList.indices) {
                    if (bagList[j] >= i && bagList[j] - i < minSpaceLeft) {
                        minSpaceLeft = bagList[j] - i
                        minIndex = j
                    }
                }
                if (minIndex == -1) {
                    bagList.add(capacity - i)
                } else {
                    bagList[minIndex] = minSpaceLeft
                }
            }
        }
        return bagList.size
    }

    private fun dp(items: IntArray, capacity: Int): Int {
        val dp = IntArray(capacity + 1) { 0 }  // DP array to track best bin usage

        var binCount = 0

        for (i in items) {
            // Try to fit the item in an existing bin
            var placed = false
            for (j in dp.indices.reversed()) { // Iterate in reverse to avoid overwriting
                if (dp[j] >= i) {
                    dp[j] -= i
                    placed = true
                    break
                }
            }

            // If no existing bin can fit this item, open a new bin
            if (!placed) {
                dp[capacity] = capacity - i  // Use a new bin
                binCount++
            }
        }

        return binCount
    }


    fun generateTestCase(): Pair<IntArray, Int> {
        val items = IntArray(Random.nextInt(1, 100)) { Random.nextInt(1, 100) }
        val totalCapacity = items.sum()
        val capacity = Random.nextInt(1, totalCapacity);
        return Pair(items, capacity)
    }

    fun bagOfRocks(items: IntArray, bagVolume: Int) {
        val firstFit = firstFit(items, bagVolume)
        val bestFit = bestFit(items, bagVolume)
        val dpFit = dp(items, bagVolume)

        println("First Fit: $firstFit")
        println("Best Fit: $bestFit")
        println("DP: $dpFit")
    }
}

fun main() {
    val bagging = Bagging()

    repeat(5) { i ->
        println("This is iteration $i")

        val testCase = bagging.generateTestCase()

        println("Generated Test Case: ${testCase.first.joinToString()} (Capacity: ${testCase.second})")
        bagging.bagOfRocks(testCase.first, testCase.second)
    }
}
