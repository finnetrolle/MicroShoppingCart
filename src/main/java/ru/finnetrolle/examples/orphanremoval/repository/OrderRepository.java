package ru.finnetrolle.examples.orphanremoval.repository;

import org.springframework.data.repository.CrudRepository;
import ru.finnetrolle.examples.orphanremoval.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {


}
