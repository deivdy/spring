package br.com.deivdy.spring.web.rest;

import br.com.deivdy.spring.SpringApp;
import br.com.deivdy.spring.domain.Phones;
import br.com.deivdy.spring.domain.User;
import br.com.deivdy.spring.repository.PhonesRepository;
import br.com.deivdy.spring.service.PhonesService;
import br.com.deivdy.spring.service.dto.PhonesCriteria;
import br.com.deivdy.spring.service.PhonesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PhonesResource} REST controller.
 */
@SpringBootTest(classes = SpringApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhonesResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DDD = "AAAAAAAAAA";
    private static final String UPDATED_DDD = "BBBBBBBBBB";

    @Autowired
    private PhonesRepository phonesRepository;

    @Autowired
    private PhonesService phonesService;

    @Autowired
    private PhonesQueryService phonesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhonesMockMvc;

    private Phones phones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phones createEntity(EntityManager em) {
        Phones phones = new Phones()
            .number(DEFAULT_NUMBER)
            .ddd(DEFAULT_DDD);
        return phones;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phones createUpdatedEntity(EntityManager em) {
        Phones phones = new Phones()
            .number(UPDATED_NUMBER)
            .ddd(UPDATED_DDD);
        return phones;
    }

    @BeforeEach
    public void initTest() {
        phones = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhones() throws Exception {
        int databaseSizeBeforeCreate = phonesRepository.findAll().size();
        // Create the Phones
        restPhonesMockMvc.perform(post("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isCreated());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeCreate + 1);
        Phones testPhones = phonesList.get(phonesList.size() - 1);
        assertThat(testPhones.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPhones.getDdd()).isEqualTo(DEFAULT_DDD);
    }

    @Test
    @Transactional
    public void createPhonesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phonesRepository.findAll().size();

        // Create the Phones with an existing ID
        phones.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhonesMockMvc.perform(post("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isBadRequest());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = phonesRepository.findAll().size();
        // set the field null
        phones.setNumber(null);

        // Create the Phones, which fails.


        restPhonesMockMvc.perform(post("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isBadRequest());

        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDddIsRequired() throws Exception {
        int databaseSizeBeforeTest = phonesRepository.findAll().size();
        // set the field null
        phones.setDdd(null);

        // Create the Phones, which fails.


        restPhonesMockMvc.perform(post("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isBadRequest());

        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhones() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList
        restPhonesMockMvc.perform(get("/api/phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phones.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].ddd").value(hasItem(DEFAULT_DDD)));
    }
    
    @Test
    @Transactional
    public void getPhones() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get the phones
        restPhonesMockMvc.perform(get("/api/phones/{id}", phones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phones.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.ddd").value(DEFAULT_DDD));
    }


    @Test
    @Transactional
    public void getPhonesByIdFiltering() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        Long id = phones.getId();

        defaultPhonesShouldBeFound("id.equals=" + id);
        defaultPhonesShouldNotBeFound("id.notEquals=" + id);

        defaultPhonesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPhonesShouldNotBeFound("id.greaterThan=" + id);

        defaultPhonesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPhonesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPhonesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where number equals to DEFAULT_NUMBER
        defaultPhonesShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the phonesList where number equals to UPDATED_NUMBER
        defaultPhonesShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPhonesByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where number not equals to DEFAULT_NUMBER
        defaultPhonesShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the phonesList where number not equals to UPDATED_NUMBER
        defaultPhonesShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPhonesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultPhonesShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the phonesList where number equals to UPDATED_NUMBER
        defaultPhonesShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPhonesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where number is not null
        defaultPhonesShouldBeFound("number.specified=true");

        // Get all the phonesList where number is null
        defaultPhonesShouldNotBeFound("number.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhonesByNumberContainsSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where number contains DEFAULT_NUMBER
        defaultPhonesShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the phonesList where number contains UPDATED_NUMBER
        defaultPhonesShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPhonesByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where number does not contain DEFAULT_NUMBER
        defaultPhonesShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the phonesList where number does not contain UPDATED_NUMBER
        defaultPhonesShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPhonesByDddIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where ddd equals to DEFAULT_DDD
        defaultPhonesShouldBeFound("ddd.equals=" + DEFAULT_DDD);

        // Get all the phonesList where ddd equals to UPDATED_DDD
        defaultPhonesShouldNotBeFound("ddd.equals=" + UPDATED_DDD);
    }

    @Test
    @Transactional
    public void getAllPhonesByDddIsNotEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where ddd not equals to DEFAULT_DDD
        defaultPhonesShouldNotBeFound("ddd.notEquals=" + DEFAULT_DDD);

        // Get all the phonesList where ddd not equals to UPDATED_DDD
        defaultPhonesShouldBeFound("ddd.notEquals=" + UPDATED_DDD);
    }

    @Test
    @Transactional
    public void getAllPhonesByDddIsInShouldWork() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where ddd in DEFAULT_DDD or UPDATED_DDD
        defaultPhonesShouldBeFound("ddd.in=" + DEFAULT_DDD + "," + UPDATED_DDD);

        // Get all the phonesList where ddd equals to UPDATED_DDD
        defaultPhonesShouldNotBeFound("ddd.in=" + UPDATED_DDD);
    }

    @Test
    @Transactional
    public void getAllPhonesByDddIsNullOrNotNull() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where ddd is not null
        defaultPhonesShouldBeFound("ddd.specified=true");

        // Get all the phonesList where ddd is null
        defaultPhonesShouldNotBeFound("ddd.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhonesByDddContainsSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where ddd contains DEFAULT_DDD
        defaultPhonesShouldBeFound("ddd.contains=" + DEFAULT_DDD);

        // Get all the phonesList where ddd contains UPDATED_DDD
        defaultPhonesShouldNotBeFound("ddd.contains=" + UPDATED_DDD);
    }

    @Test
    @Transactional
    public void getAllPhonesByDddNotContainsSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where ddd does not contain DEFAULT_DDD
        defaultPhonesShouldNotBeFound("ddd.doesNotContain=" + DEFAULT_DDD);

        // Get all the phonesList where ddd does not contain UPDATED_DDD
        defaultPhonesShouldBeFound("ddd.doesNotContain=" + UPDATED_DDD);
    }


    @Test
    @Transactional
    public void getAllPhonesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        phones.setUser(user);
        phonesRepository.saveAndFlush(phones);
        Long userId = user.getId();

        // Get all the phonesList where user equals to userId
        defaultPhonesShouldBeFound("userId.equals=" + userId);

        // Get all the phonesList where user equals to userId + 1
        defaultPhonesShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhonesShouldBeFound(String filter) throws Exception {
        restPhonesMockMvc.perform(get("/api/phones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phones.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].ddd").value(hasItem(DEFAULT_DDD)));

        // Check, that the count call also returns 1
        restPhonesMockMvc.perform(get("/api/phones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhonesShouldNotBeFound(String filter) throws Exception {
        restPhonesMockMvc.perform(get("/api/phones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhonesMockMvc.perform(get("/api/phones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPhones() throws Exception {
        // Get the phones
        restPhonesMockMvc.perform(get("/api/phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhones() throws Exception {
        // Initialize the database
        phonesService.save(phones);

        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();

        // Update the phones
        Phones updatedPhones = phonesRepository.findById(phones.getId()).get();
        // Disconnect from session so that the updates on updatedPhones are not directly saved in db
        em.detach(updatedPhones);
        updatedPhones
            .number(UPDATED_NUMBER)
            .ddd(UPDATED_DDD);

        restPhonesMockMvc.perform(put("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhones)))
            .andExpect(status().isOk());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
        Phones testPhones = phonesList.get(phonesList.size() - 1);
        assertThat(testPhones.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPhones.getDdd()).isEqualTo(UPDATED_DDD);
    }

    @Test
    @Transactional
    public void updateNonExistingPhones() throws Exception {
        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhonesMockMvc.perform(put("/api/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isBadRequest());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhones() throws Exception {
        // Initialize the database
        phonesService.save(phones);

        int databaseSizeBeforeDelete = phonesRepository.findAll().size();

        // Delete the phones
        restPhonesMockMvc.perform(delete("/api/phones/{id}", phones.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
