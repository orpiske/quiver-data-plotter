package net.orpiske.qdp.plot;

import net.orpiske.qdp.plot.exceptions.EmptyDataSet;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class SnapshotPlotterTest {

    private void checkPlottedFile(final File path, final String fileName, final String last) {
        String plottedFileName = FilenameUtils.removeExtension(FilenameUtils.removeExtension(fileName)) + last;

        File plottedFile = new File(path, plottedFileName);
        assertTrue("Missing output file: " + plottedFile, plottedFile.exists());
    }

    private void doSenderPlot(File fileName) throws IOException, EmptyDataSet {
        DefaultSnapshotProcessor processor = new DefaultSnapshotProcessor();

        SnapshotReader reader = new SnapshotReader(processor);

        reader.read(fileName);

        SnapshotData snapshotData = processor.getSnapshotData();

        String baseName = FilenameUtils.removeExtension(fileName.getName());
        SnapshotPlotter snapshotPlotter = new SnapshotPlotter(fileName.getParentFile(), baseName);

        snapshotPlotter.plot(snapshotData);

        checkPlottedFile(fileName.getParentFile(), baseName, "_rate.png");
        checkPlottedFile(fileName.getParentFile(), baseName, "_cpu.png");
        checkPlottedFile(fileName.getParentFile(), baseName, "_rss.png");
    }

    @Test
    public void testSenderPlotter() throws IOException, EmptyDataSet {
        String fileName = this.getClass().getResource("sender-snapshots.csv").getPath();

        doSenderPlot(new File(fileName));
    }

    @Test
    public void testReceiverPlotter() throws IOException, EmptyDataSet {
        String fileName = this.getClass().getResource("receiver-snapshots.csv").getPath();

        doSenderPlot(new File(fileName));
    }
}
