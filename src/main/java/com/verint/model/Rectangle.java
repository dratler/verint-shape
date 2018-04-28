package com.verint.model;
import com.verint.exception.SpringException;
import com.verint.types.SupportedShapes;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class Rectangle extends Shape {



    @Min(0)
    @NotNull
    private double height;

    @Min(0)
    @NotNull
    private double length;

    public double getHeight() {
        return height;
    }

    public void setHeight(@NotNull @Min(0) double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(@NotNull @Min(0) double length) {
        this.length = length;
    }

    @Override
    public SupportedShapes getShapeType() {
        return SupportedShapes.RECTANGLE;
    }


    @Override
    public double calcArea() {
        if(Objects.isNull(height) || Objects.isNull(length)){
            throw new SpringException("Rectangle is missing 'height' or 'length'");
        }
        return (BigDecimal.valueOf(height).multiply(BigDecimal.valueOf(length))).doubleValue();
    }

    public Rectangle withHeight(double h){
        this.setHeight(h);
        return this;
    }

    public Rectangle withLength(double l){
        this.setLength(l);
        return this;
    }
}
