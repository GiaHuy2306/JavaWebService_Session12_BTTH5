package com.btth5.controller;

import com.btth5.model.Product;
import com.btth5.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest; // Giữ nguyên import chuẩn của bạn
import org.springframework.context.annotation.Import; // Import mới để nạp ObjectMapper
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ObjectMapper.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper; // Sẽ tự động hết đỏ ngay lập tức!

    // --- Các hàm test bên dưới (GET, POST, DELETE) giữ nguyên 100% ---
    @Test // STT 6: GET all - HTTP 200 JSON Array
    void getAll_ShouldReturn200() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(Arrays.asList(new Product(1L, "Sữa", 5000.0, 1)));
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sữa"));
    }

    @Test // STT 7: GET by id - 404
    void getById_NotFound_ShouldReturn404() throws Exception {
        Mockito.when(productService.getProductById(99L)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test // STT 8: POST - 201
    void create_ShouldReturn201() throws Exception {
        Product input = new Product(null, "Kẹo", 2000.0, 10);
        Product saved = new Product(1L, "Kẹo", 2000.0, 10);
        Mockito.when(productService.addProduct(Mockito.any(Product.class))).thenReturn(saved);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test // STT 9: DELETE - 204
    void delete_ShouldReturn204() throws Exception {
        Mockito.when(productService.deleteProduct(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}