package todo.story1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.commands.CommandExecutor;
import todo.commands.CommandResponse;

import static todo.AssertHelper.assertSuccessResponse;

public class TodoInitTest {
    private CommandExecutor executor;

    @BeforeEach
    void setupExecutor() {
        executor = new CommandExecutor();
    }
    @Test
    void should_create_database_when_init() {
        final CommandResponse response = executor.execute("init");
        assertSuccessResponse("Initialized empty todo repository in /home/luffy/Workspace/Project/todo-list/todo.db", response);
    }
}
