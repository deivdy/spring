package br.com.deivdy.spring.web.rest;

import br.com.deivdy.spring.domain.Phones;
import br.com.deivdy.spring.service.PhonesService;
import br.com.deivdy.spring.web.rest.errors.BadRequestAlertException;
import br.com.deivdy.spring.service.dto.PhonesCriteria;
import br.com.deivdy.spring.service.PhonesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.deivdy.spring.domain.Phones}.
 */
@RestController
@RequestMapping("/api")
public class PhonesResource {

    private final Logger log = LoggerFactory.getLogger(PhonesResource.class);

    private static final String ENTITY_NAME = "phones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhonesService phonesService;

    private final PhonesQueryService phonesQueryService;

    public PhonesResource(PhonesService phonesService, PhonesQueryService phonesQueryService) {
        this.phonesService = phonesService;
        this.phonesQueryService = phonesQueryService;
    }

    /**
     * {@code POST  /phones} : Create a new phones.
     *
     * @param phones the phones to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phones, or with status {@code 400 (Bad Request)} if the phones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phones")
    public ResponseEntity<Phones> createPhones(@Valid @RequestBody Phones phones) throws URISyntaxException {
        log.debug("REST request to save Phones : {}", phones);
        if (phones.getId() != null) {
            throw new BadRequestAlertException("A new phones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Phones result = phonesService.save(phones);
        return ResponseEntity.created(new URI("/api/phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phones} : Updates an existing phones.
     *
     * @param phones the phones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phones,
     * or with status {@code 400 (Bad Request)} if the phones is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phones")
    public ResponseEntity<Phones> updatePhones(@Valid @RequestBody Phones phones) throws URISyntaxException {
        log.debug("REST request to update Phones : {}", phones);
        if (phones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Phones result = phonesService.save(phones);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, phones.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phones} : get all the phones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phones in body.
     */
    @GetMapping("/phones")
    public ResponseEntity<List<Phones>> getAllPhones(PhonesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Phones by criteria: {}", criteria);
        Page<Phones> page = phonesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phones/count} : count all the phones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/phones/count")
    public ResponseEntity<Long> countPhones(PhonesCriteria criteria) {
        log.debug("REST request to count Phones by criteria: {}", criteria);
        return ResponseEntity.ok().body(phonesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /phones/:id} : get the "id" phones.
     *
     * @param id the id of the phones to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phones, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phones/{id}")
    public ResponseEntity<Phones> getPhones(@PathVariable Long id) {
        log.debug("REST request to get Phones : {}", id);
        Optional<Phones> phones = phonesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phones);
    }

    /**
     * {@code DELETE  /phones/:id} : delete the "id" phones.
     *
     * @param id the id of the phones to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phones/{id}")
    public ResponseEntity<Void> deletePhones(@PathVariable Long id) {
        log.debug("REST request to delete Phones : {}", id);
        phonesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
