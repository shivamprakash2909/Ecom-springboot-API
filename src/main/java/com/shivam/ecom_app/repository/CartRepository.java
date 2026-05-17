package com.shivam.ecom_app.repository;
import com.shivam.ecom_app.model.CartItem;
import com.shivam.ecom_app.model.Product;
import com.shivam.ecom_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long>{
    CartItem findByUserAndProduct(User user, Product product);
    @Modifying
    @Query("DELETE FROM cart_item c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
