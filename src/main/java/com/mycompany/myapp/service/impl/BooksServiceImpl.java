package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.repository.MenusRepository;
import com.mycompany.myapp.service.BooksService;
import com.mycompany.myapp.domain.Books;
import com.mycompany.myapp.repository.BooksRepository;
import com.mycompany.myapp.service.dto.BooksDTO;
import com.mycompany.myapp.service.mapper.BooksMapper;
import com.mycompany.myapp.service.mapper.MenusMapper;
import com.mycompany.myapp.web.rest.util.DateUtil;
import com.mycompany.myapp.web.rest.util.ResultObj;
import com.mycompany.myapp.web.rest.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Books.
 */
@Service
@Transactional
public class BooksServiceImpl implements BooksService {

    private final Logger log = LoggerFactory.getLogger(BooksServiceImpl.class);

    private final BooksRepository booksRepository;

    private final MenusRepository menusRepository;

    private final BooksMapper booksMapper;

    public BooksServiceImpl(BooksRepository booksRepository, BooksMapper booksMapper,MenusRepository menusRepository) {
        this.booksRepository = booksRepository;
        this.booksMapper = booksMapper;
        this.menusRepository = menusRepository;
    }

    /**
     * Save a books.
     *
     * @param booksDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BooksDTO save(BooksDTO booksDTO) {
        log.debug("Request to save Books : {}", booksDTO);
        Books books = booksMapper.toEntity(booksDTO);
        books = booksRepository.save(books);
        return booksMapper.toDto(books);
    }

    /**
     * Get all the books.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BooksDTO> findAll() {
        log.debug("Request to get all Books");
        return booksRepository.findAll().stream().map(booksMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one books by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BooksDTO findOne(Long id) {
        log.debug("Request to get Books : {}", id);
        Books books = booksRepository.findOne(id);
        return booksMapper.toDto(books);
    }

    /**
     * Delete the books by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Books : {}", id);
        booksRepository.delete(id);
    }

    @Override
    public ResultObj insertsBooks(Books books) {
        Books book = booksRepository.findBooksByOnlyName(books.getName());
        if (!TypeUtils.isEmpty(books)) {
            if (TypeUtils.isEmpty(book)) {
                booksRepository.save(books);
                return ResultObj.backInfo(true, 200, "新增成功", null);
            }else{
                return ResultObj.backInfo(true, 200, "新增成功", null);
            }
        }
        return ResultObj.backInfo(false, 200, "新增失败", null);
    }

    public Books selectBooksById(Long id) {
        return booksRepository.selectBooksById(id);
    }

    @Override
    public List<Books> selectBooksByStar(String star) {
        return booksRepository.selectBooksByStar(star);
    }

    @Override
    public List<Books> selectBooksLasted() {
        List<Books> list = booksRepository.selectBooksLasted();
        if (list.size() >= 8) {
            return booksRepository.selectBooksLasted().subList(0, 8);
        }
        return booksRepository.selectBooksLasted();
    }

    @Override
    public List<Books> selectBooksByFirstType(String type) {
        List<Books> books = new ArrayList<>();
        List list = menusRepository.findAllMenusByType(type);

        for(int i = 0; i< list.size(); i++){
            List<Books> booksList = booksRepository.selectBooksByType(String.valueOf(list.get(i)));
            books.addAll(booksList);
        }
        return books;
    }

    @Override
    public List<Books>  selectBooksByName(String name) {
        return booksRepository.findBooksByName(name);
    }

    @Override
    public ResultObj updateBooks(Long id, String count) {
        Books books = selectBooksById(id);
        if (!TypeUtils.isEmpty(books)) {
            if (!TypeUtils.isEmpty(books.getId())) {
                books.setCount(books.getCount() - Integer.valueOf(count));
                booksRepository.save(books);
                return ResultObj.backInfo(true, 200, "修改成功", null);
            }
        }
        return ResultObj.backInfo(false, 200, "修改失败", null);
    }

    @Override
    public List<Books> selectBooksByType(String type) {
        return booksRepository.selectBooksByType(type);
    }


}
