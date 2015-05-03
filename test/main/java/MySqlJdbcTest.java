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

import com.github.sakserv.config.ConfigVars;
import com.github.sakserv.config.JsonTableParser;
import com.github.sakserv.config.PropertyParser;
import com.github.sakserv.jdbc.JdbcGenerator;
import com.github.sakserv.jdbc.Table;
import com.github.sakserv.minicluster.impl.HsqldbLocalServer;
import com.github.sakserv.utils.TableUtils;
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
import static org.junit.Assert.*;

public class MySqlJdbcTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(MySqlJdbcTest.class);

    private static JdbcGenerator mysqlJdbcGenerator;
    private static HsqldbLocalServer hsqldbLocalServer;
    private static Connection connection;

    // Setup the property parser
    private static PropertyParser propertyParser = new PropertyParser();
    private static Table table;

    // Table Utils
    private static TableUtils tableUtils = new TableUtils();
    
    @BeforeClass
    public static void setUp() throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException {
        // Load the propertyFile
        propertyParser.setPropFileName(ConfigVars.DEFAULT_PROPS_FILE);
        propertyParser.parsePropsFile();
        
        // Start the HSQLDB
        hsqldbLocalServer = new HsqldbLocalServer.Builder()
                .setHsqldbHostName(propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_VAR))
                .setHsqldbPort(propertyParser.getProperty(ConfigVars.JDBC_PORT_VAR))
                .setHsqldbTempDir(propertyParser.getProperty(ConfigVars.JDBC_TEMP_DIR_VAR))
                .setHsqldbDatabaseName(propertyParser.getProperty(ConfigVars.JDBC_DATABASE_VAR))
                .setHsqldbJdbcConnectionStringPrefix(
                        propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_VAR))
                .setHsqldbJdbcDriver(propertyParser.getProperty(ConfigVars.JDBC_DRIVER_NAME_VAR))
                .setHsqldbCompatibilityMode(propertyParser.getProperty(ConfigVars.JDBC_COMPATIBILITY_MODE_KEY))
                .build();
        hsqldbLocalServer.start();
        
        mysqlJdbcGenerator = new JdbcGenerator();
        mysqlJdbcGenerator.loadJdbcDriver(propertyParser.getProperty(ConfigVars.JDBC_DRIVER_NAME_VAR));
        
        connection = mysqlJdbcGenerator.getConnection(
                propertyParser.getProperty(ConfigVars.JDBC_CONNECTION_STRING_PREFIX_VAR),
                propertyParser.getProperty(ConfigVars.JDBC_HOSTNAME_VAR),
                propertyParser.getProperty(ConfigVars.JDBC_PORT_VAR),
                propertyParser.getProperty(ConfigVars.JDBC_DATABASE_VAR),
                propertyParser.getProperty(ConfigVars.JDBC_USER_VAR),
                propertyParser.getProperty(ConfigVars.JDBC_PASSWORD_VAR)
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
    public void testMySqlJdbcDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        mysqlJdbcGenerator = new JdbcGenerator();
        mysqlJdbcGenerator.loadJdbcDriver(propertyParser.getProperty(ConfigVars.JDBC_DRIVER_NAME_VAR));
    }
    
    @Test
    public void testMySqlJdbcConnection() throws SQLException {
        assertEquals("PUBLIC", connection.getCatalog());
    }
    
    @Test
    public void testTables() throws SQLException {
        // Create the table
        Statement statement = connection.createStatement();
        String sql = table.generateCreateTable();
        statement.executeQuery(sql);
        
        List<Table> tables = mysqlJdbcGenerator.getTableList(connection);
        assertEquals(tables.size(), 1);
        assertThat(tables.get(0).getTableName().toLowerCase(),
                containsString(propertyParser.getProperty(ConfigVars.JDBC_TABLE_VAR)));
    }
    
/*    @Test
    public void testGenerateFirstName() throws IOException {
        assertTrue(mysqlJdbcGenerator.generateFirstName(ConfigVars.DATA_FIRST_NAMES_FILE) instanceof String);
    }
    
    @Test
    public void testGenerateRows() throws IOException {
        Integer rowCount = Integer.parseInt(propertyParser.getProperty(ConfigVars.JDBC_NUM_ROWS_VAR));
        for(int i = 0; i < rowCount; i++ ) {
            LOG.info("ROW: " + mysqlJdbcGenerator.generateRow(
                    Boolean.parseBoolean(propertyParser.getProperty(ConfigVars.JDBC_AUTO_INCREMENT_ID_VAR)),
                    ConfigVars.DATA_FIRST_NAMES_FILE,
                    ConfigVars.DATA_LAST_NAMES_FILE,
                    ConfigVars.DATA_SCHOOL_SUBJECTS_FILE).toString());
        }
    }*/
    
    private static void setCompatibilityMode() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(hsqldbLocalServer.getHsqldbCompatibilityModeStatement());
    }
}
