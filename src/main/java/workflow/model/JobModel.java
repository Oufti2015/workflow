package workflow.model;

import lombok.Data;
import workflow.interfaces.IJob;

@Data
public class JobModel {
    private final long sequence;
    private final IJob job;

    public JobModel(long sequence, IJob job) {
        this.sequence = sequence;
        this.job = job;
    }
}
