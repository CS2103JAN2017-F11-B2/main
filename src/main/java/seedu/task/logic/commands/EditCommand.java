package seedu.task.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.task.commons.core.Messages;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskTime;
import seedu.task.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of an existing task identified "
			+ "by the index number used in the last task listing. "
			+ "Existing values will be overwritten by the input values.\n"
			+ "Parameters: INDEX (must be a positive integer) [NAME] [d/DATE] [s/START_TIME] [e/END_TIME] [m/DESCRIPTION]\n"
			+ "Example: " + COMMAND_WORD + " 1 d/140317 s/1200";

	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
	public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

	private final int filteredTaskListIndex;
	private final EditTaskDescriptor editTaskDescriptor;

	/**
	 * @param filteredTaskListIndex
	 *            the index of the task in the filtered task list to edit
	 * @param editTaskDescriptor
	 *            details to edit the task with
	 */
	public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
		assert filteredTaskListIndex > 0;
		assert editTaskDescriptor != null;

		// converts filteredTaskListIndex from one-based to zero-based.
		this.filteredTaskListIndex = filteredTaskListIndex - 1;

		this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
	}

	@Override
	public CommandResult execute() throws CommandException {
		List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

		if (filteredTaskListIndex >= lastShownList.size()) {
			throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
		Task editedTask = createEditedPerson(taskToEdit, editTaskDescriptor);

		try {
			model.updateTask(filteredTaskListIndex, editedTask);
		} catch (UniqueTaskList.DuplicateTaskException dpe) {
			throw new CommandException(MESSAGE_DUPLICATE_TASK);
		}
		model.updateFilteredListToShowAll();
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
	}

	/**
	 * Creates and returns a {@code Task} with the details of {@code taskToEdit}
	 * edited with {@code editTaskDescriptor}.
	 */
	private static Task createEditedPerson(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
		assert taskToEdit != null;

		TaskName updatedTaskName = editTaskDescriptor.getTaskName().orElseGet(taskToEdit::getTaskName);
		TaskDate updatedTaskDate = editTaskDescriptor.getTaskDate().orElseGet(taskToEdit::getTaskDate);
		TaskTime updatedTaskStartTime = editTaskDescriptor.getTaskStartTime().orElseGet(taskToEdit::getTaskStartTime);
		TaskTime updatedTaskEndTime = editTaskDescriptor.getTaskEndTime().orElseGet(taskToEdit::getTaskEndTime);
		String updatedTaskDescription = editTaskDescriptor.getTaskDescription()
				.orElseGet(taskToEdit::getTaskDescription);

		return new Task(updatedTaskName, updatedTaskDate, updatedTaskStartTime, updatedTaskEndTime,
				updatedTaskDescription);
	}

	/**
	 * Stores the details to edit the person with. Each non-empty field value
	 * will replace the corresponding field value of the person.
	 */
	public static class EditTaskDescriptor {
		private Optional<TaskName> taskName = Optional.empty();
		private Optional<TaskDate> taskDate = Optional.empty();
		private Optional<TaskTime> taskStartTime = Optional.empty();
		private Optional<TaskTime> taskEndTime = Optional.empty();
		private Optional<String> taskDescription = Optional.empty();

		public EditTaskDescriptor() {
		}

		public EditTaskDescriptor(EditTaskDescriptor toCopy) {
			this.taskName = toCopy.getTaskName();
			this.taskDate = toCopy.getTaskDate();
			this.taskStartTime = toCopy.getTaskStartTime();
			this.taskEndTime = toCopy.getTaskEndTime();
			this.taskDescription = toCopy.getTaskDescription();
		}

		/**
		 * Returns true if at least one field is edited.
		 */
		public boolean isAnyFieldEdited() {
			return CollectionUtil.isAnyPresent(this.taskName, this.taskDate, this.taskStartTime, this.taskEndTime,
					this.taskDescription);
		}

		public void setTaskName(Optional<TaskName> taskName) {
			assert taskName != null;
			this.taskName = taskName;
		}

		public Optional<TaskName> getTaskName() {
			return taskName;
		}

		public void setTaskDate(Optional<TaskDate> date) {
			assert date != null;
			this.taskDate = date;
		}

		public Optional<TaskDate> getTaskDate() {
			return taskDate;
		}

		public void setTaskStartTime(Optional<TaskTime> startTime) {
			assert startTime != null;
			this.taskStartTime = startTime;
		}

		public Optional<TaskTime> getTaskStartTime() {
			return taskStartTime;
		}

		public void setTaskEndTime(Optional<TaskTime> endTime) {
			assert endTime != null;
			this.taskEndTime = endTime;
		}

		public Optional<TaskTime> getTaskEndTime() {
			return taskEndTime;
		}

		public void setTaskDescription(Optional<String> description) {
			assert description != null;
			this.taskDescription = description;
		}

		public Optional<String> getTaskDescription() {
			return taskDescription;
		}

	}
}
