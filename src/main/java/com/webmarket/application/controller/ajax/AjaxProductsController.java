package com.webmarket.application.controller.ajax;

import com.webmarket.application.controller.AbstractProductsController;
import com.webmarket.application.controller.manager.AmazonStorageManager;
import com.webmarket.application.dto.ProductDTO;
import com.webmarket.application.model.Product;
import com.webmarket.application.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.webmarket.application.controller.ajax.AjaxProductsController.AJAX_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(AJAX_URL)
class AjaxProductsController extends AbstractProductsController {
    static final String AJAX_URL = "/ajax/admin/products/";

    @Autowired
    private AmazonStorageManager storageManager;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<Product> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public Product get(@PathVariable("id") String id) {
        return super.get(id);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void delete(@PathVariable("id") String id) {
        super.delete(id);
        storageManager.delete(id + ".jpg");
    }

    @RequestMapping(method = POST)
    public void createOrUpdate(@Valid ProductDTO productDTO, @RequestParam("image")MultipartFile image) {
        if (!image.isEmpty()) {
            if (productDTO.isNew()) {
                Product product = super.save(ProductUtil.createFromTo(productDTO));
                product.setImageUrl(storageManager.save(product.getId() + ".jpg", image));
                super.save(product);
            } else {
                Product product = ProductUtil.updateFromTo(productDTO);
                product.setImageUrl(storageManager.save(product.getId() + ".jpg", image));
                super.save(product);
            }
        } else {
            if (productDTO.isNew()) {
                super.save(ProductUtil.createFromTo(productDTO));
            } else {
                productDTO.setImageUrl(super.get(productDTO.getId()).getImageUrl());
                super.save(ProductUtil.updateFromTo(productDTO));
            }
        }
    }
}