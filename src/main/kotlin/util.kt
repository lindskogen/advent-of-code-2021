package util

fun Iterable<Int>.product(): Int {
    var product: Int = 1
    for (element in this) {
        product *= element
    }
    return product
}

fun <T> List<T>.toPair(): Pair<T, T> =
    if (this.size != 2) {
        throw UnsupportedOperationException("List must be of length 2")
    } else {
        this[0] to this[1]
    }
