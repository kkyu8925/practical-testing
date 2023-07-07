package sample.cafekiosk.spring.domain.product

enum class ProductType(
    val text: String
) {
    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");

    companion object {

        fun containsStockType(type: ProductType): Boolean {
            return listOf(BOTTLE, BAKERY).contains(type)
        }
    }
}
