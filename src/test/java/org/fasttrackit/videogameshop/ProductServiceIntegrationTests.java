package org.fasttrackit.videogameshop;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.service.ProductService;
import org.fasttrackit.videogameshop.steps.ProductTestSteps;
import org.fasttrackit.videogameshop.steps.UserSteps;
import org.fasttrackit.videogameshop.transfer.Product.GetProductsRequest;
import org.fasttrackit.videogameshop.transfer.Product.ProductResponse;
import org.fasttrackit.videogameshop.transfer.Product.SaveProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTestSteps productTestSteps;


    @Test
    void createProduct_whenValidRequest_returnCreatedProduct() {
        productTestSteps.createProduct();

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

    @Test
    void getProduct_whenExistingProduct_thenReturnProduct() {
        final Product product = productTestSteps.createProduct();
        Product response = productService.getProduct(product.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(product.getId()));
        assertThat(response.getName(), is(product.getName()));
        assertThat(response.getQuantity(), is(product.getQuantity()));
        assertThat(response.getPrice(), is(product.getPrice()));
        assertThat(response.getDescription(), is(product.getDescription()));
        assertThat(response.getImageUrl(), is(product.getImageUrl()));

    }

    @Test
    void getProduct_whenNonExistingProduct_thenThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(0));
    }
@Test
    void getProducts_whenOneExistingProduct_thenReturnPageOfOneProduct() {
        Product product = productTestSteps.createProduct();

        final Page<ProductResponse> productsPage = productService.getProducts(new GetProductsRequest(), PageRequest.of(0, 100));

        assertThat(productsPage, notNullValue());
        assertThat(productsPage.getTotalElements(), is(1L));
        assertThat(productsPage.getContent().get(0).getId(), is(product.getId()));

    }

    @Test
    void updateProduct_whenValidRequest_thenReturnUpdatedProduct() {
        final Product product = productTestSteps.createProduct();

        SaveProductRequest request = new SaveProductRequest();
        request.setName(product.getName() + "Updated");
        request.setPrice(request.getPrice() + 10);
        request.setQuantity(request.getQuantity() + 10);

        final Product updatedProduct = productService.updateProduct(product.getId(), request);

        assertThat(updatedProduct, notNullValue());
        assertThat(updatedProduct.getId(), is(product.getId()));
        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
    }

    void deleteProduct_whenExistingProduct_ThenProductDoesNotExistAnymore() {
        Product product = productTestSteps.createProduct();

        productService.deleteProduct(product.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(product.getId()));

    }


}
