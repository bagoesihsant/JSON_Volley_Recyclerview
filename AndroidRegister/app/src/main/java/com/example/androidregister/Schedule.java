package com.example.androidregister;

public class Schedule {

    private int schedule_id;
    private String schedule;
    private String schedule_teacher;

    public Schedule(){}

    public Schedule(int paramSchedule_id, String paramSchedule, String paramSchedule_teacher){
        this.schedule_id = paramSchedule_id;
        this.schedule = paramSchedule;
        this.schedule_teacher = paramSchedule_teacher;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSchedule_teacher() {
        return schedule_teacher;
    }

    public void setSchedule_teacher(String schedule_teacher) {
        this.schedule_teacher = schedule_teacher;
    }
}
