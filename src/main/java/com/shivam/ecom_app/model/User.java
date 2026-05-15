package com.shivam.ecom_app.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data //lombok used to generate all the getters and setters automatically {generates getters + setters + equals + hashCode + toString automatically}
@Entity(name ="users") //marks a Java class as a JPA entity, meaning it maps to a database table. Name attribute sets the JPQL entity name used in queries.
@NoArgsConstructor //Used for creating a default constructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private UserRole role = UserRole.CUSTOMER;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = true)
    private Address address;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
