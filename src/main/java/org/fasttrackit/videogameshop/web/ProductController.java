package org.fasttrackit.videogameshop.web;
import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.service.ProductService;
import org.fasttrackit.videogameshop.transfer.Product.GetProductsRequest;
import org.fasttrackit.videogameshop.transfer.Product.ProductResponse;
import org.fasttrackit.videogameshop.transfer.Product.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody SaveProductRequest request) {
        final Product product = productService.createProduct(request);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @Valid @RequestBody SaveProductRequest request) {
        Product product = productService.updateProduct(id, request);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        Product product = productService.getProduct(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(@Valid GetProductsRequest request, Pageable pageable) {
         Page<ProductResponse> products = productService.getProducts(request, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
      productService.deleteProduct(id);

      return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
