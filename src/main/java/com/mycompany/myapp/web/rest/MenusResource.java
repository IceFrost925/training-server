package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.MenusService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.MenusDTO;
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
 * REST controller for managing Menus.
 */
@RestController
@RequestMapping("/api")
public class MenusResource {

    private final Logger log = LoggerFactory.getLogger(MenusResource.class);

    private static final String ENTITY_NAME = "menus";

    private final MenusService menusService;

    public MenusResource(MenusService menusService) {
        this.menusService = menusService;
    }

    /**
     * POST  /menus : Create a new menus.
     *
     * @param menusDTO the menusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menusDTO, or with status 400 (Bad Request) if the menus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menus")
    @Timed
    public ResponseEntity<MenusDTO> createMenus(@RequestBody MenusDTO menusDTO) throws URISyntaxException {
        log.debug("REST request to save Menus : {}", menusDTO);
        if (menusDTO.getId() != null) {
            throw new BadRequestAlertException("A new menus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MenusDTO result = menusService.save(menusDTO);
        return ResponseEntity.created(new URI("/api/menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menus : Updates an existing menus.
     *
     * @param menusDTO the menusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menusDTO,
     * or with status 400 (Bad Request) if the menusDTO is not valid,
     * or with status 500 (Internal Server Error) if the menusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menus")
    @Timed
    public ResponseEntity<MenusDTO> updateMenus(@RequestBody MenusDTO menusDTO) throws URISyntaxException {
        log.debug("REST request to update Menus : {}", menusDTO);
        if (menusDTO.getId() == null) {
            return createMenus(menusDTO);
        }
        MenusDTO result = menusService.save(menusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menus : get all the menus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of menus in body
     */
    @GetMapping("/menus")
    @Timed
    public List<MenusDTO> getAllMenus() {
        log.debug("REST request to get all Menus");
        return menusService.findAll();
        }

    /**
     * GET  /menus/:id : get the "id" menus.
     *
     * @param id the id of the menusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/menus/{id}")
    @Timed
    public ResponseEntity<MenusDTO> getMenus(@PathVariable Long id) {
        log.debug("REST request to get Menus : {}", id);
        MenusDTO menusDTO = menusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(menusDTO));
    }

    /**
     * DELETE  /menus/:id : delete the "id" menus.
     *
     * @param id the id of the menusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteMenus(@PathVariable Long id) {
        log.debug("REST request to delete Menus : {}", id);
        menusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
