package unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verint.Application;
import com.verint.model.Circle;
import com.verint.model.Rectangle;
import com.verint.model.Shape;
import com.verint.serializer.ShapeSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@JsonTest
@SpringBootTest(classes = Application.class)
public class Serializer_DeSerializerUnitTest {



    @Test
    public void testSerializeCircle() throws Exception {
        Circle circle = new Circle().withRadius(2);
        Shape actual = new ObjectMapper().readValue("{\"type\":\"circle\",\"radius\":2.0}", Shape.class);
        Assert.assertEquals(circle.calcArea(),actual.calcArea(),0.0001);
    }
    @Test
    public void testSerializeRectangle() throws Exception {
        Rectangle rectangle = new Rectangle().withHeight(2).withLength(3);
        Shape actual = new ObjectMapper().readValue("{\"type\":\"rectangle\",\"height\":2.0,\"length\":3.0}", Shape.class);
        Assert.assertEquals(rectangle.calcArea(),actual.calcArea(),0.0001);
    }
    @Test
    public void testSerializeCircle_MissingRadius(){
        Boolean hasFailed = false;
        try{
            new ObjectMapper().readValue("{\"type\":\"circle\"}", Shape.class);
            hasFailed = true;
        } catch (Exception e){}
        Assert.assertFalse(hasFailed);
    }
    @Test
    public void testSerializeRectangle_MissingHeight() throws Exception {
        Boolean hasFailed = false;
        try{
            new ObjectMapper().readValue("{\"type\":\"rectangle\",\"length\":3.0}", Shape.class);
            hasFailed = true;
        } catch (Exception e){}
        Assert.assertFalse(hasFailed);
    }
    @Test
    public void testSerializeRectangle_MissingLength() throws Exception {
        Boolean hasFailed = false;
        try{
            new ObjectMapper().readValue("{\"type\":\"rectangle\",\"height\":2.0}", Shape.class);
            hasFailed = true;
        } catch (Exception e){}
        Assert.assertFalse(hasFailed);
    }
}
