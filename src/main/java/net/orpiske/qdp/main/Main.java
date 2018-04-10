/*
 *  Copyright 2017 Otavio Rodolfo Piske
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.orpiske.qdp.main;


import net.orpiske.qdp.plot.renderer.IndexRenderer;
import net.orpiske.qdp.plot.renderer.PropertyUtils;
import net.orpiske.qdp.utils.Constants;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Main {
    private static CommandLine cmdLine;

    private static String directory;

    private static void configureCommon(Properties properties) {
        properties.setProperty("log4j.appender.stdout",
                "org.apache.log4j.ConsoleAppender");

        properties.setProperty("log4j.appender.stdout.Target",
                "System.out");
        properties.setProperty("log4j.appender.stdout.layout",
                "org.apache.log4j.PatternLayout");
        properties.setProperty(
                "log4j.appender.stdout.layout.ConversionPattern",
                "%C.%M:%L [%p] %m%n");
        properties.setProperty("log4j.additivity.net.orpiske", "false");
    }

    private static void configureTrace(Properties properties) {
        properties.setProperty("log4j.rootLogger", "DEBUG, stdout");
        properties.setProperty("log4j.logger.net.orpiske", "TRACE, stdout");
    }

    private static void configureDebug(Properties properties) {
        properties.setProperty("log4j.rootLogger", "INFO, stdout");
        properties.setProperty("log4j.logger.net.orpiske", "DEBUG, stdout");
    }

    private static void configureVerbose(Properties properties) {
        properties.setProperty("log4j.rootLogger", "WARN, stdout");
        properties.setProperty("log4j.logger.net.orpiske", "INFO, stdout");
    }

    private static void configureSilent(Properties properties) {
        properties.setProperty("log4j.rootLogger", "WARN, stdout");
        properties.setProperty("log4j.logger.net.orpiske", "WARN, stdout");
    }


    public static void trace() {
        Properties properties = new Properties();

        configureCommon(properties);
        configureTrace(properties);

        PropertyConfigurator.configure(properties);
    }


    /**
     * Configure the output to be at debug level
     */
    public static void debug() {
        Properties properties = new Properties();

        configureCommon(properties);
        configureDebug(properties);

        PropertyConfigurator.configure(properties);
    }


    /**
     * Configure the output to be at verbose (info) level
     */
    public static void verbose() {
        Properties properties = new Properties();

        configureCommon(properties);
        configureVerbose(properties);

        PropertyConfigurator.configure(properties);
    }

    /**
     * Configure the output to be as silent as possible
     */
    public static void silent() {
        Properties properties = new Properties();

        configureCommon(properties);
        configureSilent(properties);

        PropertyConfigurator.configure(properties);
    }

    /**
     * Prints the help for the action and exit
     * @param options the options object
     * @param code the exit code
     */
    private static void help(final Options options, int code) {
        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp(Constants.BIN_NAME, options);
        System.exit(code);
    }

    private static void processCommand(String[] args) {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("d", "dir", true, "the directory containing the files to plot");
        options.addOption("l", "log-level", true, "optional log-level (one of trace, debug, info, silent [ default] )");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            help(options, -1);
        }

        if (cmdLine.hasOption("help")) {
            help(options, 0);
        }

        directory = cmdLine.getOptionValue('d');
        if (directory == null) {
            help(options, -1);
        }

        String logLevel = cmdLine.getOptionValue('l');
        if (logLevel == null) {
            logLevel = "silent";
        }

        switch (logLevel) {
            case "trace": {
                trace();
                break;
            }
            case "debug": {
                debug();
                break;
            }
            case "info": {
                verbose();
                break;
            }
            case "silent":
            default: {
                silent();
                break;
            }
        }
    }

    public static void main(String[] args) {
        processCommand(args);
        try {
            QuiverReportWalker reportWalker = new QuiverReportWalker();

            File outputDirectory = new File(directory);

            reportWalker.walk(outputDirectory);

            IndexRenderer indexRenderer = new IndexRenderer();
            renderSenderPage(indexRenderer, outputDirectory);
            renderReceiverPage(indexRenderer, outputDirectory);
            renderIndexPage(indexRenderer, outputDirectory);

            indexRenderer.copyResources(outputDirectory);

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        System.exit(1);
    }

    private static void renderSenderPage(IndexRenderer indexRenderer, File outputDirectory) throws Exception {
        File outFile = new File(outputDirectory, "sender.html");

        Map<String, Object> context = new HashMap<>();
        PropertyUtils.loadProperties(new File(outputDirectory, "sender-snapshots.properties"), context);

        FileUtils.writeStringToFile(outFile, indexRenderer.render("/report/sender.html", context),
                StandardCharsets.UTF_8);
    }

    private static void renderReceiverPage(IndexRenderer indexRenderer, File outputDirectory) throws Exception {
        File outFile = new File(outputDirectory, "receiver.html");

        Map<String, Object> context = new HashMap<>();
        PropertyUtils.loadProperties(new File(outputDirectory, "receiver-snapshots.properties"), context);

        FileUtils.writeStringToFile(outFile, indexRenderer.render("/report/receiver.html", context),
                StandardCharsets.UTF_8);
    }

    private static void renderIndexPage(IndexRenderer indexRenderer, File outputDirectory) throws Exception {
        File outFile = new File(outputDirectory, "index.html");

        Map<String, Object> context = new HashMap<>();

        FileUtils.writeStringToFile(outFile, indexRenderer.render("/report/index.html", context),
                StandardCharsets.UTF_8);
    }
}
