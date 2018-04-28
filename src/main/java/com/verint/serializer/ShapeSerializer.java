package com.verint.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.verint.model.Circle;
import com.verint.model.Rectangle;
import com.verint.model.Shape;
import com.verint.types.SupportedShapes;

import java.io.IOException;

/**
 * This class Serialize the Interface to supported JSON
 */
public class ShapeSerializer extends JsonSerializer<Shape> {

    @Override
    public void serialize(Shape shape, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        SupportedShapes type = shape.getShapeType();
        jsonGenerator.writeNumberField("id",shape.getId());
        jsonGenerator.writeStringField("type",type.name());
        jsonGenerator.writeNumberField("area",shape.calcArea());
        if(SupportedShapes.CIRCLE == type){
            jsonGenerator.writeNumberField("radius",((Circle)shape).getRadius());
        } else if(SupportedShapes.RECTANGLE == type){
            jsonGenerator.writeNumberField("height",((Rectangle)shape).getHeight());
            jsonGenerator.writeNumberField("length",((Rectangle)shape).getLength());
        }
        jsonGenerator.writeEndObject();
    }
}
