package ru.finnetrolle.examples.orphanremoval.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.finnetrolle.examples.orphanremoval.model.Good;
import ru.finnetrolle.examples.orphanremoval.model.Order;
import ru.finnetrolle.examples.orphanremoval.model.Part;
import ru.finnetrolle.examples.orphanremoval.model.ShoppingCart;
import ru.finnetrolle.examples.orphanremoval.repository.OrderRepository;
import ru.finnetrolle.examples.orphanremoval.repository.ShoppingCartRepository;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("cart")
public class ShoppingCartResource {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @RequestMapping(value = "{cartId}", method = GET)
    public @ResponseBody
    ShoppingCart get(@PathVariable Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findOne(cartId);
        if (cart == null) {
            cart = new ShoppingCart(cartId, UUID.randomUUID().toString());
            return shoppingCartRepository.save(cart);
        } else {
            return cart;
        }
    }

    @Transactional
    @RequestMapping(value = "{cartId}/pay", method = GET)
    public @ResponseBody
    Order pay(@PathVariable Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findOne(cartId);
        if (cart == null) {
            return null;
        }
        Order order = new Order(UUID.randomUUID().toString());
        for (Good good : cart.getGoods()) {
            order.addGood(good);
        }
        return orderRepository.save(order);
    }

    @Transactional
    @RequestMapping(value = "{cartId}/remove/{goodId}", method = GET)
    public @ResponseBody
    ShoppingCart create(@PathVariable Long cartId, @PathVariable Long goodId) {
        ShoppingCart cart = shoppingCartRepository.findOne(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("cart not exist");
        }
        Good good = cart.getGoods().stream()
                .filter(g -> g.getId().equals(goodId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        if (good.getOrder() == null) {
            cart.removeGood(good);
        } else {
            throw new IllegalArgumentException("good is in paying");
        }
        return shoppingCartRepository.save(cart);
    }

    @Transactional
    @RequestMapping(value = "{cartId}/add", method = GET)
    public @ResponseBody
    ShoppingCart create(@PathVariable Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findOne(cartId);
        cart.addGood(createGood(UUID.randomUUID().toString()));
        return shoppingCartRepository.save(cart);
    }

    private Good createGood(String name) {
        Good good = new Good(name);
        good.addPart(new Part(UUID.randomUUID().toString()));
        return good;
    }

}
