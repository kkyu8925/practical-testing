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
    registeredDateTime: LocalDateTime
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1
        protected set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus = OrderStatus.INIT

    val totalPrice: Int = products.sumOf { it.price }

    val registeredDateTime: LocalDateTime = registeredDateTime

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderProducts: MutableList<OrderProduct> = products.map {
        OrderProduct(order = this, product = it)
    }.toMutableList()
}
