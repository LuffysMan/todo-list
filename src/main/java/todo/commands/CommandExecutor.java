package todo.commands;

import java.sql.*;

public class CommandExecutor {
    public CommandResponse execute(String command) {
        command = command.trim();
        String[] commandTokens = command.split("\\s+");
        String commandName = commandTokens[0];
        if (commandName.equals("init")) {
            if (commandTokens.length != 1) {
                return new CommandResponse("Bad Command: todo <init>", false);
            }
            try {
                Class.forName("org.sqlite.JDBC");
                try (Connection connection = DriverManager.getConnection("jdbc:sqlite:todo.db")) {
                    try (Statement statement = connection.createStatement()) {
                        statement.executeUpdate("CREATE TABLE tasks(" +
                                "`id` INT NOT NULL, `" +
                                "name` VARCHAR(32) NOT NULL, " +
                                "`begin` DATE NOT NULL, " +
                                "`end` DATE, " +
                                "`parent` INT NOT NULL DEFAULT 1, " +
                                "`children` TEXT, " +
                                "`state` BOOLEAN NOT NULL DEFAULT 0, " +
                                "`desc` TEXT, PRIMARY KEY(`id`))");
//                        statement.execute("INSERT INTO tasks name, begin, state, desc VALUES(life, 1992-1-10, 0, life is the task through your life)");
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                return new CommandResponse(e.getMessage(), false);
            }
            String path = String.format("Initialized empty todo repository in %s/todo.db", System.getProperty("user.dir"));
            return new CommandResponse(path, true);
        }
        return new CommandResponse("Bad Command: todo <init> | <add> | <list> | <set> | <drop>", false);
    }
}
