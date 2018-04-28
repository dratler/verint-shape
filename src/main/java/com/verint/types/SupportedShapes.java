package com.verint.types;

import javax.validation.constraints.NotNull;

public enum SupportedShapes {
    CIRCLE,
    RECTANGLE;

    public static SupportedShapes getSupportedShape(@NotNull String name){
        for(SupportedShapes shapes : SupportedShapes.values()){
            if(shapes.name().equalsIgnoreCase(name)){
                return shapes;
            }
        }
        throw new IllegalArgumentException("Given shape ["+name+"] is not supported");
    }
}
