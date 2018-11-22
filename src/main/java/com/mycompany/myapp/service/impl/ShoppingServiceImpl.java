package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ShoppingService;
import com.mycompany.myapp.domain.Shopping;
import com.mycompany.myapp.repository.ShoppingRepository;
import com.mycompany.myapp.service.dto.ShoppingDTO;
import com.mycompany.myapp.service.mapper.ShoppingMapper;
import com.mycompany.myapp.web.rest.util.ResultObj;
import com.mycompany.myapp.web.rest.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Shopping.
 */
@Service
@Transactional
public class ShoppingServiceImpl implements ShoppingService {

    private final Logger log = LoggerFactory.getLogger(ShoppingServiceImpl.class);

    private final ShoppingRepository shoppingRepository;

    private final ShoppingMapper shoppingMapper;

    public ShoppingServiceImpl(ShoppingRepository shoppingRepository, ShoppingMapper shoppingMapper) {
        this.shoppingRepository = shoppingRepository;
        this.shoppingMapper = shoppingMapper;
    }

    /**
     * Save a shopping.
     *
     * @param shoppingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShoppingDTO save(ShoppingDTO shoppingDTO) {
        log.debug("Request to save Shopping : {}", shoppingDTO);
        Shopping shopping = shoppingMapper.toEntity(shoppingDTO);
        shopping = shoppingRepository.save(shopping);
        return shoppingMapper.toDto(shopping);
    }

    /**
     * Get all the shoppings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingDTO> findAll() {
        log.debug("Request to get all Shoppings");
        return shoppingRepository.findAll().stream().map(shoppingMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one shopping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShoppingDTO findOne(Long id) {
        log.debug("Request to get Shopping : {}", id);
        Shopping shopping = shoppingRepository.findOne(id);
        return shoppingMapper.toDto(shopping);
    }

    /**
     * Delete the shopping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shopping : {}", id);
        shoppingRepository.delete(id);
    }

    @Override
    public ResultObj createShopping(ShoppingDTO shoppingDTO) {
        Shopping shopping = shoppingMapper.toEntity(shoppingDTO);
        if (!TypeUtils.isEmpty(shopping)) {
            Shopping shopping1 = shoppingRepository.findByBookId_IdAndSuser_Id(shopping.getBookId().getId(),shopping.getSuser().getId());
            if (!TypeUtils.isEmpty(shopping1)) {
                //根据结果 修改number
                shopping1.setNumber(shopping.getNumber() + shopping1.getNumber());
                shoppingRepository.save(shopping1);
            } else {
                shopping.setExtra1("'1'");
                shoppingRepository.save(shopping);
            }
            return ResultObj.backInfo(true, 200, "添加成功", null);
        }
        return ResultObj.backInfo(false, 200, "添加失败", null);
    }

    @Override
    public ResultObj updateShoppingNumber(Long id, String number) {
        Shopping shopping = shoppingRepository.findOne(id);
        shopping.setNumber(number);
        shoppingRepository.save(shopping);
        return ResultObj.backInfo(true, 200, "修改成功", null);
    }
    /*
    *
    * */
    @Override
    public ResultObj deleteShopping(Long id) {
        Shopping shopping = shoppingRepository.findOne(id);
        shopping.setExtra1("'0'");
        shoppingRepository.save(shopping);
        return ResultObj.backInfo(true, 200, "删除成功", null);
    }

    @Override
    public List<Shopping> selectShopping(Long userId) {
        log.info(userId.toString());
        return shoppingRepository.findBySuserIdAndExtra1(userId,"'1'");
    }

    @Override
    public List<Shopping> selectShoppingByIdList(String[] idList) {
        List<Shopping> list = new ArrayList<>();
        for(int i = 0;i<idList.length;i++){
            Shopping shopping =shoppingRepository.findOne(Long.valueOf(idList[i]));
            list.add(shopping);
        }
        return list;
    }

    @Override
    public ResultObj deleteShoppingList(String[] idList) {
        for(int i = 0;i<idList.length;i++){
            Shopping shopping = shoppingRepository.findOne(Long.valueOf(idList[i]));
            shopping.setExtra1("'0'");
            shoppingRepository.save(shopping);
        }
        return ResultObj.backInfo(true, 200, "删除成功", null);
    }


}
