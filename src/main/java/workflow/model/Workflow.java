package workflow.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Workflow {

    private List<Task> tasks = new ArrayList<Task>();
}
