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
import com.github.sakserv.utils.JdbcUtils;
import com.github.sakserv.utils.CmdLineUtils;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    
    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static PropertyParser propertyParser = new PropertyParser();
    private static JdbcUtils jdbcUtils = new JdbcUtils();
    private static Connection connection;

    public static void main(String[] args) throws IOException, SQLException {
        
        // Parse the cmdline args and property file
        propertyParser = CmdLineUtils.parseCmdLineAndProps(args, propertyParser);
        
        // Get a JDBC connection, without the database
        String connectionString = JdbcUtils.getConnectionString(
                propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_PORT_KEY));
        connection = JdbcUtils.getConnection(connectionString,
                propertyParser.getProperty(ConfigVars.JDBC_USER_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_PASSWORD_KEY));
                
        // Create the grants
        if(!Boolean.parseBoolean(propertyParser.getProperty(ConfigVars.JDBC_SKIP_GRANTS_KEY))) {
            JdbcUtils.createGrants(connection);
        }

        // Create the database, if required
        createDatabase(connection);
        
        // Get a connection, with the database specified
        connection = getConnection();
        
        // Create the table
        // TODO: Make table definitions more abstract
        createTable(connection);

/*        // Populate the table
        populateTable(connection);*/
        
        // Display success message if we got this far
        logSuccessMessage();
        
    }
    
/*    private static Connection getConnectionNoDb() {
        // Establish the connection, without the db
        String noDbConnString = getNoDbConnString();
        LOG.info("Establishing connection to: " + noDbConnString);
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection(
                    propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_PORT_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_USER_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_PASSWORD_KEY)
            );
        } catch (SQLException e) {
            LOG.error("ERROR: Failed to connect to database at: " + noDbConnString +
                    " using username: " + propertyParser.getProperty(ConfigVars.JDBC_USER_KEY));
            e.printStackTrace();
        }
        return connection;
        
    }
    
    private static Connection getConnection() {
        // Establish the connection, with the db
        String dbConnString = propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_KEY) +
                propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_KEY) + ":" +
                propertyParser.getProperty(ConfigVars.JDBC_PORT_KEY) + "/" +
                propertyParser.getProperty(ConfigVars.JDBC_DATABASE_KEY);
        LOG.info("Establishing connection to: " + getConnString());
        connection = null;
        try {
            connection = jdbcUtils.getConnection(
                    propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_PORT_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_DATABASE_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_USER_KEY),
                    propertyParser.getProperty(ConfigVars.JDBC_PASSWORD_KEY)
            );
        } catch (SQLException e) {
            LOG.error("ERROR: Failed to connect to database at: " + dbConnString +
                    " using username: " + propertyParser.getProperty(ConfigVars.JDBC_USER_KEY));
            e.printStackTrace();
        }
        return connection;
        
    }*/

    
/*    private static void createDatabase(Connection connection) {
        // Create the database
        if (!Boolean.parseBoolean(propertyParser.getProperty(ConfigVars.JDBC_SKIP_DATABASE_CREATE_KEY))) {
            try {
                LOG.info("Creating the database: " + propertyParser.getProperty(ConfigVars.JDBC_DATABASE_KEY));
                Statement statement = connection.createStatement();
                String sql = "CREATE DATABASE IF NOT EXISTS " + propertyParser.getProperty(ConfigVars.JDBC_DATABASE_KEY);
                displayQueryDebug(sql);
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                LOG.error("ERROR: Failed to create database: " + propertyParser.getProperty(ConfigVars.JDBC_DATABASE_KEY));
                e.printStackTrace();
            }
        }
        
    }
    
    private static void createTable(Connection connection) {
        try {
            LOG.info("Creating the table: " + propertyParser.getProperty(ConfigVars.JDBC_TABLE_KEY));
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + propertyParser.getProperty(ConfigVars.JDBC_TABLE_KEY) + " " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    "firstname VARCHAR(255), " +
                    "lastname VARCHAR(255), " +
                    "subject VARCHAR(255), " +
                    "score INTEGER, " +
                    "date DATE, " +
                    "PRIMARY KEY ( id ))";
            displayQueryDebug(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            LOG.error("ERROR: Failed to create table: " + propertyParser.getProperty(ConfigVars.JDBC_TABLE_KEY));
            e.printStackTrace();
        }
        
    }
    
    private static void logSuccessMessage() {
        // Display success message if we got this far
        LOG.info("SUCCESS: Successfully populated table " +
                        propertyParser.getProperty(ConfigVars.JDBC_DATABASE_KEY) +
                        "." + propertyParser.getProperty(ConfigVars.JDBC_TABLE_KEY) +
                        " with " + propertyParser.getProperty(ConfigVars.JDBC_NUM_ROWS_KEY) + " records."
        );
        
    }*/
    
/*    private static void populateTable(Connection connection) {
        Integer totalRows = Integer.parseInt(propertyParser.getProperty(ConfigVars.JDBC_NUM_ROWS_KEY));
        Integer batchSize = Integer.parseInt(propertyParser.getProperty(ConfigVars.JDBC_BATCH_SIZE_KEY));
        
        // Populate the table
        try {
            LOG.info("Populating the table: " + propertyParser.getProperty(ConfigVars.JDBC_TABLE_KEY));
            Statement statement = connection.createStatement();
            for(Integer i=1; i<=totalRows; i++){
                String sql = "INSERT INTO " + propertyParser.getProperty(ConfigVars.JDBC_TABLE_KEY) +
                        " (firstname, lastname, subject, score, date) VALUES ( " +
                        jdbcGenerator.generateRow(true,
                                ConfigVars.DATA_FIRST_NAMES_FILE,
                                ConfigVars.DATA_LAST_NAMES_FILE,
                                ConfigVars.DATA_SCHOOL_SUBJECTS_FILE)
                        + " )";
                //displayQueryDebug(sql);
                statement.addBatch(sql);
                
                // Commit the batch
                if (!i.equals(0) && (i.equals(totalRows) || (i % batchSize) == 0)) {
                    try {
                        Thread.sleep(Long.parseLong(
                                propertyParser.getProperty(ConfigVars.JDBC_BATCH_COMMIT_DELAY_KEY)) * 1000);
                    } catch (InterruptedException e) {}
                    LOG.info("Committing batch of " + batchSize + " rows");
                    statement.executeBatch();
                    LOG.info("Inserted " + i + " total rows");
                }
            }
        } catch (SQLException e) {
            LOG.error("ERROR: Failed to write row to : " + propertyParser.getProperty(ConfigVars.JDBC_TABLE_KEY));
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error("ERROR: Could not read test data files");
            e.printStackTrace();
        }
        
    }*/
    
}
