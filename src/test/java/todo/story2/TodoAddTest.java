package todo.story2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.commands.CommandExecutor;

public class TodoAddTest {
    private CommandExecutor executor;

    @BeforeEach
    void setupExecutor() {
        executor = new CommandExecutor();
    }

    @Test
    void should_able_to_add_task_with_all_filed_specified() {

    }

}
