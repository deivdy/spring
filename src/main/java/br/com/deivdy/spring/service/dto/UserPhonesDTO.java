package br.com.deivdy.spring.service.dto;

import br.com.deivdy.spring.config.Constants;

import br.com.deivdy.spring.domain.Authority;
import br.com.deivdy.spring.domain.Phones;
import br.com.deivdy.spring.domain.User;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserPhonesDTO {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 254)
    private String name;

    @NotBlank
    private String password;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Set<Phones> phones;

    public UserPhonesDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserPhonesDTO(User user) {
        this.id = user.getId();
        this.name = user.getLogin();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phones = user.getPhones();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Phones> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phones> phones) {
        this.phones = phones;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", phones=" + phones +
            "}";
    }
}
