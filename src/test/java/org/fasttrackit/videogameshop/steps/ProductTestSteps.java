package org.fasttrackit.videogameshop.steps;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.service.ProductService;
import org.fasttrackit.videogameshop.transfer.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProductTestSteps {

    @Autowired
    private ProductService productService;

    public Product createProduct() {
        SaveProductRequest request = new SaveProductRequest();
        request.setName("GTA-5");
        request.setPrice(500);
        request.setQuantity(1000);

        final Product product = productService.createProduct(request);

        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));
        assertThat(product.getName(), is(request.getName()));
        assertThat(product.getPrice(), is(request.getPrice()));
        assertThat(product.getQuantity(), is(request.getQuantity()));

        return product;
    }
}
