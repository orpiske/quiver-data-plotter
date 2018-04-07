package net.orpiske.qdp.plot;

import java.time.Instant;

public class DefaultSnapshotProcessor implements SnapshotProcessor {
    private SnapshotData snapshotData;

    /*
        This is a snipped of how Quiver does the calculation

        stime = (ssnap.timestamp - self.start_time) / 1000
        srate = ssnap.period_count / (ssnap.period / 1000)
        scpu = (ssnap.period_cpu_time / ssnap.period) * 100
        srss = ssnap.rss / (1000 * 1024)
     */

    @Override
    public void process(final String timestamp, final String period, final String count, final String periodCount,
                        final String latency, final String cpuTime, final String periodCpuTime, final String rss)
    {
        SnapshotInfo snapshotInfo = new SnapshotInfo();

        long convertedTimestamp = Long.parseLong(timestamp);
        snapshotInfo.setTimestamp(Instant.ofEpochMilli(convertedTimestamp));

        int convertedPeriod = Integer.parseInt(period);
        long convertedCount = Long.parseLong(count);
        snapshotInfo.setCount(convertedCount);

        long convertedPeriodCount = Long.parseLong(periodCount);
        double rate = convertedPeriodCount / (convertedPeriod / 1000);
        snapshotInfo.setRate(rate);

        double convertedPeriodCpuTime = Double.parseDouble(periodCpuTime);
        double cpu = (convertedPeriodCpuTime / convertedPeriod) * 100;
        snapshotInfo.setCpu(cpu);

        double convertedRss = Double.parseDouble(rss);
        snapshotInfo.setRss(convertedRss / (1000 * 1024));

        snapshotData.add(snapshotInfo);
    }
}
