package workflow.model;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import workflow.exceptions.ExecutionException;

import java.util.ArrayList;
import java.util.List;

@Data
@Log4j
public class WorkflowModel {

    private TaskModel start;
    private TaskModel end;
    private List<TaskModel> tasks = new ArrayList<>();

    WorkflowModel() {
    }

    public void execute() throws ExecutionException {
        if (start == null) {
            throw new ExecutionException("I don't how to start...");
        }

        start.setInput(new ArrayList<>());

        execute(start);
    }

    private void execute(TaskModel task) {
        log.info("Running " + task + "...");
        task.execute();

        List<Object> output = task.getOutput();

        for (TaskModel subTask : task.getSuccessors()) {
            subTask.setInput(output);
            execute(subTask);
        }
        log.info("Task " + task + " finished...");
    }

    public Object getOutput() {
        return end.getOutput();
    }
}
