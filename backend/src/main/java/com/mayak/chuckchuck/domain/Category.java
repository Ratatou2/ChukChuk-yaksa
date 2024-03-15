package com.mayak.chuckchuck.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "category")
//대분류(카테고리)
public class Category {

    //대분류 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    
    //카테고리명
    @Column(name = "category_name")
    private String categoryName;

    //공통데이터
    @Embedded
    private CommonData commonData;
}
