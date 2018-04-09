package net.orpiske.qdp.plot;

import net.orpiske.qdp.plot.exceptions.EmptyDataSet;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class QuiverPropertyWriterTest {

    private void checkProperties(final String fileName, final String propertiesFileName) throws IOException {
        DefaultSnapshotProcessor processor = new DefaultSnapshotProcessor();

        SnapshotReader reader = new SnapshotReader(processor);
        reader.read(fileName);

        File outDirectory = (new File(fileName)).getParentFile();

        SnapshotData snapshotData = processor.getSnapshotData();
        QuiverPropertyWriter.write(snapshotData, outDirectory, propertiesFileName);

        File outFile = new File(outDirectory, propertiesFileName);
        assertTrue("Sender properties does not exist", outFile.exists());
    }

    @Test
    public void testSenderPlotter() throws IOException, EmptyDataSet {
        String fileName = this.getClass().getResource("sender-snapshots.csv").getPath();

        checkProperties(fileName, "quiver-sender.properties");
    }

    @Test
    public void testReceiverPlotter() throws IOException, EmptyDataSet {
        String fileName = this.getClass().getResource("receiver-snapshots.csv").getPath();

        checkProperties(fileName, "quiver-receiver.properties");
    }
}
