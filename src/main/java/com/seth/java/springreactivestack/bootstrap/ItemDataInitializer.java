package com.seth.java.springreactivestack.bootstrap;

import com.seth.java.springreactivestack.entity.Item;
import com.seth.java.springreactivestack.repositoory.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
public class ItemDataInitializer implements CommandLineRunner {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {
        initialDataSetUp();
    }

    private List<Item> data(){
        return Arrays.asList(
                new Item(null, "Samsung TV", 799.99),
                new Item(null, "LG TV", 699.99),
                new Item(null, "Apple Watch", 399.99),
                new Item("1111", "Beats Headphones", 349.99)
        );
    }

    private void initialDataSetUp() {
        itemRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(itemRepository::save)
                .thenMany(itemRepository.findAll())
                .subscribe(item -> {
                    System.out.println("Item inserted from Command Line Runner: " +item);
                });
    }
}
