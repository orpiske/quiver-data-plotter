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

/**
 * A interface for processing snapshot data.
 */
@FunctionalInterface
public interface SnapshotProcessor {



    /**
     *
     * @param timestamp record timestamp
     * @param period elapsed milliseconds
     * @param count total message count
     * @param periodCount message count within the period
     * @param latency latency
     * @param cpuTime cpu time
     * @param periodCpuTime cpu time within the period
     * @param rss resident set size
     */
    void process(final String timestamp, final String period, final String count, final String periodCount,
                 final String latency, final String cpuTime, final String periodCpuTime, final String rss);
}