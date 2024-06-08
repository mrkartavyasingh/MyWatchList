package com.android.mywatchlist.models.submodels;

public class CrewMemberModel {
    private boolean adult;
    private int id;
    private int gender;
    private String job;
    private String name;
    private String known_for_department;
    private String department;
    private String profile_path;

    public boolean isAdult() {
        return adult;
    }

    public int getId() {
        return id;
    }

    public int getGender() {
        return gender;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public String getDepartment() {
        return department;
    }

    public String getProfile_path() {
        return profile_path;
    }

    @Override
    public String toString() {
        return "CrewMemberModel{" +
                "adult=" + adult +
                ", id=" + id +
                ", gender=" + gender +
                ", job='" + job + '\'' +
                ", name='" + name + '\'' +
                ", known_for_department='" + known_for_department + '\'' +
                ", department='" + department + '\'' +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}
