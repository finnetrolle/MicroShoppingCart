package ru.finnetrolle.examples.orphanremoval.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.finnetrolle.examples.orphanremoval.model.Good;
import ru.finnetrolle.examples.orphanremoval.model.Order;
import ru.finnetrolle.examples.orphanremoval.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("order")
public class OrderResource {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @RequestMapping(value = "{orderId}", method = GET)
    public @ResponseBody
    Order pay(@PathVariable Long orderId) {
        return orderRepository.findOne(orderId);
    }

    @Transactional
    @RequestMapping(value = "{orderId}/success", method = GET)
    public @ResponseBody
    Order success(@PathVariable Long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        order.getGoods().forEach(g -> g.getShoppingCart().removeGood(g));
        return orderRepository.save(order);
    }

    @Transactional
    @RequestMapping(value = "{orderId}/fail", method = GET)
    public @ResponseBody
    Order fail(@PathVariable Long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        List<Good> goods = new ArrayList<>();
        goods.addAll(order.getGoods());
        goods.forEach(g -> order.removeGood(g));
        return orderRepository.save(order);
    }
}
