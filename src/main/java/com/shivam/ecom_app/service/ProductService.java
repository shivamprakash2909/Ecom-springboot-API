package com.shivam.ecom_app.service;

import com.shivam.ecom_app.Exception.ResourceNotFoundException;
import com.shivam.ecom_app.controller.dto.ProductRequestDto;
import com.shivam.ecom_app.controller.dto.ProductResponseDto;
import com.shivam.ecom_app.model.Product;
import com.shivam.ecom_app.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRespository;

    public ProductResponseDto addProduct(ProductRequestDto requestProduct){
        Product product = new Product();
        product.setName(requestProduct.getName());
        product.setDescription(requestProduct.getDescription());
        product.setPrice(requestProduct.getPrice());
        product.setQuantity(requestProduct.getQuantity());
        product.setCategory(requestProduct.getCategory());
        return mapToResponse(productRespository.save(product));
    }

    public List<ProductResponseDto> getAllProducts(){
        return productRespository.findAll()
                .stream()
                .map(this::mapToResponse).toList();

    }

    public ProductResponseDto getProductById(Long id){
        Product product = productRespository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        return mapToResponse(product);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestProduct){
        Product p = new Product();
        p.setName(requestProduct.getName());
        p.setDescription(requestProduct.getDescription());
        p.setPrice(requestProduct.getPrice());
        p.setQuantity(requestProduct.getQuantity());
        p.setCategory(requestProduct.getCategory());
        p.setIsActive(requestProduct.getIsActive());
        return mapToResponse(productRespository.save(p));
    }

    public String deleteProduct(Long id){
        productRespository.deleteById(id);
        return "Product with id " + id + " has been deleted successfully";
    }

    private ProductResponseDto mapToResponse(Product product){
       ProductResponseDto response = new ProductResponseDto();
       response.setId(product.getId());
       response.setName(product.getName());
       response.setDescription(product.getDescription());
       response.setPrice(product.getPrice());
       response.setQuantity(product.getQuantity());
       response.setCategory((product.getCategory()));
       response.setIsActive(product.getIsActive());
       response.setCreatedAt(product.getCreatedAt());
       response.setUpdatedAt((product.getUpdatedAt()));
       return response;
    }
}


