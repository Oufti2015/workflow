package workflow.model;

import lombok.Data;
import workflow.interfaces.IJob;

import java.util.ArrayList;
import java.util.List;

@Data
public class Task {
    private List<IJob> jobs = new ArrayList<>();
}
