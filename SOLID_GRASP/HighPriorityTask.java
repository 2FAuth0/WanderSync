public class HighPriorityTask extends Task {
    public HighPriorityTask(String title, String description, String status, int due_date) {
        super(title, description, status, due_date, "high");
    }

    public int getDue_date() {
        return due_date;
    }
}
