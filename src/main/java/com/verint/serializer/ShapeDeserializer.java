package com.verint.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.verint.exception.SpringException;
import com.verint.model.Circle;
import com.verint.model.Rectangle;
import com.verint.model.Shape;
import com.verint.types.SupportedShapes;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Objects;

/**
 * Override the Jackson OTB Deserializer for Shape
 */

@JsonComponent
public class ShapeDeserializer extends JsonDeserializer<Shape> {
    @Override
    public Shape deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        TreeNode type = treeNode.get("type");
        Shape s = null;
        if(null == type){
            throw new SpringException("Shape 'type' is missing");
        }
        SupportedShapes shapeType = SupportedShapes.getSupportedShape(((TextNode)type).asText());
        if(SupportedShapes.RECTANGLE == shapeType){
            s = new Rectangle();
            DoubleNode height = (DoubleNode) treeNode.get("height");
            DoubleNode length = (DoubleNode) treeNode.get("length");
            ((Rectangle)s).withHeight(height.doubleValue());
            ((Rectangle)s).withLength(length.doubleValue());
        } else if(SupportedShapes.CIRCLE == shapeType){
            s = new Circle();
            DoubleNode radius = (DoubleNode) treeNode.get("radius");
            ((Circle)s).withRadius(radius.doubleValue());
        }
        NumericNode id = (NumericNode)treeNode.get("id");
        if(Objects.nonNull(id)){
            s.setId(id.asInt());
        }
        return s;
    }

}
