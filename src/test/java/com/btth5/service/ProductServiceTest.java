package com.btth5.service;

import com.btth5.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
        productService.addProduct(new Product(null, "Coca Cola", 10000.0, 50));
    }

    @Test // STT 1: getAll - có dữ liệu
    void getAll_ReturnNonEmptyList() {
        List<Product> list = productService.getAllProducts();
        assertFalse(list.isEmpty());
    }

    @Test // STT 2: getById - tìm thấy
    void getById_Found() {
        Product p = productService.getProductById(1L);
        assertEquals("Coca Cola", p.getName());
    }

    @Test // STT 3: getById - không tìm thấy
    void getById_NotFound_ThrowException() {
        assertThrows(RuntimeException.class, () -> productService.getProductById(99L));
    }

    @Test // STT 4: addProduct - thành công
    void addProduct_Success() {
        Product p = productService.addProduct(new Product(null, "Pepsi", 10000.0, 20));
        assertNotNull(p.getId());
    }

    @Test // STT 5: deleteProduct - thành công
    void deleteProduct_Success() {
        boolean deleted = productService.deleteProduct(1L);
        assertTrue(deleted);
        assertEquals(0, productService.getAllProducts().size());
    }
}
