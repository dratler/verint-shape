package com.verint.model;

import com.verint.exception.MissingArgException;
import com.verint.types.SupportedShapes;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class Circle extends Shape {



    @NotNull @Range(min = 0L)
    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius( double radius) {
        this.radius = radius;
    }

    @Override
    public SupportedShapes getShapeType() {
        return SupportedShapes.CIRCLE;
    }

    @Override
    public double calcArea() {
        if(Objects.isNull(radius)){
            throw new MissingArgException("Missing radius on circle shape");
        }
        return (BigDecimal
                    .valueOf(radius)
                    .multiply(
                            BigDecimal.valueOf(Math.PI)
                    ).multiply(BigDecimal.valueOf(radius))
                ).doubleValue();

    }

    public Circle withRadius(double r){
        this.setRadius(r);
        return this;
    }
}
