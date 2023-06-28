package sample.cafekiosk.unit.order

import sample.cafekiosk.unit.beverage.Beverage

import java.time.LocalDateTime

data class Order(
    val orderDateTime: LocalDateTime,
    val beverages: List<Beverage>
)
