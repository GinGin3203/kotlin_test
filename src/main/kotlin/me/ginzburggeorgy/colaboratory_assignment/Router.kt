package me.ginzburggeorgy.colaboratory_assignment

import me.ginzburggeorgy.colaboratory_assignment.items.rest.ItemsHandler
import me.ginzburggeorgy.colaboratory_assignment.orders.rest.OrdersHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class Router(
    val itemsHandler: ItemsHandler,
    val ordersHandler: OrdersHandler,
) {
    @Bean
    fun routes() = coRouter {
        ("/api").nest {
            ("/items").nest {
                GET("/all", itemsHandler::getAllItems)
                POST("/new", itemsHandler::createNewItem)
                ("/aggregated").nest {
                    GET("/by_order_count", ordersHandler::getItemIdsByOrderCount)
                }
            }
            ("/orders").nest {
                POST("/new", ordersHandler::createNewOrder)
            }
        }
    }
}

