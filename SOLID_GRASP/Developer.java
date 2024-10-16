public class Developer extends TeamMember {
    public Developer(String n, String e) {
        super(n, e);
    }
    @Override
    public void executeTask(Task t) {
        t.setStatus("Opened PR, awaiting review.");
    }
}
