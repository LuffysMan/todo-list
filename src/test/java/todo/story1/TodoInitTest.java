package todo.story1;

import org.junit.jupiter.api.Test;
import todo.commands.CommandExecutor;
import todo.commands.CommandResponse;

import static todo.AssertHelper.assertSuccessResponse;

public class TodoInitTest {
    private CommandExecutor executor;

    @Test
    void should_create_database_when_init() {
        final CommandResponse response = executor.execute("init");
        assertSuccessResponse("", response);
    }
}
