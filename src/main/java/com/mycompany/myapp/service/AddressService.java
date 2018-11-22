package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing Address.
 */
public interface AddressService {

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    AddressDTO save(AddressDTO addressDTO);

    /**
     * Get all the addresses.
     *
     * @return the list of entities
     */
    List<AddressDTO> findAll();

    /**
     * Get the "id" address.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AddressDTO findOne(Long id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ResultObj createAddress(AddressDTO addressDTO);

    ResultObj updateAddress(AddressDTO addressDTO);

    ResultObj deleteAddress(Long userId,Long addressId);

    List<Address> findAllAddress(Long userId);

    ResultObj updateDefaultAddress(Long userId,Long AddressId);

    Address findOneById(Long id);

    List<Address> getDefaultAddresses(Long userId,Boolean flag);
}
