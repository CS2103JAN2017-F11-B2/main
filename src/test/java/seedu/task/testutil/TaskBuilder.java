package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskTime;
import seedu.task.model.task.TaskDate;
/*
import seedu.task.model.task.Phone;
import seedu.task.model.task.Address;
import seedu.task.model.task.Email;
*/
/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withTaskName(String taskName) throws IllegalValueException {
        this.task.setTaskName(new TaskName(taskName));
        return this;
    }

    public TaskBuilder withTaskDate(String taskDate) throws IllegalValueException {
        this.task.setTaskDate(new TaskDate(taskDate));
        return this;
    }
    
    public TaskBuilder withTaskStartTime(String taskStartTime) throws IllegalValueException {
        this.task.setTaskStartTime(new TaskTime(taskStartTime));
        return this;
    }

    public TaskBuilder withTaskEndTime(String taskEndTime) throws IllegalValueException {
        this.task.setTaskEndTime(new TaskTime(taskEndTime));
        return this;
    }

    public TaskBuilder withTaskDescription(String taskDescription) throws IllegalValueException {
        this.task.setTaskDescription(new String(taskDescription));
        return this;
    }
    
    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}