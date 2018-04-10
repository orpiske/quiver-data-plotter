package net.orpiske.qdp.plot.renderer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EasyRender {
    public static void renderSenderPage(IndexRenderer indexRenderer, File outputDirectory) throws Exception {
        File outFile = new File(outputDirectory, "sender.html");

        Map<String, Object> context = new HashMap<>();
        PropertyUtils.loadProperties(new File(outputDirectory, "sender-snapshots.properties"), context);

        FileUtils.writeStringToFile(outFile, indexRenderer.render("/report/sender.html", context),
                StandardCharsets.UTF_8);
    }

    public static void renderReceiverPage(IndexRenderer indexRenderer, File outputDirectory) throws Exception {
        File outFile = new File(outputDirectory, "receiver.html");

        Map<String, Object> context = new HashMap<>();
        PropertyUtils.loadProperties(new File(outputDirectory, "receiver-snapshots.properties"), context);

        FileUtils.writeStringToFile(outFile, indexRenderer.render("/report/receiver.html", context),
                StandardCharsets.UTF_8);
    }

    public static void renderIndexPage(IndexRenderer indexRenderer, File outputDirectory) throws Exception {
        File outFile = new File(outputDirectory, "index.html");

        Map<String, Object> context = new HashMap<>();

        FileUtils.writeStringToFile(outFile, indexRenderer.render("/report/index.html", context),
                StandardCharsets.UTF_8);
    }
}
