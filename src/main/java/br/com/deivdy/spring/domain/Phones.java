package br.com.deivdy.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Phones.
 */
@Entity
@Table(name = "phones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8)
    @Column(name = "number", nullable = false)
    private String number;

    @NotNull
    @Size(min = 2)
    @Column(name = "ddd", nullable = false)
    private String ddd;

    @ManyToOne
    @JsonIgnoreProperties(value = "phones", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Phones number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDdd() {
        return ddd;
    }

    public Phones ddd(String ddd) {
        this.ddd = ddd;
        return this;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public User getUser() {
        return user;
    }

    public Phones user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phones)) {
            return false;
        }
        return id != null && id.equals(((Phones) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Phones{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", ddd='" + getDdd() + "'" +
            "}";
    }
}
