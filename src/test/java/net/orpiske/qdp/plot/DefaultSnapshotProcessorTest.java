package net.orpiske.qdp.plot;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DefaultSnapshotProcessorTest {
    @Test
    public void testProcessSender() throws IOException {
        String fileName = this.getClass().getResource("sender-snapshots.csv").getPath();

        DefaultSnapshotProcessor processor = new DefaultSnapshotProcessor();
        SnapshotReader reader = new SnapshotReader(processor);

        reader.read(fileName);

        SnapshotData snapshotData = processor.getSnapshotData();
        assertEquals("Invalid record size", 20, snapshotData.getNumberOfSamples());

        // Basic check on two values previously calculated
        List<Double> rateValues = snapshotData.getRateValues();
        assertEquals(26230.884557721, rateValues.get(5), 0.0001);
        assertEquals(28902.548725637, rateValues.get(18), 0.0001);
        assertEquals(28902.5487, snapshotData.getRateMax(), 0.0001);
        assertEquals(24469.74736, snapshotData.getRateGeometricMean(), 0.0001);

        List<Double> cpuValues = snapshotData.getCpuValues();
        assertEquals(97.902097902, cpuValues.get(17), 0.0001);
        assertEquals(99.400599401, cpuValues.get(9), 0.0001);
        assertEquals(109.8352, snapshotData.getCpuMax(), 0.0001);
        assertEquals(94.62755, snapshotData.getCpuGeometricMean(), 0.0001);

        //
        List<Double> rssValues = snapshotData.getRssValues();
        assertEquals(87.816, rssValues.get(10), 0.0001);
        assertEquals(88.604, rssValues.get(12), 0.0001);
        assertEquals(89.768, snapshotData.getRssMax(), 0.0001);
        rssValues.forEach(System.out::println);
        assertEquals(0.0, snapshotData.getRssMin(), 0.0000);

        assertEquals(0.0, snapshotData.getRssGeometricMean(), 0.0000);
    }

    @Test
    public void testProcessReceiver() throws IOException {
        String fileName = this.getClass().getResource("receiver-snapshots.csv").getPath();

        DefaultSnapshotProcessor processor = new DefaultSnapshotProcessor();
        SnapshotReader reader = new SnapshotReader(processor);

        reader.read(fileName);

        SnapshotData snapshotData = processor.getSnapshotData();
        assertEquals("Invalid record size", 19, snapshotData.getNumberOfSamples());

        // Basic check on two values previously calculated
        List<Double> rateValues = snapshotData.getRateValues();
        assertEquals(25970.02997003, rateValues.get(5), 0.0001);
        assertEquals(45.977011494, rateValues.get(18), 0.0001);

        List<Double> cpuValues = snapshotData.getCpuValues();
        assertEquals(39.48025987, cpuValues.get(17), 0.0001);
        assertEquals(59.440559441, cpuValues.get(9), 0.0001);


        List<Double> rssValues = snapshotData.getRssValues();
        assertEquals(80.432, rssValues.get(10), 0.0001);
        assertEquals(80.432, rssValues.get(12), 0.0001);
    }

    @Test
    public void testProcessSenderNonStrict() throws IOException {
        String fileName = this.getClass().getResource("sender-snapshots.csv").getPath();

        DefaultSnapshotProcessor processor = new DefaultSnapshotProcessor(false);
        SnapshotReader reader = new SnapshotReader(processor);

        reader.read(fileName);

        SnapshotData snapshotData = processor.getSnapshotData();
        assertEquals("Invalid record size", 20, snapshotData.getNumberOfSamples());

        // Basic check on two values previously calculated
        List<Double> rateValues = snapshotData.getRateValues();
        assertEquals(26230.884557721, rateValues.get(5), 0.0001);
        assertEquals(28902.548725637, rateValues.get(18), 0.0001);
        assertEquals(28902.5487, snapshotData.getRateMax(), 0.0001);
        assertEquals(24469.74736, snapshotData.getRateGeometricMean(), 0.0001);

        List<Double> cpuValues = snapshotData.getCpuValues();
        assertEquals(97.902097902, cpuValues.get(17), 0.0001);
        assertEquals(99.400599401, cpuValues.get(9), 0.0001);
        assertEquals(109.8352, snapshotData.getCpuMax(), 0.0001);
        assertEquals(94.62755, snapshotData.getCpuGeometricMean(), 0.0001);

        List<Double> rssValues = snapshotData.getRssValues();
        assertEquals(87.816, rssValues.get(10), 0.0001);
        assertEquals(88.604, rssValues.get(12), 0.0001);
        assertEquals(89.768, snapshotData.getRssMax(), 0.0001);
        assertEquals(86.624, snapshotData.getRssMin(), 0.0000);

        assertEquals(88.15148, snapshotData.getRssGeometricMean(), 0.0001);
    }
}
