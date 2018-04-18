package com.oop.cwk2.thiva;

public class MainOperations {

    private int credits = 10;
    private int betCredits;
    private final static int BET_MAX_CREDIT = 3;
    //Variables to count the total wins and loses
    private int matchWins;
    private int matchLost;
    private int nettedCredits;

    /*
    * Method to add coins
    */
    public void addCoin(){
        if (!(credits == 0)) {
            credits++;
        }
    }

    /*
     * Method to bet Coins
     */
    public void betCoin(){
        if (credits > 0){
            betCredits++;
            nettedCredits++;
            credits--;
        }
    }

    /*
    * Method to bet Maximum Coins
    */
    public void betMax(){
        /* checking if available credits are
             * more than bet
             * maximum credits
             */
        if (credits > 2){
            betCredits += BET_MAX_CREDIT;
            nettedCredits += BET_MAX_CREDIT;
            credits -= BET_MAX_CREDIT;
        }
    }

    /*
    * Method to Reset /
    * gain all the credits which were
    * spent
    */
    public void reset(){
        credits += betCredits;
        betCredits = 0;//to prevent reset more than once
    }



    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getBetCredits() {
        return betCredits;
    }

    public void setBetCredits(int betCredits) {
        this.betCredits = betCredits;
    }

    public int getMatchWins() {
        return matchWins;
    }

    public void setMatchWins(int matchWins) {
        this.matchWins = matchWins;
    }

    public int getMatchLost() {
        return matchLost;
    }

    public void setMatchLost(int matchLost) {
        this.matchLost = matchLost;
    }

    public int getNettedCredits() {
        return nettedCredits;
    }

    public void setNettedCredits(int nettedCredits) {
        this.nettedCredits = nettedCredits;
    }

    public static int getBetMaxCredit() {
        return BET_MAX_CREDIT;
    }
}
