package workflow.model;

import workflow.exceptions.XMLFileException;
import workflow.interfaces.IJob;
import workflow.schema.Dependency;
import workflow.schema.Job;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowBuilder {
    private final String xmlFileName;
    private Map<String, TaskModel> tasksMap = new HashMap<>();

    public WorkflowBuilder(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public WorkflowModel build() throws XMLFileException {
        File file = new File(xmlFileName);
        WorkflowModel result = new WorkflowModel();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(workflow.schema.Workflow.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            workflow.schema.Workflow workflow = (workflow.schema.Workflow) jaxbUnmarshaller.unmarshal(file);
            extracttasks(workflow, result);
        } catch (JAXBException j) {
            throw new XMLFileException("Parsing error", j);
        }
        return result;
    }

    private void extracttasks(workflow.schema.Workflow workflow, WorkflowModel result) throws XMLFileException {
        List<workflow.schema.Task> tasks = workflow.getTask();
        for (workflow.schema.Task task : tasks) {
            TaskModel newTask = new TaskModel();
            newTask.setId(task.getId());
            newTask.setFirst(task.isEntry());
            newTask.setLast(task.isExit());

            tasksMap.put(newTask.getId(), newTask);

            extractDependencies(task, newTask);
            extractJobs(task, newTask);
            result.getTasks().add(newTask);
            if (task.isEntry()) {
                result.setStart(newTask);
            }
            if (task.isExit()) {
                result.setEnd(newTask);
            }
        }
    }

    private void extractDependencies(workflow.schema.Task task, TaskModel newTask) throws XMLFileException {
        if (task.getDependencies() != null) {
            for (Dependency dep : task.getDependencies().getDependency()) {
                TaskModel previousTask = tasksMap.get(dep.getDependsOn());
                if (previousTask == null) {
                    throw new XMLFileException("Unknown dependency " + dep.getDependsOn() + " in task " + newTask.getId());
                }
                newTask.getPredecessors().add(previousTask);
                previousTask.getSuccessors().add(newTask);
            }
        }
    }

    private void extractJobs(workflow.schema.Task task, TaskModel newTask) throws XMLFileException {
        if (task.getJobs() == null) {
            throw new XMLFileException("No jobs defined on Task " + newTask.getId());
        }

        for (Job job : task.getJobs().getJob()) {
            try {
                Class<IJob> cls = (Class<IJob>) Class.forName(job.getClazz());
                newTask.getJobs().add(new JobModel(job.getSequence(), cls.newInstance()));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new XMLFileException("Cannot create instance of " + job.getClazz(), e);
            }
        }
    }
}
