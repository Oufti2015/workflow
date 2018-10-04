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
    private boolean first = false, last = false;
    private List<Object> input = new ArrayList<>();
    private List<Object> output = new ArrayList<>();

    @Override
    public String toString() {
        return "Task(" + getId() + ", pred=[" + String.join(",", predecessors.stream().map(p -> p.getId()).collect(Collectors.toList())) + "], succ=[" + String.join(",", successors.stream().map(p -> p.getId()).collect(Collectors.toList())) + "])";
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
