package org.modelmapper.functional.depthlimit;

import org.modelmapper.AbstractTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class DepthLimitTest extends AbstractTest {
    static class ParentBox {
        Integer a;
        ChildBox child;
    }
    static class ChildBox {
        Integer a;
    }

    static class ParentBox2 {
        Integer a;
        ChildBox2 child;
    }
    static class ChildBox2 {
        Integer a;
    }

    public void shouldMapFullIfNoLimit() {
        ParentBox parentBox = new ParentBox();
        parentBox.child = new ChildBox();
        parentBox.a = 1;
        parentBox.child.a = 2;
        ParentBox2 mappedParentBox = modelMapper.map(parentBox, ParentBox2.class);
        assertEquals(mappedParentBox.a, Integer.valueOf(1));
        assertNotNull(mappedParentBox.child);
        assertEquals(mappedParentBox.child.a, Integer.valueOf(2));
    }

    public void shouldNotMapNestedOnSourceLimit() {
        ParentBox parentBox = new ParentBox();
        parentBox.child = new ChildBox();
        parentBox.a = 1;
        parentBox.child.a = 2;
        modelMapper.getConfiguration().setMaxSourceMappingDepth(0);
        ParentBox2 mappedParentBox = modelMapper.map(parentBox, ParentBox2.class);
        assertNull(mappedParentBox.child);
        assertEquals(mappedParentBox.a, Integer.valueOf(1));
    }

    public void shouldNotMapNestedOnDestinationLimit() {
        ParentBox parentBox = new ParentBox();
        parentBox.child = new ChildBox();
        parentBox.a = 1;
        parentBox.child.a = 2;
        modelMapper.getConfiguration().setMaxDestinationMappingDepth(0);
        ParentBox2 mappedParentBox = modelMapper.map(parentBox, ParentBox2.class);
        assertNull(mappedParentBox.child);
        assertEquals(mappedParentBox.a, Integer.valueOf(1));
    }

}
