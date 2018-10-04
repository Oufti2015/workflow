package workflow.jobs;

import workflow.interfaces.IJob;
import workflow.interfaces.ITask;

import java.util.List;

public class JobsC1 implements IJob {
    @Override
    public void run(ITask task) {
        List<Object> input = task.input();

        String output = "";

        for (Object object : input) {
            output += object.toString();
        }

        output += this.getClass().getSimpleName();

        task.giveOutput(output);
    }
}
