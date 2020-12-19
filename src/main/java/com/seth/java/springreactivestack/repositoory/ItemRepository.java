package com.seth.java.springreactivestack.repositoory;

import com.seth.java.springreactivestack.entity.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemRepository extends ReactiveMongoRepository<Item, String> {
}
