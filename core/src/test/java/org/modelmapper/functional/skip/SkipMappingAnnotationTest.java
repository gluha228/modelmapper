package org.modelmapper.functional.skip;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.modelmapper.AbstractTest;
import org.modelmapper.SkipMapping;
import org.testng.annotations.Test;

@Test
public class SkipMappingAnnotationTest extends AbstractTest {
    static class Box1 {
        private Integer a;
        private Integer b;

        public Integer getA() {
            return a;
        }

        @SkipMapping
        public Integer getB() {
            return b;
        }
        @SkipMapping
        public void setA(Integer a) {
            this.a = a;
        }

        public void setB(Integer b) {
            this.b = b;
        }
    }

    static class Box2 {
        private Integer a;
        private Integer b;

        public Integer getA() {
            return a;
        }

        public Integer getB() {
            return b;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public void setB(Integer b) {
            this.b = b;
        }
    }

    public void shouldSkipMappingByAnnotationOnSourceAccessor() {
        Box1 box1 = new Box1();
        box1.setA(1);
        box1.setB(2);
        Box2 box2 = modelMapper.map(box1, Box2.class);
        assertEquals(box2.getA(), Integer.valueOf(1));
        assertNull(box2.getB());
    }

    public void shouldSkipMappingByAnnotationOnDestinationMutator() {
        Box2 box2 = new Box2();
        box2.setA(1);
        box2.setB(2);
        Box1 box1 = modelMapper.map(box2, Box1.class);
        assertNull(box1.getA());
        assertEquals(box1.getB(), Integer.valueOf(2));
    }


    static class Plain1 {
        public Integer a;
        @SkipMapping
        public Integer b;
    }

    static class Plain2 {
        public Integer a;
        public Integer b;
    }

    public void shouldSkipMappingByAnnotationOnField() {
        Plain1 plain1 = new Plain1();
        plain1.a = 1;
        plain1.b = 2;
        Plain2 plain2 = modelMapper.map(plain1, Plain2.class);
        assertEquals(plain1.a, Integer.valueOf(1));
        assertNull(plain2.b);
    }
}
