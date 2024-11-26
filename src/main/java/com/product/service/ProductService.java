package com.product.service;

import com.product.dao.ProductRepository;
import com.product.entity.Product;
import com.product.utility.dto.ProductRequestDTO;
import com.product.utility.dto.ProductResponseDTO;
import com.product.utility.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {


    ProductRepository productRepository;

   public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO) {

       Product product = ProductMapper.toEntity(productRequestDTO);
        Product saveProduct = productRepository.save(product);
        return ProductMapper.toResponseDTO(saveProduct);
    }

    public Optional<ProductResponseDTO> getProductById(Long id) {
        return productRepository.findById(id).map(ProductMapper::toResponseDTO);
    }

    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll().stream().map(ProductMapper::toResponseDTO).collect(Collectors.toList());
    }

    public Optional<ProductResponseDTO> updateProduct(Long id, ProductRequestDTO productDetails) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            Product updatedProduct = productRepository.save(existingProduct);
            return ProductMapper.toResponseDTO(updatedProduct);
        });
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
