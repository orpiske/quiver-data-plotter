/*
 *  Copyright 2017 Otavio R. Piske <angusyoung@gmail.com>
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

package net.orpiske.qdp.plot.renderer;

import com.google.common.base.Charsets;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.loader.CascadingResourceLocator;
import com.hubspot.jinjava.loader.FileLocator;
import net.orpiske.qdp.plot.renderer.custom.FileExists;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;
import java.util.Map;

/**
 * A base class for rendering reports using Jinja2
 */
public abstract class AbstractRenderer {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRenderer.class);

    private final Jinjava jinjava;

    public AbstractRenderer() {
        JinjavaConfig config = new JinjavaConfig();

        jinjava = new Jinjava(config);
        jinjava.getGlobalContext().registerFilter(new FileExists());

        setupResourceLocator();
    }

    protected Jinjava getJinjava() {
        return jinjava;
    }

    protected void setupResourceLocator() {
        try {
            File currentDir = new File(Paths.get(".").toAbsolutePath().normalize().toString());
            FileLocator currentDirLocator = new FileLocator(currentDir);

            CascadingResourceLocator cr = new CascadingResourceLocator(currentDirLocator);

            jinjava.setResourceLocator(cr);
        } catch (FileNotFoundException e) {
            logger.warn("Unable to find the current working directory: " + e.getMessage(), e);
        }
    }

    /**
     * Render a report
     * @param fileName The resource name to be parsed
     * @param context the Jinja context containing the variables to be used
     * @return A String contained the parsed template
     * @throws Exception If unable to parse the template
     */
    public String render(final String fileName, final Map<String, Object> context) throws Exception {
        String text;

        text = IOUtils.toString(this.getClass().getResourceAsStream(fileName), Charsets.UTF_8);

        return jinjava.render(text, context);
    }

    /**
     * Copy static resources
     * @param path the path to copy to
     * @param resource the resource name
     * @param destinationName the destination name
     * @throws IOException
     */
    protected void copyResources(final File path, final String resource, final String destinationName) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        // Skip duplicate static resources
        File outputFile = new File(path, destinationName);
        if (outputFile.exists()) {
            return;
        }

        try {
            inputStream = this.getClass().getResourceAsStream(resource);
            outputStream = new FileOutputStream(outputFile);

            IOUtils.copy(inputStream, outputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
