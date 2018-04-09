package net.orpiske.qdp.plot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Writes rate data properties to a file
 */
public class QuiverPropertyWriter {
    private static final Logger logger = LoggerFactory.getLogger(QuiverPropertyWriter.class);

    private QuiverPropertyWriter() {}

    /**
     * Save a summary of the analyzed rate data to a properties file named "rate.properties"
     * @param snapshotData the rate data to save
     * @param directory the directory where the saved file will be located
     * @throws IOException if unable to save
     */
    public static void write(final SnapshotData snapshotData, final File directory, final String fileName) throws IOException {
        logger.trace("Writing properties to {}/{}", directory.getPath(), fileName);

        Properties prop = new Properties();

        prop.setProperty("quiverRateGeometricMean", Double.toString(snapshotData.getRateGeometricMean()));
        prop.setProperty("quiverRateMax", Double.toString(snapshotData.getRateMax()));
        prop.setProperty("quiverRateMin", Double.toString(snapshotData.getRateMin()));
        prop.setProperty("quiverRateStandardDeviation", Double.toString(snapshotData.getRateStandardDeviation()));

        prop.setProperty("quiverCpuGeometricMean", Double.toString(snapshotData.getCpuGeometricMean()));
        prop.setProperty("quiverCpuMax", Double.toString(snapshotData.getCpuMax()));
        prop.setProperty("quiverCpuMin", Double.toString(snapshotData.getCpuMin()));
        prop.setProperty("quiverCpuStandardDeviation", Double.toString(snapshotData.getCpuStandardDeviation()));

        prop.setProperty("quiverRssGeometricMean", Double.toString(snapshotData.getRssGeometricMean()));
        prop.setProperty("quiverRssMax", Double.toString(snapshotData.getRssMax()));
        prop.setProperty("quiverRssMin", Double.toString(snapshotData.getRssMin()));
        prop.setProperty("quiverRssStandardDeviation", Double.toString(snapshotData.getRssStandardDeviation()));

        prop.setProperty("quiverLatencyGeometricMean", Double.toString(snapshotData.getLatencyGeometricMean()));
        prop.setProperty("quiverLatencyMax", Double.toString(snapshotData.getLatencyMax()));
        prop.setProperty("quiverLatencyMin", Double.toString(snapshotData.getLatencyMin()));
        prop.setProperty("quiverLatencyStandardDeviation", Double.toString(snapshotData.getLatencyStandardDeviation()));

        if (fileName.contains("receiver")) {
            prop.setProperty("quiverLatencyPercentile50th", Double.toString(snapshotData.getLatencyPercentileAt(50.0)));
            prop.setProperty("quiverLatencyPercentile90th", Double.toString(snapshotData.getLatencyPercentileAt(90.0)));
            prop.setProperty("quiverLatencyPercentile95th", Double.toString(snapshotData.getLatencyPercentileAt(95.0)));
            prop.setProperty("quiverLatencyPercentile99th", Double.toString(snapshotData.getLatencyPercentileAt(99.0)));
            prop.setProperty("quiverLatencyPercentile999th", Double.toString(snapshotData.getLatencyPercentileAt(99.9)));
            prop.setProperty("quiverLatencyPercentile9999th", Double.toString(snapshotData.getLatencyPercentileAt(99.99)));
        }

        prop.setProperty("quiverSamples", Double.toString(snapshotData.getNumberOfSamples()));
        prop.setProperty("quiverErrorCount", Long.toString(snapshotData.getErrorCount()));
        prop.setProperty("quiverSkipCount", Long.toString(snapshotData.getSkipCount()));

        try (FileOutputStream fos = new FileOutputStream(new File(directory, fileName))) {
            prop.store(fos, "qdp-data-plotter");
        }
    }
}
