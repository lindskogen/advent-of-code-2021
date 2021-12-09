package util

fun Iterable<Int>.product(): Int {
    var product: Int = 1
    for (element in this) {
        product *= element
    }
    return product
}
