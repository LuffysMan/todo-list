package todo.story1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.commands.CommandExecutor;
import todo.commands.CommandResponse;

import static todo.AssertHelper.assertFailedResponse;
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

    @Test
    void should_ignore_spaces_in_head_and_tail() {
        final CommandResponse response = executor.execute("  init  ");
        assertSuccessResponse("Initialized empty todo repository in /home/luffy/Workspace/Project/todo-list/todo.db", response);
    }

    @Test
    void should_fail_if_cannot_match_command_name() {
        final CommandResponse response = executor.execute("initial");
        assertFailedResponse("Bad Command: todo <init> | <add> | <list> | <set> | <drop>", response);
    }
}
