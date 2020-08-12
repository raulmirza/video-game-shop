package org.fasttrackit.videogameshop;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.service.ProductService;
import org.fasttrackit.videogameshop.transfer.GetProductsRequest;
import org.fasttrackit.videogameshop.transfer.SaveProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@SpringBootTest
class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Test
    void createProduct_whenValidRequest_returnCreatedProduct() {
        createProduct();

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
        final Product product = createProduct();

        final Product response = productService.getProduct(product.getId());

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
        Product product = createProduct();

        final Page<Product> productsPage = productService.getProducts(new GetProductsRequest(), PageRequest.of(0, 100));

        assertThat(productsPage, notNullValue());
        assertThat(productsPage.getTotalElements(), greaterThanOrEqualTo(1L));

    }

    @Test
    void updateProduct_whenValidRequest_thenReturnUpdatedProduct() {
        final Product product = createProduct();

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
        Product product = createProduct();

        productService.deleteProduct(product.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(product.getId()));

    }

    private Product createProduct() {
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
