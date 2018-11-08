package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.CollectService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.CollectDTO;
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
 * REST controller for managing Collect.
 */
@RestController
@RequestMapping("/api")
public class CollectResource {

    private final Logger log = LoggerFactory.getLogger(CollectResource.class);

    private static final String ENTITY_NAME = "collect";

    private final CollectService collectService;

    public CollectResource(CollectService collectService) {
        this.collectService = collectService;
    }

    /**
     * POST  /collects : Create a new collect.
     *
     * @param collectDTO the collectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collectDTO, or with status 400 (Bad Request) if the collect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collects")
    @Timed
    public ResponseEntity<CollectDTO> createCollect(@RequestBody CollectDTO collectDTO) throws URISyntaxException {
        log.debug("REST request to save Collect : {}", collectDTO);
        if (collectDTO.getId() != null) {
            throw new BadRequestAlertException("A new collect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollectDTO result = collectService.save(collectDTO);
        return ResponseEntity.created(new URI("/api/collects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collects : Updates an existing collect.
     *
     * @param collectDTO the collectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collectDTO,
     * or with status 400 (Bad Request) if the collectDTO is not valid,
     * or with status 500 (Internal Server Error) if the collectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collects")
    @Timed
    public ResponseEntity<CollectDTO> updateCollect(@RequestBody CollectDTO collectDTO) throws URISyntaxException {
        log.debug("REST request to update Collect : {}", collectDTO);
        if (collectDTO.getId() == null) {
            return createCollect(collectDTO);
        }
        CollectDTO result = collectService.save(collectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collects : get all the collects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of collects in body
     */
    @GetMapping("/collects")
    @Timed
    public List<CollectDTO> getAllCollects() {
        log.debug("REST request to get all Collects");
        return collectService.findAll();
        }

    /**
     * GET  /collects/:id : get the "id" collect.
     *
     * @param id the id of the collectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collects/{id}")
    @Timed
    public ResponseEntity<CollectDTO> getCollect(@PathVariable Long id) {
        log.debug("REST request to get Collect : {}", id);
        CollectDTO collectDTO = collectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collectDTO));
    }

    /**
     * DELETE  /collects/:id : delete the "id" collect.
     *
     * @param id the id of the collectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collects/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollect(@PathVariable Long id) {
        log.debug("REST request to delete Collect : {}", id);
        collectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
