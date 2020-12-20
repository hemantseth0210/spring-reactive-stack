package com.seth.java.springreactivestack.handler;

import com.seth.java.springreactivestack.entity.Item;
import com.seth.java.springreactivestack.repositoory.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromDataBuffers;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ItemHandler {

    @Autowired
    ItemRepository itemRepository;

    public Mono<ServerResponse> getAllItems(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemRepository.findAll(), Item.class);
    }

    public Mono<ServerResponse> getOneItem(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Item> itemMono = itemRepository.findById(id);

        return itemMono.flatMap(item ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(fromObject(item)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createItem(ServerRequest serverRequest){
        Mono<Item> itemMono = serverRequest.bodyToMono(Item.class);
        return itemMono.flatMap(item ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(itemRepository.save(item), Item.class));
    }

    public Mono<ServerResponse> deleteItem(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemRepository.deleteById(id), Void.class);
    }

    public Mono<ServerResponse> updateItem(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Item> updatedItem = serverRequest.bodyToMono(Item.class)
                .flatMap(item -> {
                    Mono<Item> itemMono = itemRepository.findById(id)
                            .flatMap(currentItem -> {
                                currentItem.setDescription(item.getDescription());
                                currentItem.setPrice(item.getPrice());
                                return itemRepository.save(currentItem);
                            });
                    return itemMono;
                });

        return updatedItem.flatMap(item ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(fromObject(item)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> exceptionItem(ServerRequest serverRequest){
        throw new RuntimeException("RuntimeException Occurred");
    }
}
