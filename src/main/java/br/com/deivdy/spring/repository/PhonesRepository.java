package br.com.deivdy.spring.repository;

import br.com.deivdy.spring.domain.Phones;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Phones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhonesRepository extends JpaRepository<Phones, Long>, JpaSpecificationExecutor<Phones> {

    @Query("select phones from Phones phones where phones.user.login = ?#{principal.username}")
    List<Phones> findByUserIsCurrentUser();
}
