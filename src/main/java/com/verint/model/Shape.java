package com.verint.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.verint.serializer.ShapeDeserializer;
import com.verint.serializer.ShapeSerializer;
import com.verint.types.SupportedShapes;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * The following class is the main POJO object it has ShapeSerializer/ShapeDeserializer
 */
@JsonSerialize(using = ShapeSerializer.class)
@JsonDeserialize(using = ShapeDeserializer.class)
public abstract class Shape {

    @Min(0)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract SupportedShapes getShapeType();

    public abstract double calcArea();
}
