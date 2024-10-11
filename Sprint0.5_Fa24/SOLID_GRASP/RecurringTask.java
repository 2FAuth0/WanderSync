
import java.time.LocalDate;

public class RecurringTask extends Task {
    private final int start_date;
    private final int frequency;

    public RecurringTask(String title, String description, int frequency, int start_date, String status, String priority) {
        super(title,description,status,start_date,priority);
        this.start_date = start_date;
        this.frequency = frequency;

    }

    @Override
    public int getDue_date() {
        due_date = start_date;
        while (LocalDate.now().toEpochDay() > due_date) {
            due_date += frequency;
        }
        return due_date;
    }
}