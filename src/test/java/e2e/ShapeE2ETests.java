package e2e;

import com.verint.Application;
import com.verint.model.Circle;
import com.verint.model.Shape;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class ,
                webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShapeE2ETests {


    @Autowired
     private TestRestTemplate restTemplate;

    @Test
    public void testGetAllIds() {
        List<Integer> ids = restTemplate.getForObject("/shape",List.class);
        Assert.assertNotNull(ids);
    }

    @Test
    public void testSingleShape(){
        List<Integer> ids = restTemplate.getForObject("/shape",List.class);
        Shape shape = restTemplate.getForObject("/shape/"+ids.get(0),Shape.class);
        Assert.assertNotNull(shape);
        Assert.assertNotNull(shape.calcArea());
        Assert.assertNotNull(shape.getShapeType());
    }

}
