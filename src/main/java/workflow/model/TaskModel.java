package workflow.model;

import lombok.Data;
import workflow.interfaces.ITask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskModel implements ITask {
    private String id;
    private List<JobModel> jobs = new ArrayList<>();
    private List<TaskModel> predecessors = new ArrayList<>();
    private List<TaskModel> successors = new ArrayList<>();
    private boolean first = false;
    private boolean last = false;
    private List<Object> input = new ArrayList<>();
    private List<Object> output = new ArrayList<>();

    @Override
    public String toString() {
        return "Task(" + getId() + ", pred=["
                + predecessors.stream().map(TaskModel::getId).collect(Collectors.joining(",")) + "], succ=["
                + successors.stream().map(TaskModel::getId).collect(Collectors.joining(",")) + "])";
    }

    @Override
    public List<Object> input() {
        return input;
    }

    @Override
    public void giveOutput(Object output) {
        this.output.add(output);
    }

    public void execute() {
        for (JobModel job : jobs) {
            job.getJob().run(this);
        }
    }
}
