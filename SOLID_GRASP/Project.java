import java.util.ArrayList;

public class Project {
    private String name;
    private String description;
    private String start_date;
    private String end_date;
    private ArrayList<Task> tasks;
    private ArrayList<TeamMember> teamMembers;

    public Project(String n, String d, String start, String end) {
        this.name = n;
        this.description = d;
        this.start_date = start;
        this.end_date = end;
        this.tasks = new ArrayList<>();
        this.teamMembers = new ArrayList<>();
    }

    public void addTask(Task t) {
        tasks.add(t);
    }
    public void removeTask(Task t) {
        tasks.remove(t);
    }
    public void addMember(TeamMember t) {
        teamMembers.add(t);
    }
    public void removeMember(TeamMember t) {
        teamMembers.remove(t);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStart_date() {
        return start_date;
    }


    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
