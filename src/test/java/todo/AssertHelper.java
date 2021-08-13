package todo;

import todo.commands.CommandResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertHelper {
    public static void assertSuccessResponse(String expectedMessage, CommandResponse response) {
        assertCommandResponse(true, expectedMessage, response);
    }

    public static void assertFailedResponse(String expectedMessage, CommandResponse response) {
        assertCommandResponse(false, expectedMessage, response);
    }

    private static void assertCommandResponse(boolean expectedSuccess, String expectedMessage, CommandResponse response) {
        assertEquals(expectedSuccess, response.isSuccess());
        assertEquals(expectedMessage, response.getMessage());
    }
}
