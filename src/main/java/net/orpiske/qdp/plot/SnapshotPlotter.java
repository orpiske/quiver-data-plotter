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

package net.orpiske.qdp.plot;

import net.orpiske.qdp.plot.exceptions.EmptyDataSet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class SnapshotPlotter extends AbstractPlotter<SnapshotData> {
    private final File outputDir;
    private final String baseName;


    public SnapshotPlotter(final File outputDir, final String baseName) {
        this.outputDir = outputDir;
        this.baseName = baseName;
    }

    protected void updateChart(final String title, final String seriesName, final String xTitle, final String yTitle) {
        getChartProperties().setTitle(title);
        getChartProperties().setSeriesName(seriesName);
        getChartProperties().setxTitle(xTitle);
        getChartProperties().setyTitle(yTitle);
    }

    protected void plotSnapshot(final List<Date> xData, final List<? extends Number> yData) throws IOException, EmptyDataSet {
        updateChart("Rate distribution over time", "Throughput rate", "", "Messages p/ second");

        File outputFile = new File(outputDir, baseName + "_rate.png");

        plotCommon(xData, yData, outputFile);
    }

    protected void plotCpu(final List<Date> xData, final List<? extends Number> yData) throws IOException, EmptyDataSet {
        updateChart("CPU utilization over time", "CPU", "", "Utilization (in %)");

        File outputFile = new File(outputDir, baseName + "_cpu.png");
        plotCommon(xData, yData, outputFile);
    }

    protected void plotRss(final List<Date> xData, final List<? extends Number> yData) throws IOException, EmptyDataSet {
        updateChart("RSS utilization over time", "Memory", "", "Megabytes");

        File outputFile = new File(outputDir, baseName + "_rss.png");
        plotCommon(xData, yData, outputFile);
    }

    protected void plotLatency(final List<Date> xData, final List<? extends Number> yData) throws IOException, EmptyDataSet {
        updateChart("Latency distribution over time", "Latency", "", "Milliseconds");

        File outputFile = new File(outputDir, baseName + "_latency.png");
        plotCommon(xData, yData, outputFile);
    }

    @Override
    public void plot(SnapshotData data) throws IOException, EmptyDataSet {
        plotSnapshot(data.getRatePeriods(), data.getRateValues());
        plotCpu(data.getRatePeriods(), data.getCpuValues());
        plotRss(data.getRatePeriods(), data.getRssValues());

        if (baseName.contains("receiver")) {
            plotLatency(data.getRatePeriods(), data.getLatencyValues());
        }
    }
}
