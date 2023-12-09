package com.taodigital.controller;


import com.taodigital.entity.ProductEntity;
import com.taodigital.service.ProductService;
import com.taodigital.util.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductEntity> getProducts() {
        return productService.getAllActiveProducts();
    }

    @GetMapping("/search")
    public List<ProductEntity> getSearchProducts(
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "minPrice", required = false) Integer minPrice,
            @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(value = "minPostedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date minPostedDate,
            @RequestParam(value = "maxPostedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date maxPostedDate) {
        return productService.getAllSearchProducts(productName, minPrice, maxPrice, minPostedDate, maxPostedDate);
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductDTO product) {
        if (product.getPrice() > 10000) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product price should not be more than $10000.");
        } else {
            return productService.createProduct(product);
        }

    }

    @PutMapping("/{productId}")
    public ResponseEntity<Object> updateProduct(@PathVariable("productId") long productId, @RequestBody ProductDTO product) {
        if (product.getPrice() > 10000) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product price should not be more than $10000.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productId, product));
        }

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("productId") long productId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.deleteProduct(productId));
    }

    @GetMapping("/approval-queue")
    public ResponseEntity<Object> getAllProductFromApprovalQueue() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductFromApprovalQueue());
    }

    @PutMapping("/approval-queue/{approvalId}/approve")
    public ResponseEntity<Object> approveProductByApprovalId(@PathVariable("approvalId") long approvalId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.approveProductByApprovalId(approvalId));
    }

    @PutMapping("/approval-queue/{approvalId}/reject")
    public ResponseEntity<Object> rejectProductByApprovalId(@PathVariable("approvalId") long approvalId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.rejectProductByApprovalId(approvalId));
    }
}