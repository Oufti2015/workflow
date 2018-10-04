package workflow.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Workflow {

    private List<Task> tasks = new ArrayList<Task>();

    private Workflow() {

    }

    public static class Builder {
        private final String xmlFileName;

        public Builder(String xmlFileName) {
            this.xmlFileName = xmlFileName;
        }

        public Workflow build() {
            return new Workflow();
        }
    }
}
