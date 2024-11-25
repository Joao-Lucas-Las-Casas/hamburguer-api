package br.com.codegrill.codegrill_api.entities;

import br.com.codegrill.codegrill_api.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
@Table(name = "produtos")
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String description;
    private String image;
    @Setter
    private BigDecimal price;
    private BigDecimal discount;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private String introduction;
}
