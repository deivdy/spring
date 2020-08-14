package br.com.deivdy.spring.service;

import br.com.deivdy.spring.domain.Phones;
import br.com.deivdy.spring.repository.PhonesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Phones}.
 */
@Service
@Transactional
public class PhonesService {

    private final Logger log = LoggerFactory.getLogger(PhonesService.class);

    private final PhonesRepository phonesRepository;

    public PhonesService(PhonesRepository phonesRepository) {
        this.phonesRepository = phonesRepository;
    }

    /**
     * Save a phones.
     *
     * @param phones the entity to save.
     * @return the persisted entity.
     */
    public Phones save(Phones phones) {
        log.debug("Request to save Phones : {}", phones);
        return phonesRepository.save(phones);
    }

    /**
     * Get all the phones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Phones> findAll(Pageable pageable) {
        log.debug("Request to get all Phones");
        return phonesRepository.findAll(pageable);
    }


    /**
     * Get one phones by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Phones> findOne(Long id) {
        log.debug("Request to get Phones : {}", id);
        return phonesRepository.findById(id);
    }

    /**
     * Delete the phones by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Phones : {}", id);
        phonesRepository.deleteById(id);
    }
}
