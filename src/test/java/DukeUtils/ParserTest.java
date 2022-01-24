package DukeUtils;

import Command.*;
import Task.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    @Test
    @DisplayName("Should parse to ExitCommand")
    public void testParsedToExitCommand() {
        try {
            ExitCommand exitCommand = new ExitCommand();
            Command parsedCommand = Parser.parse("bye");
            assertEquals(exitCommand, parsedCommand);
        } catch (CortanaException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    @DisplayName("Should parse to ListCommand")
    public void testParsedToListCommand() {
        try {
            ListCommand listCommand = new ListCommand();
            Command parsedCommand = Parser.parse("list");
            assertEquals(listCommand, parsedCommand);
        } catch (CortanaException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    @DisplayName("Should parse to MarkCommand")
    public void testParsedToMarkCommand() {
        try {
            MarkCommand markCommand = new MarkCommand(0);
            Command parsedCommand = Parser.parse("mark 1");
            assertEquals(markCommand, parsedCommand);
        } catch (CortanaException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    @DisplayName("Should parse to UnmarkCommand")
    public void testParsedToUnmarkCommand() {
        try {
            UnmarkCommand unmarkCommand = new UnmarkCommand(0);
            Command parsedCommand = Parser.parse("unmark 1");
            assertEquals(unmarkCommand, parsedCommand);
        } catch (CortanaException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    @DisplayName("Should parse to DeleteCommand")
    public void testParsedToDeleteCommand() {
        try {
            DeleteCommand deleteCommand = new DeleteCommand(0);
            Command parsedCommand = Parser.parse("delete 1");
            assertEquals(deleteCommand, parsedCommand);
        } catch (CortanaException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    @DisplayName("Should parse to DeleteAllCommand")
    public void testParsedToDeleteAllCommand() {
        try {
            DeleteAllCommand deleteAllCommand = new DeleteAllCommand();
            Command parsedCommand = Parser.parse("delete all");
            assertEquals(deleteAllCommand, parsedCommand);
        } catch (CortanaException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    @DisplayName("Should parse to ShowAllTasksOnSameDateCommand")
    public void testParsedToShowAllTasksOnSameDateCommand() {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse("2022-01-24 1800",  DateTimeFormatter.ofPattern("yyyy-M-d HHmm"));
            ShowAllTasksOnSameDateCommand showAllTasksOnSameDateCommand = new ShowAllTasksOnSameDateCommand(localDateTime, "2022-01-24 1800");
            Command parsedCommand = Parser.parse("show all 2022-01-24 1800");
            assertEquals(showAllTasksOnSameDateCommand, parsedCommand);
        } catch (CortanaException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    @DisplayName("Should ignore and throw exception")
    public void InvalidCommand() {
        try {
            Parser.parse("something invalid");
            fail();
        } catch (CortanaException e) {
            assertEquals("I don't know what that means :(", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should parse to AddCommand")
    public void testParsedToAddCommand() {
        try {
            Todo dummyTodo = new Todo("watch lecture");
            AddCommand markCommand = new AddCommand(dummyTodo);
            Command parsedCommand = Parser.parse("todo watch lecture");
            assertEquals(markCommand, parsedCommand);
        } catch (CortanaException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}