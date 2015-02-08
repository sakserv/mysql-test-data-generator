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
package com.github.sakserv.mysql;

import com.github.sakserv.config.ConfigVars;
import com.github.sakserv.config.PropertyParser;
import com.github.sakserv.datagenerator.StringDateRandomValue;
import com.github.sakserv.datagenerator.StringFileBasedRandomValue;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlGenerator {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(MySqlGenerator.class);
    
    // Instance variables
    private Integer rowId = 0;
    private String firstNameFile = "first-names.txt";
    private String lastNameFile = "last-names.txt";
    private String schoolSubjectFile = "school-subjects.txt";

    // Setup the property parser
    private static PropertyParser propertyParser = new PropertyParser();
    
    public void loadMysqlJdbcDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Load the Hive JDBC driver
        LOG.info("MYSQL: Loading MySQL JDBC Driver");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
    
    public Connection getConnection() throws SQLException {
        propertyParser.configurePropertyParser();
        Connection connection = DriverManager.getConnection(
                propertyParser.getProperty(ConfigVars.MYSQL_CONNECTION_STRING_PREFIX_VAR) + 
                        propertyParser.getProperty(ConfigVars.MYSQL_HOSTNAME_VAR) + ":" +
                        propertyParser.getProperty(ConfigVars.MYSQL_PORT_VAR) + "/" +
                        propertyParser.getProperty(ConfigVars.MYSQL_DATABASE_VAR),
                propertyParser.getProperty(ConfigVars.MYSQL_USER_VAR),
                propertyParser.getProperty(ConfigVars.MYSQL_PASSWORD_VAR)
        );
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
    
    public Row generateRow() throws IOException {
        propertyParser.configurePropertyParser();
        Row row = new Row();
        if(!Boolean.parseBoolean(propertyParser.getProperty(ConfigVars.MYSQL_AUTO_INCREMENT_ID_VAR))) {
            row.setId(generateId());
        }
        row.setFirstName(generateFirstName());
        row.setLastName(generateLastName());
        row.setSubject(generateSchoolSubject());
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
    
    public String generateFirstName() throws IOException {
        return getRandomValueFromFile(firstNameFile);
    }
    
    public String generateLastName() throws IOException {
        return getRandomValueFromFile(lastNameFile);
    }
    
    public String generateSchoolSubject() throws IOException {
        return getRandomValueFromFile(schoolSubjectFile);
    }
    
    public String generateDate() {
        StringDateRandomValue stringDateRandomValue = new StringDateRandomValue();
        return stringDateRandomValue.getRandomValue();
    }
    
    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("c", true, "configuration file");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);
        
        if(cmd.hasOption("c")) {
            propertyParser.setPropFileName(cmd.getOptionValue("c"));
        }
        
    }
    
}
