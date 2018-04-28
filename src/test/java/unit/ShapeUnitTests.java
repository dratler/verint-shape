package unit;

import com.verint.model.Circle;
import com.verint.model.Rectangle;
import com.verint.model.Shape;
import com.verint.types.SupportedShapes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
public class ShapeUnitTests {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void testAValidCircle(){
        Circle circle = new Circle();
        circle.withRadius(1);
        Assert.assertEquals(SupportedShapes.CIRCLE,circle.getShapeType());
        Assert.assertEquals(1,circle.getRadius(),0.001);
        Assert.assertEquals(Math.PI,circle.calcArea(),0.001);
    }
    @Test
    public void testAValidRectangle(){
        Rectangle rectangle = new Rectangle();
        rectangle.withLength(1)
                .withHeight(2);
        Assert.assertEquals(SupportedShapes.RECTANGLE,rectangle.getShapeType());
        Assert.assertEquals(2,rectangle.getHeight(),0.000);
        Assert.assertEquals(1,rectangle.getLength(),0.000);
        Assert.assertEquals(2,rectangle.calcArea(),0.001);
    }

    @Test
    public void testInvalidCircle_NegativeValues(){
        Circle circle = new Circle();
        circle.withRadius(-1);
        Assert.assertEquals(SupportedShapes.CIRCLE,circle.getShapeType());
        Set<ConstraintViolation<Circle>> violations = validator.validate(circle);
        Assert.assertFalse(violations.isEmpty());
    }
    @Test
    public void testInvalidRectangle_NegativeValues(){
        Rectangle rectangle = new Rectangle();
        rectangle.withLength(-1)
                .withHeight(-2);
        Set<ConstraintViolation<Rectangle>> violations = validator.validate(rectangle);
        Assert.assertFalse(violations.isEmpty());
    }
}
