package org.fasttrackit.videogameshop;

import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.service.CartService;
import org.fasttrackit.videogameshop.steps.UserSteps;
import org.fasttrackit.videogameshop.transfer.cart.AddProductsToCartRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserSteps userSteps;


    @Test
    void addProductsToCart_whenNewCart_thenCartIsCreated() {
        User user = userSteps.createUser();

        AddProductsToCartRequest cartRequest = new AddProductsToCartRequest();
        cartRequest.setUserId(user.getId());

        cartService.addProductsToCart(cartRequest);
    }
}
