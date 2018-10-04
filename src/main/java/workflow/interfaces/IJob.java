package workflow.interfaces;

public interface IJob {
    public void addInput(Object input);

    public void run();

    public Object output();
}
