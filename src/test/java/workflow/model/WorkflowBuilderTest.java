package workflow.model;

import org.junit.Assert;
import org.junit.Test;
import workflow.exceptions.ExecutionException;
import workflow.exceptions.XMLFileException;

import java.io.File;

public class WorkflowBuilderTest {

    public static final String TEST_XML = "wf-test.xml";

    @Test
    public void test() {
        Assert.assertTrue("File " + TEST_XML + " does not exists.", new File(TEST_XML).exists());

        WorkflowModel workflow = null;
        try {
            workflow = new WorkflowBuilder(TEST_XML).build();
        } catch (XMLFileException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(workflow);
        Assert.assertEquals(8, workflow.getTasks().size());

        System.out.println("workflow = "+workflow);
        for (TaskModel task : workflow.getTasks()) {
            System.out.println("task = " + task);
        }

        try {
            workflow.execute();
        } catch (ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }
}
