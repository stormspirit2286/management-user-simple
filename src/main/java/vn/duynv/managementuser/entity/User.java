package vn.duynv.managementuser.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
