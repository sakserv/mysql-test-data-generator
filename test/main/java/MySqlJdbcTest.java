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
import com.github.sakserv.config.PropertyParser;
import com.github.sakserv.mysql.MySqlGenerator;
import com.github.sakserv.mysql.Table;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

public class MySqlJdbcTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(MySqlGenerator.class);

    private static MySqlGenerator mySqlGenerator;

    // Setup the property parser
    private static PropertyParser propertyParser = new PropertyParser();
    
    @BeforeClass
    public static void setUp() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        propertyParser.configurePropertyParser();
        mySqlGenerator = new MySqlGenerator();
        mySqlGenerator.loadMysqlJdbcDriver();
    }
    
    @Test
    public void testMySqlJdbcDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        MySqlGenerator mySqlGenerator = new MySqlGenerator();
        mySqlGenerator.loadMysqlJdbcDriver();
    }
    
    @Test
    public void testMySqlJdbcConnection() throws SQLException {
        Connection conn = mySqlGenerator.getConnection();
        conn.close();
    }
    
    @Test
    public void testTables() throws SQLException {
        Connection conn = mySqlGenerator.getConnection();
        List<Table> tables = mySqlGenerator.getTableList(conn);
        assertEquals(tables.size(), 1);
        assertThat(tables.get(0).getTableName(), 
                containsString(propertyParser.getProperty(ConfigVars.MYSQL_TABLE_VAR)));
    }
    
    @Test
    public void testGenerateFirstName() throws IOException {
        LOG.info("FIRST NAME: " + mySqlGenerator.generateFirstName());
    }
    
    @Test
    public void testGenerateRows() throws IOException {
        propertyParser.configurePropertyParser();
        Integer rowCount = Integer.parseInt(propertyParser.getProperty(ConfigVars.MYSQL_NUM_ROWS_VAR));
        for(int i = 0; i < rowCount; i++ ) {
            LOG.info("ROW: " + mySqlGenerator.generateRow().toString());
        }
    }
}
