package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AddressService;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.repository.AddressRepository;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.service.mapper.AddressMapper;
import com.mycompany.myapp.web.rest.util.ResultObj;
import com.mycompany.myapp.web.rest.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Address.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        Address address = addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.toDto(address);
    }

    /**
     * Get all the addresses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressDTO> findAll() {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll().stream().map(addressMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one address by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AddressDTO findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        return addressMapper.toDto(address);
    }

    /**
     * Delete the address by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.delete(id);
    }


    @Override
    public ResultObj createAddress(AddressDTO addressDTO) {
        if (!TypeUtils.isEmpty(addressDTO)) {
            if (!TypeUtils.isEmpty(addressDTO.getSuserId())) {
                List<Address> addressList = findAllAddress(addressDTO.getSuserId());
                if(!TypeUtils.isEmpty(addressList)){
                    addressDTO.setFlag(false);
                }else{
                    addressDTO.setFlag(true);
                }
                addressDTO.setExtra1("'1'");
                save(addressDTO);
                return ResultObj.backInfo(true, 200, "新增成功", null);
            }
        }
        return ResultObj.backInfo(true, 200, "新增失败", null);
    }

    @Override
    public ResultObj updateAddress(AddressDTO addressDTO) {
        AddressDTO address = findOne(addressDTO.getId());
        if (!TypeUtils.isEmpty(address)) {
            save(addressDTO);
            return ResultObj.backInfo(true, 200, "修改成功", null);
        }
        return ResultObj.backInfo(false, 200, "修改失败", null);
    }

    @Override
    public ResultObj deleteAddress(Long userId,Long addressId) {
        Address address = addressRepository.findOneById(addressId,"'1'");
        address.setExtra1("'0'");
        addressRepository.save(address);
        if(address.getFlag()){
            List<Address> addressList = findAllAddress(userId);
            if(!TypeUtils.isEmpty(addressList)){
                Address address2 = addressList.get(0);
                address2.setFlag(true);
                addressRepository.save(address2);
            }
        }
        address.setFlag(false);
        addressRepository.save(address);
        return ResultObj.backInfo(true, 200, "删除成功", null);
    }

    @Override
    public List<Address> findAllAddress(Long userId) {
        return addressRepository.findBySuserIdAndExtra1OrderById(userId, "'1'");
    }

    @Override
    public ResultObj updateDefaultAddress(Long userId,Long AddressId) {
        Address address = addressRepository.findBySuserIdAndFlag(userId, true);
        address.setFlag(false);
        addressRepository.save(address);
        Address a = addressRepository.findOneById(AddressId,"'1'");
        a.setFlag(true);
        addressRepository.save(a);
        return ResultObj.backInfo(true, 200, "设置成功", null);
    }

    @Override
    public Address findOneById(Long id) {
        return addressRepository.findOneById(id,"'1'");
    }
}
