package com.verint.service;

import com.verint.datasource.ShapeDB;
import com.verint.exception.SpringException;
import com.verint.model.Circle;
import com.verint.model.Shape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ShapeService {

    //TODO - add validation \ Data sanitation
    @Autowired
    private ShapeDB dao;

    public List<Integer> getAll(){
        return dao.getAllShapeIds();
    }

    public Shape getById(@NotNull Integer id) {
        return dao.getById(id);
    }

    public Integer delete(@NotNull Integer id) {
        return dao.deleteShape(id);
    }

    public Shape store(@NotNull Shape shape) { return dao.storeShape(shape); }
}
