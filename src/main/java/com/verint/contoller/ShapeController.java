package com.verint.contoller;

import com.verint.exception.MissingArgException;

import com.verint.model.Shape;
import com.verint.service.ShapeService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.constraints.Min;
import java.util.List;
@RestController
@EnableWebMvc
@RequestMapping(value = "/shape", produces = {"application/json"})
public class ShapeController {
    //TOOD - Add Exception \ Error routh
    @Autowired
    private ShapeService shapeService;

    @RequestMapping( method = RequestMethod.GET)
    public List<Integer> getAll(){
        return shapeService.getAll();
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public @ResponseBody Shape getByid(@PathVariable @Range(min = 0) Integer id){

        return shapeService.getById(id);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public Integer delete(@PathVariable @Min(0) Integer id){
        return shapeService.delete(id);
    }

    @RequestMapping( method = RequestMethod.POST , consumes = {"application/json"})
    public @ResponseBody Shape insert(@RequestBody Shape shape){
        return shapeService.store(shape);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.PUT , consumes = {"application/json"})
    public @ResponseBody Shape update(@PathVariable @Min(0) Integer id ,@RequestBody Shape shape){
        if(id != shape.getId()){
            throw new MissingArgException("The shape id and parameter id are different");
        }
        return shapeService.store(shape);
    }
}