package net.orpiske.qdp.plot;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;


public class SnapshotReader {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotReader.class);

    private SnapshotProcessor processor;

    public SnapshotReader(SnapshotProcessor snapshotProcessor) {
        this.processor = snapshotProcessor;

        logger.debug("Reading records using the default rate reader");
    }


    public void read(final String filename) throws IOException {
        InputStream fileStream = null;
        InputStream gzipStream = null;
        Reader in = null;

        logger.debug("Reading file {}", filename);

        try {
            fileStream = new FileInputStream(filename);

            in = new InputStreamReader(fileStream);

            Iterable<CSVRecord> records = CSVFormat.RFC4180
                    .withCommentMarker('#')
                    .withRecordSeparator(',')
                    .withQuote('"')
                    .withQuoteMode(QuoteMode.NON_NUMERIC)
                    .parse(in);

            for (CSVRecord record : records) {
                processor.process(record.get(0), record.get(1), record.get(2), record.get(3), record.get(4),
                        record.get(5), record.get(6), record.get(1));
            }
        }
        finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(fileStream);
        }
    }
}
