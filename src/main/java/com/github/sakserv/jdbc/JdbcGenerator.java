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
package com.github.sakserv.jdbc;

import com.github.sakserv.datagenerator.IntegerRandomValue;
import com.github.sakserv.datagenerator.StringDateRandomValue;
import com.github.sakserv.datagenerator.StringFileBasedRandomValue;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcGenerator {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(JdbcGenerator.class);
    
    // Instance variables
    private Integer rowId = 0;
    
    public void loadJdbcDriver(String jdbcDriverClass) throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException {
        LOG.info("JDBC: Loading JDBC Driver: " + jdbcDriverClass);
        Class.forName(jdbcDriverClass).newInstance();
    }

    public Connection getConnectionNoDb(String connectionStringPrefix, String hostName,
                                    String port, String userName,
                                    String password) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionStringPrefix + hostName + ":"
                + port + "/", userName, password);
        return connection;
    }
    
    public Connection getConnection(String connectionStringPrefix, String hostName, 
                                    String port, String databaseName, String userName,
                                    String password) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionStringPrefix + hostName + ":" 
                        + port + "/" + databaseName, userName, password);
        return connection;
    }
    
    public List<Table> getTableList(Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        List<Table> tableList = new ArrayList<Table>();
        String[] types = {"TABLE"};
        ResultSet rs = md.getTables(null, null, "%", types);
        while(rs.next()) {
            Table table = new Table();
            table.setTableName(rs.getString("TABLE_NAME"));
            tableList.add(table);
        }
        return tableList;
        
    }
    
    public Row generateRow(Boolean priKeyAutoIncrements, String firstNameFile,
                           String lastNameFile, String schoolSubjectFile) throws IOException {
        Row row = new Row();
        if(!priKeyAutoIncrements) {
            row.setId(generateId());
        }
        row.setFirstName(generateFirstName(firstNameFile));
        row.setLastName(generateLastName(lastNameFile));
        row.setSubject(generateSchoolSubject(schoolSubjectFile));
        row.setScore(generateScore());
        row.setDate(generateDate());
        return row;
    }
    
    public Integer generateId() {
        if (rowId.equals(0)) {
            rowId++;
            return 0;
        }
        return rowId++;
    }
    
    public String getRandomValueFromFile(String fileName) throws IOException {
        StringFileBasedRandomValue stringFileBasedRandomValue = new StringFileBasedRandomValue();
        stringFileBasedRandomValue.setFileFromString(fileName);
        stringFileBasedRandomValue.setFileContents(stringFileBasedRandomValue.getFile());
        return WordUtils.capitalizeFully(stringFileBasedRandomValue.getRandomValue());
    } 
    
    public String generateFirstName(String fileName) throws IOException {
        return getRandomValueFromFile(fileName);
    }
    
    public String generateLastName(String fileName) throws IOException {
        return getRandomValueFromFile(fileName);
    }
    
    public String generateSchoolSubject(String fileName) throws IOException {
        return getRandomValueFromFile(fileName);
    }
    
    public String generateDate() {
        StringDateRandomValue stringDateRandomValue = new StringDateRandomValue();
        return stringDateRandomValue.getRandomValue();
    }
    
    public Integer generateScore() {
        IntegerRandomValue integerRandomValue = new IntegerRandomValue();
        return integerRandomValue.getRandomValue();
        
    }
    
}
