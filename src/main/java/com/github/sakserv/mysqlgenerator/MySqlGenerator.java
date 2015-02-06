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
package com.github.sakserv.mysqlgenerator;

import com.github.sakserv.config.ConfigVars;
import com.github.sakserv.config.PropertyParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlGenerator {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(MySqlGenerator.class);

    // Setup the property parser
    private static PropertyParser propertyParser;
    static {
        try {
            propertyParser = new PropertyParser(ConfigVars.DEFAULT_PROPS_FILE);
        } catch(IOException e) {
            LOG.error("Unable to load property file: " + propertyParser.getProperty(ConfigVars.DEFAULT_PROPS_FILE));
        }
    }
    
    Connection connection;
    
    public void loadMysqlJdbcDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Load the Hive JDBC driver
        LOG.info("MYSQL: Loading MySQL JDBC Driver");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
    
    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(
                propertyParser.getProperty(ConfigVars.MYSQL_CONNECTION_STRING_PREFIX_VAR) + 
                        propertyParser.getProperty(ConfigVars.MYSQL_HOSTNAME_VAR) + ":" +
                        propertyParser.getProperty(ConfigVars.MYSQL_PORT_VAR),
                propertyParser.getProperty(ConfigVars.MYSQL_USER_VAR),
                propertyParser.getProperty(ConfigVars.MYSQL_PASSWORD_VAR)
        );
        return connection;
    }
    
}
