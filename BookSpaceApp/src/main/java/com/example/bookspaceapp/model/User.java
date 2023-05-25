package com.example.bookspaceapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    @Length(min = 5, message = "Username should be at least 5 chars!")
    private String username;

    @Column(name = "password")
    @Length(min = 5, message = "Password should be at least 5 chars!")
    private String password;

    @Column(name = "firstname")
    @Length(min = 1, message = "First name should be at least 1 char!")
    private String firstName;

    @Column(name = "lastname")
    @Length(min = 1, message = "Last name should be at least 1 chars!")
    private String lastName;

    @Column(name = "email")
    @Email(message = "Employee email format is invalid!")
    private String email;

    /** ONE TO MANY relation with User_Book **/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private List<UserBook> userBooks = new ArrayList<>();

    /** ONE TO MANY relation with Review **/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;


    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean accountNotExpired = true;

    @Builder.Default
    private Boolean accountNotLocked = true;

    @Builder.Default
    private Boolean credentialsNotExpired = true;
}
