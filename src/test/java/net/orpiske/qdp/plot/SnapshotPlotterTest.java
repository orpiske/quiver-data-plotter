package net.orpiske.qdp.plot;

import net.orpiske.qdp.plot.exceptions.EmptyDataSet;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class SnapshotPlotterTest {

    private void checkPlottedFile(final String fileName, final String last) {
        String plottedFileName = FilenameUtils.removeExtension(FilenameUtils.removeExtension(fileName)) + last;

        File plottedFile = new File(plottedFileName);
        assertTrue("Missing output file: " + plottedFile, plottedFile.exists());
    }

    private void doSenderPlot(String fileName) throws IOException, EmptyDataSet {
        DefaultSnapshotProcessor processor = new DefaultSnapshotProcessor();
        SnapshotReader reader = new SnapshotReader(processor);

        reader.read(fileName);

        SnapshotData snapshotData = processor.getSnapshotData();

        String baseName = FilenameUtils.removeExtension(fileName);
        SnapshotPlotter snapshotPlotter = new SnapshotPlotter(baseName);

        snapshotPlotter.plot(snapshotData);

        checkPlottedFile(fileName, "_snapshots.png");
        checkPlottedFile(fileName, "_cpu.png");
        checkPlottedFile(fileName, "_rss.png");
    }

    @Test
    public void testSenderPlotter() throws IOException, EmptyDataSet {
        String fileName = this.getClass().getResource("sender-snapshots.csv").getPath();

        doSenderPlot(fileName);
    }

    @Test
    public void testReceiverPlotter() throws IOException, EmptyDataSet {
        String fileName = this.getClass().getResource("receiver-snapshots.csv").getPath();

        doSenderPlot(fileName);
    }
}
