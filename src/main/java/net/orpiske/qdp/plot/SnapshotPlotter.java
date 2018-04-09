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

import java.io.IOException;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class SnapshotPlotter extends AbstractPlotter<SnapshotData> {
    private final String baseName;


    public SnapshotPlotter(final String baseName) {
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

        plotCommon(xData, yData, baseName + "_snapshots.png");
    }

    protected void plotCpu(final List<Date> xData, final List<? extends Number> yData) throws IOException, EmptyDataSet {
        updateChart("CPU utilization over time", "CPU", "", "Utilization (in %)");

        plotCommon(xData, yData, baseName + "_cpu.png");
    }

    protected void plotRss(final List<Date> xData, final List<? extends Number> yData) throws IOException, EmptyDataSet {
        updateChart("RSS utilization over time", "Memory", "", "Megabytes");

        plotCommon(xData, yData, baseName + "_rss.png");
    }

    @Override
    public void plot(SnapshotData data) throws IOException, EmptyDataSet {
        plotSnapshot(data.getRatePeriods(), data.getRateValues());
        plotCpu(data.getRatePeriods(), data.getCpuValues());
        plotRss(data.getRatePeriods(), data.getRssValues());
    }
}
