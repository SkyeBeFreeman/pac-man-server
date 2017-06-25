package com.zhtian;

/**
 * Created by Skye on 2017/6/25.
 */
public class RankForm {

    private String username;

    private int maxscore;

    private int ranking;

    private String result;

    public RankForm(String username, int maxscore, int ranking, String result) {
        this.username = username;
        this.maxscore = maxscore;
        this.ranking = ranking;
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMaxscore() {
        return maxscore;
    }

    public void setMaxscore(int maxscore) {
        this.maxscore = maxscore;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
