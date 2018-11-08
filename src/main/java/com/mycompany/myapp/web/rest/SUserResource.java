package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.SUserService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SUser.
 */
@RestController
@RequestMapping("/api")
public class SUserResource {

    private final Logger log = LoggerFactory.getLogger(SUserResource.class);

    private static final String ENTITY_NAME = "sUser";

    private final SUserService sUserService;

    public SUserResource(SUserService sUserService) {
        this.sUserService = sUserService;
    }

    /**
     * POST  /s-users : Create a new sUser.
     *
     * @param sUserDTO the sUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sUserDTO, or with status 400 (Bad Request) if the sUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-users")
    @Timed
    public ResponseEntity<SUserDTO> createSUser(@RequestBody SUserDTO sUserDTO) throws URISyntaxException {
        log.debug("REST request to save SUser : {}", sUserDTO);
        if (sUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new sUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SUserDTO result = sUserService.save(sUserDTO);
        return ResponseEntity.created(new URI("/api/s-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-users : Updates an existing sUser.
     *
     * @param sUserDTO the sUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sUserDTO,
     * or with status 400 (Bad Request) if the sUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the sUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-users")
    @Timed
    public ResponseEntity<SUserDTO> updateSUser(@RequestBody SUserDTO sUserDTO) throws URISyntaxException {
        log.debug("REST request to update SUser : {}", sUserDTO);
        if (sUserDTO.getId() == null) {
            return createSUser(sUserDTO);
        }
        SUserDTO result = sUserService.save(sUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-users : get all the sUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sUsers in body
     */
    @GetMapping("/s-users")
    @Timed
    public List<SUserDTO> getAllSUsers() {
        log.debug("REST request to get all SUsers");
        return sUserService.findAll();
        }

    /**
     * GET  /s-users/:id : get the "id" sUser.
     *
     * @param id the id of the sUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/s-users/{id}")
    @Timed
    public ResponseEntity<SUserDTO> getSUser(@PathVariable Long id) {
        log.debug("REST request to get SUser : {}", id);
        SUserDTO sUserDTO = sUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sUserDTO));
    }

    /**
     * DELETE  /s-users/:id : delete the "id" sUser.
     *
     * @param id the id of the sUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteSUser(@PathVariable Long id) {
        log.debug("REST request to delete SUser : {}", id);
        sUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
