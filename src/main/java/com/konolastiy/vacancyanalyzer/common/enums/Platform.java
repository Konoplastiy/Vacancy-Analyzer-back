package com.konolastiy.vacancyanalyzer.common.enums;

public enum Platform {
    ROBOTAUA("https://robota.ua/zapros/programist/ukraine?page="),
    DJINNI("https://djinni.co/jobs/?page="),
    DOU("https://jobs.dou.ua/vacancies/");

    private String link;

    Platform(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}