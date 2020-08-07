package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.persistance.ProductRepository;
import org.fasttrackit.videogameshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {
        System.out.println("Creating product: " + request);
        LOGGER.info("Creating product {}", request);
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

       return productRepository.save(product);
    }

    public Product getProduct(long id) {
        LOGGER.info("Retrieving product {}", id);

         return productRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found."));
    }

}
