package br.com.deivdy.spring.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.com.deivdy.spring.domain.Phones;
import br.com.deivdy.spring.domain.*; // for static metamodels
import br.com.deivdy.spring.repository.PhonesRepository;
import br.com.deivdy.spring.service.dto.PhonesCriteria;

/**
 * Service for executing complex queries for {@link Phones} entities in the database.
 * The main input is a {@link PhonesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Phones} or a {@link Page} of {@link Phones} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhonesQueryService extends QueryService<Phones> {

    private final Logger log = LoggerFactory.getLogger(PhonesQueryService.class);

    private final PhonesRepository phonesRepository;

    public PhonesQueryService(PhonesRepository phonesRepository) {
        this.phonesRepository = phonesRepository;
    }

    /**
     * Return a {@link List} of {@link Phones} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Phones> findByCriteria(PhonesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Phones> specification = createSpecification(criteria);
        return phonesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Phones} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Phones> findByCriteria(PhonesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Phones> specification = createSpecification(criteria);
        return phonesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PhonesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Phones> specification = createSpecification(criteria);
        return phonesRepository.count(specification);
    }

    /**
     * Function to convert {@link PhonesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Phones> createSpecification(PhonesCriteria criteria) {
        Specification<Phones> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Phones_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), Phones_.number));
            }
            if (criteria.getDdd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDdd(), Phones_.ddd));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Phones_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
