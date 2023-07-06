package sample.cafekiosk.spring.domain.product

enum class ProductSellingStatus(
    val text: String
) {
    SELLING("판매중"),
    HOLD("판매보류"),
    STOP_SELLING("판매중지");

    companion object {

        fun forDisplay(): List<ProductSellingStatus> {
            return listOf(SELLING, HOLD)
        }
    }
}
