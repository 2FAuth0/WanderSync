public class ProjectManager extends TeamMember {
    private String clientEmail;
    public ProjectManager(String n, String e, String c) {
        super(n, e);
        this.clientEmail = c;
    }
    @Override
    public void executeTask(Task t) {
        t.setStatus("Done");
    }
}
