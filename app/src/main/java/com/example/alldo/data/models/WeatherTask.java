package com.example.alldo.data.models;

public final class WeatherTask {
    private int id;
    private String title;
    private String details;
    private WeatherCriteria weather;
    private boolean markImp;
    private boolean check;

    public WeatherTask(){

    }

//    getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public WeatherCriteria getWeather() {
        return weather;
    }

    public void setWeather(WeatherCriteria weather) {
        this.weather = weather;
    }

    public boolean isMarkImp() {
        return markImp;
    }

    public void setMarkImp(boolean markImp) {
        this.markImp = markImp;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
