package sample.cafekiosk.spring.domain.order

import jakarta.persistence.*
import sample.cafekiosk.spring.domain.BaseEntity
import sample.cafekiosk.spring.domain.orderproduct.OrderProduct
import sample.cafekiosk.spring.domain.product.Product
import java.time.LocalDateTime

@Table(name = "orders")
@Entity
class Order(
    products: List<Product>,
    val registeredDateTime: LocalDateTime
) : BaseEntity() {

    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.INIT

    val totalPrice: Int = products.sumOf { it.price }

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderProducts: MutableList<OrderProduct> = products.map {
        OrderProduct(order = this, product = it)
    }.toMutableList()

    fun updateOrderStatus(orderStatus: OrderStatus) {
        this.orderStatus = orderStatus
    }
}
