package com.seth.java.springreactivestack.controller;

import com.seth.java.springreactivestack.entity.Item;
import com.seth.java.springreactivestack.repositoory.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/v1/items")
    public Flux<Item> getAllItems(){
        return itemRepository.findAll();
    }

    @GetMapping("/v1/items/{id}")
    public Mono<ResponseEntity<Item>> getItem(@PathVariable String id){
        return itemRepository.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/v1/items")
    public Mono<Item> createItem(@RequestBody Item item){
        return itemRepository.save(item);
    }

    @DeleteMapping("/v1/items/{id}")
    public Mono<Void> createItem(@PathVariable String id){
        return itemRepository.deleteById(id);
    }

    @PutMapping("/v1/items/{id}")
    public Mono<ResponseEntity<Item>> createItem(@PathVariable String id, @RequestBody Item item){
        return itemRepository.findById(id)
                .flatMap(currentItem -> {
                    currentItem.setPrice(item.getPrice());
                    currentItem.setDescription(item.getDescription());
                    return itemRepository.save(currentItem);
                })
                .map(updatedItem -> new ResponseEntity<>(updatedItem, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
