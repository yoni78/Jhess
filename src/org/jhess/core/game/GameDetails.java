package org.jhess.core.game;

import java.time.LocalDateTime;
import java.util.Date;

public class GameDetails {

    private String event;
    private String site;
    private LocalDateTime date;
    private String round;
    private String white;
    private String black;
    private String result;
    private String plyCount;
    // private final String timeControl; TODO
    private LocalDateTime startTime;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getWhite() {
        return white;
    }

    public void setWhite(String white) {
        this.white = white;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPlyCount() {
        return plyCount;
    }

    public void setPlyCount(String plyCount) {
        this.plyCount = plyCount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
