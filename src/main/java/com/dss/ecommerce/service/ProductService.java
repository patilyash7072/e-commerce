package com.dss.ecommerce.service;

import com.dss.ecommerce.dao.ProductDao;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProductService {
    private final ProductDao productDao;
    private List<Product> productList;
    private Logger logger;

    public ProductService(ProductDao productDao) {
        this.logger = LoggerFactory.getLogger(ProductService.class);
        this.productDao = productDao;
        refreshProductList();
    }

    //1. Get all products
    public List<Product> getAll() {
        return productList;
    }

    public Map<Product, Integer> getAllWithQuantity() {
        return productDao.getAllWithQuantity();
    }

    //2. Add new product
    public void add(Product product) {
        productDao.add(product);
        logger.info("Product with product id : {} added successfully", product.productId());
        refreshProductList();
    }

    //3. Update product
    public void update(Product product) {
        productDao.update(product);
        logger.info("Product with product id : {} updated successfully", product.productId());
        refreshProductList();
    }

    //4. Delete Product
    public void deleteById(int productId) {
        productDao.deleteById(productId);
        logger.info("Product with product id : {} deleted successfully", productId);
        refreshProductList();
    }

    public Product getById(Integer productId) throws ProductNotFoundException {
        return productList
                .stream()
                .filter(product -> product.productId() == productId)
                .findFirst()
                .orElseThrow(
                        () -> new ProductNotFoundException("Product with product id: " + productId + " was not found")
                );
    }

    public void refreshProductList() {
        this.productList = productDao.getAll();
        logger.info("Refreshed Product List");
    }
}
