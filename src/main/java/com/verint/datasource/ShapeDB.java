package com.verint.datasource;

import com.verint.mock.DataSource;
import com.verint.model.Shape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Primary
public class ShapeDB {

    @Autowired
    private DataSource db;

    public List<Integer> getAllShapeIds(){
        return db.getAllIds();
    }

    public Shape getById(Integer id)   {
        return db.select(id);
    }

    public Integer deleteShape(Integer id){
        db.delete(id);
        return id;
    }

    public Shape storeShape(Shape shape){
        if(Objects.isNull(shape.getId())){
            Integer id = db.insert(shape);
            shape.setId(id);
        } else {
            db.update(shape);
        }
        return shape;
    }


}
