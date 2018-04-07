package net.orpiske.qdp.plot;

import java.time.Instant;

/**
 * Represents a single line in one of the Quiver snapshot files after processing
 */
public class SnapshotInfo {
    private Instant timestamp;
    private long count;
    private double rate;
    private double cpu;
    private double rss;

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public double getRss() {
        return rss;
    }

    public void setRss(double rss) {
        this.rss = rss;
    }
}
