package todo.commands;

public class CommandResponse {
    private final String message;
    private final boolean isSuccess;

    public CommandResponse() {
        this(null, true);
    }

    public CommandResponse(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}

