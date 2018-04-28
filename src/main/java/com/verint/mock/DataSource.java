package com.verint.mock;

import com.verint.exception.SpringException;
import com.verint.model.Circle;
import com.verint.model.Rectangle;
import com.verint.model.Shape;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class creates Mock data source
 */
@Repository
public class DataSource {

    private final AtomicInteger counter = new AtomicInteger();

    private ConcurrentHashMap<Integer,Shape> storedShapes;

    public DataSource(){
        storedShapes = new ConcurrentHashMap<>();
        insert(new Circle().withRadius(1));
        insert(new Circle().withRadius(2));
        insert(new Rectangle()
                .withHeight(1)
                .withLength(2));
        insert(new Rectangle()
                .withHeight(2)
                .withLength(3));
    }

    private static DataSource INSTANCE;
    private static Object lock = false;

    public static DataSource getINSTANCE(){
        if(null == INSTANCE){
            synchronized (lock){
                if(null == INSTANCE){
                    INSTANCE = new DataSource();
                }
            }
        }
        return INSTANCE;
    }

    public List<Integer> getAllIds(){
        return new ArrayList<>(storedShapes.keySet());
    }

    public Shape select(Integer id){
        Shape shape = storedShapes.get(id);
        if(Objects.isNull(shape)) {
            throw new SpringException("Requested Shape with id :["+id+"] not found");
        }
        return shape;
    }

    public Integer insert(Shape shape){
        Integer id = counter.incrementAndGet();
        shape.setId(id);
        storedShapes.put(id,shape);
        return id;
    }

    public void update(Shape target){
        Integer id = target.getId();
        Shape source = select(id);
        BeanUtils.copyProperties(source,target);
        storedShapes.put(id,source);
    }

    public void delete(Integer id){
        select(id);
        storedShapes.remove(id);
        if(Objects.isNull(select(id))){
            throw new SpringException("Delete shape did not happen");
        }
    }


}
