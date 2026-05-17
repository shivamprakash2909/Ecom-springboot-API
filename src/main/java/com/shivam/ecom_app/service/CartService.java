package com.shivam.ecom_app.service;

import com.shivam.ecom_app.Exception.InsufficientStockException;
import com.shivam.ecom_app.Exception.ResourceNotFoundException;
import com.shivam.ecom_app.controller.dto.CartRequestDto;
import com.shivam.ecom_app.controller.dto.CartResponseDto;
import com.shivam.ecom_app.model.CartItem;
import com.shivam.ecom_app.model.Product;
import com.shivam.ecom_app.model.User;
import com.shivam.ecom_app.repository.CartRepository;
import com.shivam.ecom_app.repository.ProductRepository;
import com.shivam.ecom_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartResponseDto addToCart(Long userId, CartRequestDto requestItem){
        Product product = productRepository.findById(requestItem.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException(("Product not available")));
        if(product.getQuantity() < requestItem.getQuantity() ){
            throw new InsufficientStockException("Insufficient product quantity");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        CartItem existingCartItemItem = cartRepository.findByUserAndProduct(user,product);
        if(existingCartItemItem != null){
            // update the item in cart
            existingCartItemItem.setQuantity(existingCartItemItem.getQuantity() + requestItem.getQuantity());
            existingCartItemItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItemItem.getQuantity())));
            CartItem savedCartItem = cartRepository.save(existingCartItemItem);
            return mapToResponse(savedCartItem);
        }
        else{
            //add item in cart
            CartItem cartItem= new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(requestItem.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(requestItem.getQuantity())));
            CartItem savedCartItem = cartRepository.save(cartItem);
            return mapToResponse(savedCartItem);
        }
    }

    public List<CartResponseDto> getCartItems(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        List <CartItem> cartItems = user.getCartItems();
        return cartItems.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public String removeFromCart(Long userId, Long productId, Integer quantity){
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not available"));
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        CartItem cartItem = cartRepository.findByUserAndProduct(user,product);

        if(cartItem == null){
            // No item in cart
            throw new ResourceNotFoundException("Product not found in cart");
        }
        if(quantity > cartItem.getQuantity()){
            //Quantity exceeds than in cart
            throw new InsufficientStockException("Requested quantity exceeds cart quantity");
        }
        int remainingQuantity = cartItem.getQuantity() - quantity;
        if(remainingQuantity == 0){
            cartRepository.delete(cartItem);
            return "Product removed completely from cart";
        }
        cartItem.setQuantity(remainingQuantity);
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(remainingQuantity)));
        cartRepository.save(cartItem);
        return "Cart  updatedSuccessfully";
    }

    @Transactional
    public void clearCart(Long userId) {
        userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        cartRepository.deleteByUserId(userId);
    }
     private CartResponseDto mapToResponse(CartItem cartItem){
         CartResponseDto response = new CartResponseDto();
         response.setProductId(cartItem.getProduct().getId());
//         response.setProduct(cartItem.getProduct());
         response.setProductName(cartItem.getProduct().getName());
         response.setQuantity(cartItem.getQuantity());
         response.setPrice(cartItem.getPrice());
         return response;
     }
}
