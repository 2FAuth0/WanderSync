public abstract class TeamMember {
    protected String name;
    protected String email;

    public TeamMember(String n, String e) {
        this.name = n;
        this.email = e;
    }

    public void leaveProject(Project p) {
        p.removeMember(this);
    }
    public void joinProject(Project p) {
        p.addMember(this);
    }
    public abstract void executeTask(Task t);
}
