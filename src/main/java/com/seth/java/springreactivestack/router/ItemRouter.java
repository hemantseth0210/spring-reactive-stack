package com.seth.java.springreactivestack.router;

import com.seth.java.springreactivestack.handler.ItemHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ItemRouter {

    @Bean
    public RouterFunction<ServerResponse> itemsRoute(ItemHandler itemHandler){
        return RouterFunctions
                .route(GET("/v1/functional/items").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::getAllItems)
                .andRoute(GET("/v1/functional/items/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::getOneItem)
                .andRoute(POST("/v1/functional/items").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::createItem)
                .andRoute(DELETE("/v1/functional/items/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::deleteItem)
                .andRoute(PUT("/v1/functional/items/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::updateItem);
    }
}
