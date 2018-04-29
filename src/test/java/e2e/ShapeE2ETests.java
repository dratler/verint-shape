package e2e;

import com.verint.Application;
import com.verint.model.Circle;
import com.verint.model.Shape;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    @Test
    public void testDelete() throws URISyntaxException {
        List<Integer> ids = restTemplate.getForObject("/shape",List.class);
        restTemplate.delete("/shape/"+ids.get(0));
        TestResponse out = restTemplate.execute(new URI("/shape/" + ids.get(0)),
                HttpMethod.GET,
                new RequestCallback() {
                    @Override
                    public void doWithRequest(ClientHttpRequest request) throws IOException {
                    }
                },
                new ResponseExtractor<TestResponse>() {
                    @Nullable
                    @Override
                    public TestResponse extractData(ClientHttpResponse response) throws IOException {
                        return new TestResponse(response.getRawStatusCode(),response.getStatusText());
                    }
                });
        Assert.assertNotEquals(200,out.statusCode);
    }

    @Test
    public void testCreateNewShape() throws URISyntaxException {
        Shape shape = restTemplate.postForObject("/shape",
                                                        new Circle()
                                                                .withRadius(2),
                                                        Shape.class);
        Assert.assertNotNull(shape.getId());
        Assert.assertEquals(shape.calcArea(),2*2*Math.PI,0.000);
        Shape foundShape = restTemplate.getForObject("/shape/"+shape.getId(),Shape.class);
        Assert.assertNotNull(foundShape);
        Assert.assertEquals(shape.getId(),foundShape.getId());
        Assert.assertEquals(shape.calcArea(),foundShape.calcArea(),0.0);
        Assert.assertEquals(shape.getShapeType(),foundShape.getShapeType());

    }
    @Test
    public void testUpdateShape() throws URISyntaxException {
        Circle c =  new Circle().withRadius(2);
        Shape shape = restTemplate.postForObject("/shape", c, Shape.class);
        Assert.assertNotNull(shape.getId());
        Assert.assertEquals(shape.calcArea(),2*2*Math.PI,0.000);
        c.setId(shape.getId());
        c.setRadius(5);
        restTemplate.put("/shape/"+shape.getId(),c);
        Shape foundShape = restTemplate.getForObject("/shape/"+shape.getId(),Shape.class);
        Assert.assertNotNull(foundShape);
        Assert.assertEquals(shape.getId(),foundShape.getId());
        Assert.assertEquals(5*5*Math.PI,foundShape.calcArea(),0.0);
        Assert.assertEquals(shape.getShapeType(),foundShape.getShapeType());

    }
}

class TestResponse{
    int statusCode;
    boolean isOk;
    String text;
    public TestResponse(int code,String text){
        this.statusCode = code;
        this.isOk = !(HttpStatus.resolve(code)).isError();
        this.text = text;
    }
}