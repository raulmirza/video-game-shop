package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.Cart;
import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.persistance.CartRepository;
import org.fasttrackit.videogameshop.transfer.cart.AddProductsToCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public void addProductsToCart(AddProductsToCartRequest request) {
        LOGGER.info("Adding products to cart: {}", request);

        final Cart cart = cartRepository.findById(request.getUserId())
                .orElse(new Cart());

        if (cart.getUser() == null) {
            final User user = userService.getUser(request.getUserId());

            cart.setUser(user);
        }

        for (Long id : request.getProductIds()) {
            final Product product = productService.getProduct(id);
            cart.addProductToCart(product);
        }
        cartRepository.save(cart);
    }
}
