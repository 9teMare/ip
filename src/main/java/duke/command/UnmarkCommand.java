package duke.command;

import java.util.Objects;

import duke.task.Task;
import duke.utils.CortanaException;
import duke.utils.Storage;
import duke.utils.TaskList;
import duke.utils.Ui;

/**
 * The type Unmark command.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Instantiates a new Unmark command.
     *
     * @param index the index of task to be unmarked
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Execute mark task as undone operation.
     *
     * @param taskList the task list to operate on
     * @param ui the ui to operate on
     * @param storage the storage to operate on
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) throws CortanaException {
        try {
            Task task = taskList.getTaskList().get(index);
            task.markAsUndone();
            storage.writeFile(taskList);
            return ui.unmarked(task);
        } catch (Exception e) {
            assert index <= 0 : "Index is greater than 0";
            throw new CortanaException("No such task!");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            UnmarkCommand unmarkCommand = (UnmarkCommand) obj;
            return unmarkCommand.index == this.index;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
