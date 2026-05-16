package com.shivam.ecom_app.repository;
import com.shivam.ecom_app.model.CartItem;
import com.shivam.ecom_app.model.Product;
import com.shivam.ecom_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long>{
    CartItem findByUserAndProduct(User user, Product product);
}
