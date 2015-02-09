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
package com.github.sakserv;

import com.github.sakserv.config.ConfigVars;
import com.github.sakserv.config.PropertyParser;
import org.apache.commons.cli.*;

public class Driver {
    
    private static PropertyParser propertyParser = new PropertyParser();

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("c", true, "configuration file");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        if(cmd.hasOption("c")) {
            propertyParser.setPropFileName(cmd.getOptionValue("c"));
        } else {
            propertyParser.setPropFileName(ConfigVars.DEFAULT_PROPS_FILE);
        }

    }
    
}
