/*
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
package com.github.sakserv.utils;

import com.github.sakserv.config.ConfigVars;
import com.github.sakserv.config.PropertyParser;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class CmdLineUtils {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);

    public static PropertyParser parseCmdLineAndProps(String[] args, PropertyParser propertyParser) throws IOException {
        // Arg parsing for input props file
        Options options = new Options();
        options.addOption("c", true, "configuration file");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd;
        String propertyFileName = "";
        try {
            cmd = parser.parse(options, args);

            // Load the props file
            if(cmd.hasOption("c")) {
                propertyFileName = cmd.getOptionValue("c");
            } else {
                propertyFileName = ConfigVars.DEFAULT_PROPS_FILE;
            }
        } catch(ParseException e) {
            LOG.error("ERROR: Failed to parse commandline args!");
            e.printStackTrace();
        }
        LOG.info("Loading and parsing the property file: " + new File(propertyFileName).getAbsolutePath());
        propertyParser.setPropFileName(propertyFileName);
        propertyParser.parsePropsFile();
        return propertyParser;
    }

}
