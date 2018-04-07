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


import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.*;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;

/**
 * A container for the collected rate information
 */
public class SnapshotData
{
    private Set<SnapshotInfo> snapshotInfos = new TreeSet<SnapshotInfo>();
    private SummaryStatistics statistics;
    private long errorCount;
    private long skipCount = 0;

    public void add(SnapshotInfo rateInfo)
    {
        snapshotInfos.add(rateInfo);
    }

    public List<Date> getRatePeriods()
    {
        final List<Date> list = new ArrayList<>(snapshotInfos.size());
        snapshotInfos.forEach(item -> list.add(Date.from(item.getTimestamp())));
        return list;
    }

    public List<Double> getRateValues()
    {
        return snapshotInfos.stream().mapToDouble(SnapshotInfo::getRate).boxed().collect(Collectors.toList());
    }

    private void processRateValues(DoubleConsumer rateValue)
    {
        snapshotInfos.stream().mapToDouble(SnapshotInfo::getRate).forEach(rateValue);
    }

    public List<Double> getCpuValues()
    {
        return snapshotInfos.stream().mapToDouble(SnapshotInfo::getCpu).boxed().collect(Collectors.toList());
    }

    private void processCpuValues(DoubleConsumer cpuValue)
    {
        snapshotInfos.stream().mapToDouble(SnapshotInfo::getCpu).forEach(cpuValue);
    }

    public List<Double> getRssValues()
    {
        return snapshotInfos.stream().mapToDouble(SnapshotInfo::getRss).boxed().collect(Collectors.toList());
    }

    private void processRssValues(DoubleConsumer rssValue)
    {
        snapshotInfos.stream().mapToDouble(SnapshotInfo::getRss).forEach(rssValue);
    }

    private void prepareStatistics()
    {
        if (statistics == null)
        {
            // Use Summary Statistics because the data set might be too large
            // and we don't want to abuse memory usage
            statistics = new SummaryStatistics();
            processRateValues(statistics::addValue);
        }
    }


    public double getGeometricMean()
    {
        prepareStatistics();

        return statistics.getGeometricMean();
    }

    public double getMax()
    {
        prepareStatistics();

        return statistics.getMax();
    }

    public double getMin()
    {
        prepareStatistics();

        return statistics.getMin();
    }

    public double getStandardDeviation()
    {
        prepareStatistics();

        return statistics.getStandardDeviation();
    }

    public int getNumberOfSamples()
    {
        return snapshotInfos.size();
    }

    public long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    public long getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(long skipCount) {
        this.skipCount = skipCount;
    }
}
