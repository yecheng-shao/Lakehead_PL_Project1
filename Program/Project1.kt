class Bagging {
    fun _first_fit(item, capacity) {
        var bag = 0
        var bag_list = []
        for (i in item) {
            if (bag_list.isEmpty()) {
                bag_list.append(capacity - i)
            } else {
                for (j in bag_list) {
                    if (j >= i) {
                        bag_list[bag_list.index(j)] = j - i
                        break
                    } else if (j == bag_list.last()) {
                        bag_list.append(capacity - i)
                        break
                    }
                }
            }
        }
        return bag_list.size
    }

    fun _best_fit(item, capacity) {
        var bag = 0
        var bag_list = []
        for (i in item) {
            if (bag_list.isEmpty()) {
                bag_list.append(capacity - i)
            } else {
                var min = capacity
                var index = 0
                for (j in bag_list) {
                    if (j >= i && j - i < min) {
                        min = j - i
                        index = bag_list.index(j)
                    }
                }
                if (min == capacity) {
                    bag_list.append(capacity - i)
                } else {
                    bag_list[index] = min
                }
            }
        }
        return bag_list.size
    }

    fun _dp(item, capacity) {
        var bag = 0
        var bag_list = []
        for (i in item) {
            if (bag_list.isEmpty()) {
                bag_list.append(capacity - i)
            } else {
                var dp = Array(bag_list.size + 1) { Array(capacity + 1) { 0 } }
                for (j in 1..bag_list.size) {
                    for (k in 1..capacity) {
                        if (k >= i) {
                            dp[j][k] = max(dp[j - 1][k], dp[j - 1][k - i] + i)
                        } else {
                            dp[j][k] = dp[j - 1][k]
                        }
                    }
                }
                bag_list = dp.last().toMutableList()
            }
        }
        return bag_list.size
    }

    fun _generate_test_case() {
        var item = []
        var capacity = 0
        for (i in 1..100) {
            item.append(random.randint(1, 100))
            capacity += item.last()
        }
        return item, capacity
    }

    fun bagOfRocks(items: IntArray, bagVolumn: Int): Int {
        var first_fit = _first_fit(items, bagVolumn)
        var best_fit = _best_fit(items, bagVolumn)
        var dp = _dp(items, bagVolumn)
        print("First Fit: " + first_fit)
        print("Best Fit: " + best_fit)
        print("DP: " + dp)
        return first_fit, best_fit, dp;
    }
}