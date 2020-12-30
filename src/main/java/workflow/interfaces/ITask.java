package workflow.interfaces;

import java.util.List;

public interface ITask {
    List<Object> input();

    void giveOutput(Object output);
}
