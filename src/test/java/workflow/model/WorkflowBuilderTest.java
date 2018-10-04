package workflow.model;

import org.junit.Assert;
import org.junit.Test;
public class WorkflowBuilderTest {

    @Test
    public void test() {
        Workflow workflow  =  new Workflow.Builder("test.xml").build();

        Assert.assertNotNull(workflow);
    }
}
