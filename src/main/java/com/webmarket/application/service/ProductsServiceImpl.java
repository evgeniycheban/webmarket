package com.webmarket.application.service;

import com.webmarket.application.dao.ProductsDAO;
import com.webmarket.application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsDAO dao;

    @Override
    public Product save(Product product) {
        return dao.save(product);
    }

    @Override
    public Product get(int id) {
        return dao.findOne(id);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public Product update(Product product) {
        return dao.save(product);
    }

    @Override
    public Collection<Product> getAll() {
        return dao.findAll();
    }
}
