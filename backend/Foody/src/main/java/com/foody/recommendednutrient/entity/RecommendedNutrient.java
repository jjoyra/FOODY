package com.foody.recommendednutrient.entity;

import com.foody.global.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedNutrient extends BaseEntity {

    private double energy; // 칼로리, Kcal
    private double carbohydrates; // 탄수화물, g
    private double protein; // 단백질, g
    private double dietaryFiber; // 식이섬유, g
    private double calcium; // 칼슘, mg
    private double sodium; // 나트륨, mg
    private double iron; // 철분, mg
    private double fats; // 지방 g
    private double vitaminA; // 비타민A, μg
    private double vitaminC; // 비타민C, mg
}