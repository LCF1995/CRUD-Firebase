package com.example.luciana.crudfirebase.autores;

public class ItemAgendament {

    private String title, obs, dateAgendament, id, userId;
    private String createAt, agendatedAt;
    private int mon, day, status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getDateAgendament() {
        return dateAgendament;
    }

    public void setDateAgendament(String dateAgendament) {
        this.dateAgendament = dateAgendament;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getAgendatedAt() {
        return agendatedAt;
    }

    public void setAgendatedAt(String agendatedAt) {
        this.agendatedAt = agendatedAt;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
