package org.fasttrackit.videogameshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.persistance.ProductRepository;
import org.fasttrackit.videogameshop.transfer.Product.GetProductsRequest;
import org.fasttrackit.videogameshop.transfer.Product.ProductResponse;
import org.fasttrackit.videogameshop.transfer.Product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;


    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public ProductResponse createProduct(SaveProductRequest request) {
        System.out.println("Creating product: " + request);
        LOGGER.info("Creating product {}", request);
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

        final Product savedProduct = productRepository.save(product);
        return mapProductResponse(savedProduct);
    }


    public ProductResponse getProductResponse(long id) {
        LOGGER.info("Retrieving product {}", id);

        final Product product = getProduct(id);
        return mapProductResponse(product);
    }

    public Product getProduct(long id) {
        return productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found."));
    }

    public Page<ProductResponse> getProducts(GetProductsRequest request, Pageable pageable) {

        final Page<Product> page = productRepository.findByOptionalCriteria(
                request.getPartialName(), request.getMinimumQuantity(), pageable);

        List<ProductResponse> productDtos = new ArrayList<>();

        for (Product product : page.getContent()) {
            ProductResponse productResponse = mapProductResponse(product);

            productDtos.add(productResponse);
        }

        return new PageImpl<>(productDtos, pageable, page.getTotalElements());

    }

    public ProductResponse updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {}: {}", id, request);

        final Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);

        Product savedProduct = productRepository.save(product);

        return mapProductResponse(savedProduct);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}", id);
        productRepository.deleteById(id);
    }

    private ProductResponse mapProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setPrice(product.getPrice());
        productResponse.setName(product.getName());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setDescription(product.getDescription());

        return  productResponse;
    }
}
