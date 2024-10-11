public abstract class Task {
    protected String title;
    protected String description;
    protected int due_date;
    protected String status;
    protected String priority;

    public Task(String title,String description,String status, int due_date, String priority) {
        this.description = description;
        this.title = title;
        this.status = status;
        this.due_date = due_date;
        this.priority = priority;
    }


    public abstract int getDue_date();

}
