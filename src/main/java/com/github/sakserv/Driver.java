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
import com.github.sakserv.jdbc.JdbcGenerator;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver {
    
    private static PropertyParser propertyParser = new PropertyParser();

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(Driver.class);
    
    private static JdbcGenerator jdbcGenerator = new JdbcGenerator();

    public static void main(String[] args) {
        
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
        LOG.info("Loading and parsing the property file" + new File(propertyFileName).getAbsolutePath());
        propertyParser.setPropFileName(propertyFileName);
        propertyParser.parsePropsFile();
        
        // Establish the connection
        String noDbConnString = propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_VAR) +
                propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_VAR) + ":" +
                propertyParser.getProperty(ConfigVars.JDBC_PORT_VAR);
        LOG.info("Establishing connection to: " + noDbConnString);
        Connection connection = null;
        try {
            connection = jdbcGenerator.getConnectionNoDb(
                    propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_VAR),
                    propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_VAR),
                    propertyParser.getProperty(ConfigVars.JDBC_PORT_VAR),
                    propertyParser.getProperty(ConfigVars.JDBC_USER_VAR),
                    propertyParser.getProperty(ConfigVars.JDBC_PASSWORD_VAR)
            );
        } catch (SQLException e) {
            LOG.error("ERROR: Failed to connect to database at: " + noDbConnString +
            " using username: " + propertyParser.getProperty(ConfigVars.JDBC_USER_VAR));
            e.printStackTrace();
        }
                
        // Create the grants
        try {
            LOG.info("Running grants for user " + propertyParser.getProperty(ConfigVars.JDBC_USER_VAR) + 
                    " on " + noDbConnString);
            Statement statement = connection.createStatement();
            String sqlGrant = "GRANT ALL PRIVILEGES ON *.* TO " + 
                    "\"" + propertyParser.getProperty(ConfigVars.JDBC_USER_VAR) + "\"@" + 
                    "\"%\" IDENTIFIED BY \"" + propertyParser.getProperty(ConfigVars.JDBC_PASSWORD_VAR) + "\"";
            displayQueryDebug(sqlGrant);
            statement.executeQuery(sqlGrant);
            
            statement = connection.createStatement();
            String sqlFlushPriv = "FLUSH PRIVILEGES";
            displayQueryDebug(sqlFlushPriv);
            statement.executeQuery(sqlFlushPriv);
            
        } catch (SQLException e) {
            LOG.error("ERROR: Error applying grants for user: " + propertyParser.getProperty(ConfigVars.JDBC_USER_VAR));
            e.printStackTrace();
        }

        // Create the database
        try {
            LOG.info("Creating the database: " + propertyParser.getProperty(ConfigVars.JDBC_DATABASE_VAR));
            Statement statement = connection.createStatement();
            String sqlCreateDatabase = "CREATE DATABASE " + propertyParser.getProperty(ConfigVars.JDBC_DATABASE_VAR);
            displayQueryDebug(sqlCreateDatabase);
            statement.executeQuery(sqlCreateDatabase);
        } catch(SQLException e) {
            LOG.error("ERROR: Failed to create database: " propertyParser.getProperty(ConfigVars.JDBC_DATABASE_VAR));
            e.printStackTrace();
        }
        
    }
    
    private static void displayQueryDebug(String sql) {
        LOG.debug("Running the following statement: " + sql);
    }
    
}
