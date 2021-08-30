package todo.commands;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CommandExecutor {
    public CommandResponse execute(String... commandTokens) {

        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:todo.db")) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println(meta.getDriverName());
            }
        } catch (SQLException e) {
            return new CommandResponse("failed to connect connect to database", false);
        }
        String path = String.format("Initialized empty todo repository in %s/todo.db", System.getProperty("user.dir"));
        return new CommandResponse(path, true);
    }
}
