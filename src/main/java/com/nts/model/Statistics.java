package com.nts.model;

/**
 * returns the getStatistics based on the transactions for the last 60 secs
 *
 * Created by subramanya on 15/03/17.
 */
public class Statistics {

    /** total sum of the addTransaction value in the last 60 sec*/
    private double sum = 0.0;
    /** average amount of addTransaction in the last 60 sec*/
    private double avg = 0.0;
    /** single lowest addTransaction value in the last 60 sec*/
    private double min = 0.0;
    /** single highest addTransaction value in the last 60 sec*/
    private double max = 0.0;
    /** total num of transactions in the last 60 sec*/
    private long count = 0;


    public Statistics(double sum, double avg, double min, double max, long count) {
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.count = count;
    }
}
