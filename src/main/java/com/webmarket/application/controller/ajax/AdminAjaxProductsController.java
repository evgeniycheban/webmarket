package com.webmarket.application.controller.ajax;

import com.webmarket.application.controller.AbstractProductsController;
import com.webmarket.application.model.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.webmarket.application.controller.ajax.AdminAjaxProductsController.AJAX_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(AJAX_URL)
public class AdminAjaxProductsController extends AbstractProductsController {
    static final String AJAX_URL = "/ajax/admin/products/";

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
    }
}
