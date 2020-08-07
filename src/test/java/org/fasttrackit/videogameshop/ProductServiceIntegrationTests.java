package org.fasttrackit.videogameshop;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.service.ProductService;
import org.fasttrackit.videogameshop.transfer.SaveProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Test
    void createProduct_whenValidRequest_returnCreatedProduct() {
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

    }
    @Test
    void createProduct_whenMissingMandatoryProperties_thenThrowException(){
        SaveProductRequest request = new SaveProductRequest();

        try {
            productService.createProduct(request);
        } catch (Exception e) {
            e.printStackTrace();
            assertThat("Unexpected exception thrown", e instanceof ConstraintViolationException);
        }


    }

}
