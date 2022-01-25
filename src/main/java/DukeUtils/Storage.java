package DukeUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import Task.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The type Storage.
 */
public class Storage {
    private final String filePath;
    private static File taskFile;

    /**
     * Instantiates a new Storage.
     *
     * @param filePath the file path for storing/retrieving the todolist
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        createDirectoryAndFileIfNotExist();
    }

    /**
     * Create data directory and task.txt if not already exist.
     */
    public void createDirectoryAndFileIfNotExist() {
        try {
            File directory = new File(filePath);
            boolean wasDirectoryCreated = directory.mkdir();
            if (wasDirectoryCreated) {
                System.out.println("Created directory " + directory);
            }
            taskFile = new File(filePath + "task.txt");
            boolean wasFileCreated = taskFile.createNewFile();
            if (wasFileCreated) {
                System.out.println("Created task.txt under " + directory);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong during creating directory or creating file");
        }
    }

    /**
     * Load tasks from task.txt into an arraylist.
     *
     * @return the arraylist containing all tasks inside task.txt
     */
    public ArrayList<Task> loadFile() {
        createDirectoryAndFileIfNotExist();
        ArrayList<Task> tasksArrayList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(taskFile);
            Scanner scanner = new Scanner(fileInputStream);
            while(scanner.hasNextLine()) {
                Task taskToAdd = fileToTask(scanner.nextLine());
                tasksArrayList.add(taskToAdd);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return tasksArrayList;
    }

    /**
     * Write tasks from TaskList into task.txt.
     *
     * @param tasks the TaskList tasks
     * @throws IOException the io exception
     */
    public void writeFile(TaskList tasks) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(taskFile, false);
        for (Task task : tasks.tasksArrayList) {
            String taskToWrite = task.toString() + '\n';
            fileOutputStream.write(taskToWrite.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Parse the task in string from task.txt into actual task.
     *
     * @param taskInString the task in string stored inside task.txt
     * @return the actual task
     */
    public static Task fileToTask(String taskInString) {
        char type = taskInString.charAt(1);
        boolean isDone = taskInString.charAt(4) == 'X';
        DateTimeFormatter formatter;
        LocalDateTime localDateTime;
        LocalDate localDate;
        try {
            if (type == 'D') { //deadline
                String[] actualTask = taskInString.substring(7).split("\\(by: ");
                String description = actualTask[0];
                String by = actualTask[1].replaceAll("\\)", "");
                boolean hasTime = Pattern.compile("[A-Za-z]* \\d{1,2}, \\d{4} \\d{4}(AM|PM)").matcher(by).find();
                if (hasTime) {
                    formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hhmma", Locale.ENGLISH);
                    localDateTime = LocalDateTime.parse(by, formatter); //convert from hhmmaa to HHmm
                } else {
                    formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
                    localDate = LocalDate.parse(by, formatter);
                    localDateTime = LocalDateTime.of(localDate, LocalTime.MAX);
                }
                Deadline deadline = new Deadline(description, localDateTime);
                deadline.isDone = isDone;
                return deadline;
            } else if (type == 'E') { //event
                String[] actualTask = taskInString.substring(7).split("\\(at: ");
                String description = actualTask[0];
                String at = actualTask[1].replaceAll("\\)", "");
                boolean hasTime = Pattern.compile("[A-Za-z]* \\d{1,2}, \\d{4} \\d{4}(AM|PM)").matcher(at).find();
                if (hasTime) {
                    formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hhmma", Locale.ENGLISH);
                    localDateTime = LocalDateTime.parse(at, formatter); //convert from hhmmaa to HHmm
                } else {
                    formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
                    localDate = LocalDate.parse(at, formatter);
                    localDateTime = LocalDateTime.of(localDate, LocalTime.MAX);
                }
                Event event = new Event(description, localDateTime);
                event.isDone = isDone;
                return event;
            } else { //todo
                String description = taskInString.substring(7);
                Todo todo = new Todo(description);
                todo.isDone = isDone;
                return todo;
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
