package todo.story1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.commands.CommandExecutor;
import todo.commands.CommandResponse;

import java.sql.*;

import static todo.AssertHelper.assertFailedResponse;
import static todo.AssertHelper.assertSuccessResponse;
import static org.junit.jupiter.api.Assertions.*;

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
    void should_create_table_tasks_when_init() throws SQLException {
        CommandResponse response = executor.execute("init");
        assertSuccessResponse("Initialized empty todo repository in /home/luffy/Workspace/Project/todo-list/todo.db", response);

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:todo.db")) {
            assertDoesNotThrow(() -> {
                try (Statement statement = connection.createStatement()) {
                    statement.executeQuery("SELECT * FROM tasks");
                }
            });
        }
    }

    @Test
    void should_do_nothing_when_init_if_table_tasks_exists() {
        CommandResponse response = executor.execute("init");
        assertSuccessResponse("An todo repository already exists", response);
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

    @Test
    void should_fail_if_more_than_one_command_when_init() {
        final CommandResponse response = executor.execute("init database");
        assertFailedResponse("Bad Command: todo <init>", response);
    }

    @Test
    void should_fail_if_no_command() {
        final CommandResponse response = executor.execute("");
        assertFailedResponse("Bad Command: todo <init> | <add> | <list> | <set> | <drop>", response);
    }
}
