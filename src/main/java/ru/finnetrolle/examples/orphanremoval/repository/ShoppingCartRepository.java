package ru.finnetrolle.examples.orphanremoval.repository;

import org.springframework.data.repository.CrudRepository;
import ru.finnetrolle.examples.orphanremoval.model.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {}
