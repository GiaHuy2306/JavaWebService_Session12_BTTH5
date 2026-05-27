package com.btth5.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "product") // Yêu cầu định dạng thẻ XML
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
}
