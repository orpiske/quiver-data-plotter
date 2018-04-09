package net.orpiske.qdp.main;

import net.orpiske.qdp.plot.*;
import net.orpiske.qdp.plot.exceptions.EmptyDataSet;
import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuiverReportWalker extends DirectoryWalker<File> {
    private static final Logger logger = LoggerFactory.getLogger(QuiverReportWalker.class);

    private void handleSnapshotFile(final File file) throws IOException, EmptyDataSet {
        File outDirectory = file.getParentFile();
        DefaultSnapshotProcessor processor = new DefaultSnapshotProcessor();

        SnapshotReader reader = new SnapshotReader(processor);
        reader.read(file);

        String baseName = FilenameUtils.removeExtension(file.getName());
        SnapshotPlotter snapshotPlotter = new SnapshotPlotter(outDirectory, baseName);

        SnapshotData snapshotData = processor.getSnapshotData();
        snapshotPlotter.plot(snapshotData);

        QuiverPropertyWriter.write(snapshotData, outDirectory, baseName + ".properties");
    }

    @Override
    protected void handleFile(File file, int depth, Collection<File> results) throws IOException {
        if (file.getName().endsWith(".csv") && file.getName().contains("snapshots")) {
            try {
                handleSnapshotFile(file);
            } catch (EmptyDataSet emptyDataSet) {
                throw new IOException(emptyDataSet);
            }
        }
    }

    public void walk(final File initialDirectory) throws IOException {
        logger.debug("Loading classes from directory {}", initialDirectory);

        if (!initialDirectory.exists()) {
            throw new IOException("The input directory " + initialDirectory.getPath() + " does not exist");
        }

        try {
            List<File> fileList = new ArrayList<>();

            walk(initialDirectory, fileList);
        } catch (IOException e) {
            logger.error("Unable to walk the whole directory: " + e.getMessage(), e);
            logger.error("Returning a partial list of all the repository data due to errors");
        }
    }
}
