package workflow.interfaces;

import java.util.List;

public interface ITask {
    public List<Object> input();

    public void giveOutput(Object output);
}
