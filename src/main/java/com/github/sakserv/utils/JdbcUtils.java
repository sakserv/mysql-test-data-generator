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
import com.github.sakserv.db.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtils {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);

    public void loadJdbcDriver(String jdbcDriverClass) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        LOG.info("JDBC: Loading JDBC Driver: " + jdbcDriverClass);
        Class.forName(jdbcDriverClass).newInstance();
    }

    public static Connection getConnection(String connectionString, String userName, String password) throws SQLException {
        return DriverManager.getConnection(connectionString, userName, password);
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

    public static String getConnectionString(String connectionStringPrefix, String hostname, String port) {
        return connectionStringPrefix + hostname + ":" + port;
    }

    public static String getConnectionString(String connectionStringPrefix, String hostname, String port, String database) {
        return connectionStringPrefix + hostname + ":" + port + "/" + database;
    }

    public static void createGrants(Connection connection, String connectionString, String userName, String passWord) {

        // Create the grants
        try {
            LOG.info("Running grants for user " + userName +
                    " on " + connectionString);
            Statement statement = connection.createStatement();
            String sqlGrantPw = "GRANT ALL PRIVILEGES ON *.* TO " +
                    "\"" + userName + "\"@" +
                    "\"%\" IDENTIFIED BY \"" + passWord + "\"";
            displayQueryDebug(sqlGrantPw);
            statement.executeQuery(sqlGrantPw);

            /*statement = connection.createStatement();
            String sqlGrantSandbox = "GRANT ALL ON *.* to root@mysql.sandbox WITH GRANT OPTION";
            displayQueryDebug(sqlGrantSandbox);
            statement.executeQuery(sqlGrantSandbox);*/

            statement = connection.createStatement();
            String sqlFlushPriv = "FLUSH PRIVILEGES";
            displayQueryDebug(sqlFlushPriv);
            statement.executeQuery(sqlFlushPriv);

        } catch (SQLException e) {
            LOG.error("ERROR: Error applying grants for user: " + userName);
            e.printStackTrace();
        }

    }

    public static void displayQueryDebug(String sql) {
        LOG.info("DEBUG: Running (or batching) the following statement: " + sql);
    }

}
