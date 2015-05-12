package com.github.sakserv.utils;/*
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

import com.github.sakserv.config.ConfigVars;
import com.github.sakserv.config.JsonTableParser;
import com.github.sakserv.config.PropertyParser;
import com.github.sakserv.db.Table;
import com.github.sakserv.minicluster.impl.HsqldbLocalServer;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MySqlJdbcTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(MySqlJdbcTest.class);

    private static JdbcUtils jdbcUtils;
    private static HsqldbLocalServer hsqldbLocalServer;
    private static Connection connection;

    // Setup the property parser
    private static PropertyParser propertyParser = new PropertyParser();
    private static Table table;

    // Table Utils
    private static TableUtils tableUtils = new TableUtils();
    
    @BeforeClass
    public static void setUp() throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, ParseException, IOException {
        // Load the propertyFile
        propertyParser.setPropFileName(ConfigVars.DEFAULT_PROPS_FILE);
        propertyParser.parsePropsFile();
        
        // Start the HSQLDB
        hsqldbLocalServer = new HsqldbLocalServer.Builder()
                .setHsqldbHostName(propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_KEY))
                .setHsqldbPort(propertyParser.getProperty(ConfigVars.JDBC_PORT_KEY))
                .setHsqldbTempDir(propertyParser.getProperty(ConfigVars.JDBC_TEMP_DIR_KEY))
                .setHsqldbDatabaseName(propertyParser.getProperty(ConfigVars.JDBC_DATABASE_KEY))
                .setHsqldbJdbcConnectionStringPrefix(
                        propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_KEY))
                .setHsqldbJdbcDriver(propertyParser.getProperty(ConfigVars.JDBC_DRIVER_NAME_KEY))
                .setHsqldbCompatibilityMode(propertyParser.getProperty(ConfigVars.JDBC_COMPATIBILITY_MODE_KEY))
                .build();
        hsqldbLocalServer.start();
        
        jdbcUtils = new JdbcUtils();
        jdbcUtils.loadJdbcDriver(propertyParser.getProperty(ConfigVars.JDBC_DRIVER_NAME_KEY));

        connection = jdbcUtils.getConnection(
                propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_PORT_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_USER_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_PASSWORD_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_DATABASE_KEY)
        );
        setCompatibilityMode();

        JsonTableParser jsonTableParser = new JsonTableParser(ConfigVars.DEFAULT_TABLE_DEFINITION);
        table = tableUtils.createTableObjFromJsonString(jsonTableParser.getJsonFileContents());

    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
        hsqldbLocalServer.stop();
    }

    @Test
    public void testConnectionNoDb() throws Exception {
        connection = jdbcUtils.getConnection(
                propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_PORT_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_USER_KEY),
                propertyParser.getProperty(ConfigVars.JDBC_PASSWORD_KEY)
        );
        setCompatibilityMode();

        JsonTableParser jsonTableParser = new JsonTableParser(ConfigVars.DEFAULT_TABLE_DEFINITION);
        table = tableUtils.createTableObjFromJsonString(jsonTableParser.getJsonFileContents());
    }
    
    @Test
    public void testMySqlJdbcDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        jdbcUtils = new JdbcUtils();
        jdbcUtils.loadJdbcDriver(propertyParser.getProperty(ConfigVars.JDBC_DRIVER_NAME_KEY));
    }
    
    @Test
    public void testMySqlJdbcConnection() throws SQLException {
        assertEquals("PUBLIC", connection.getCatalog());
    }
    
    @Test
    public void testTables() throws SQLException {
        // Create the table
        Statement statement = connection.createStatement();
        String sql = table.generateCreateTableStatement();
        statement.executeQuery(sql);
        
        List<Table> tables = jdbcUtils.getTableList(connection);
        assertEquals(tables.size(), 1);
        assertThat(tables.get(0).getTableName().toLowerCase(),
                containsString(propertyParser.getProperty(ConfigVars.JDBC_TABLE_KEY)));
    }
    
    private static void setCompatibilityMode() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(hsqldbLocalServer.getHsqldbCompatibilityModeStatement());
    }
}
