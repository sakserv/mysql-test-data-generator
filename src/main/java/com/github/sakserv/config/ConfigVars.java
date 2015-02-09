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
package com.github.sakserv.config;

public class ConfigVars {
    
    // Default properties file
    public static final String DEFAULT_PROPS_FILE = "local.properties";
    
    // Data files
    public static final String DATA_FIRST_NAMES_FILE = "first-names.txt";
    public static final String DATA_LAST_NAMES_FILE = "last-names.txt";
    public static final String DATA_SCHOOL_SUBJECTS_FILE = "school-subjects.txt";
    
    // JDBC
    public static final String JDBC_HOSTNAME_VAR = "jdbc.hostname";
    public static final String JDBC_PORT_VAR = "jdbc.port";
    public static final String JDBC_TEMP_DIR_VAR = "jdbc.temp.dir";
    public static final String JDBC_USER_VAR = "jdbc.user";
    public static final String JDBC_PASSWORD_VAR = "jdbc.password";
    public static final String JDBC_DATABASE_VAR = "jdbc.database";
    public static final String JDBC_TABLE_VAR = "jdbc.table";
    public static final String JDBC_DRIVER_NAME_VAR = "jdbc.driver.name";
    public static final String JDBC_CONNECTION_STRING_PREFIX_VAR = "jdbc.connection.string.prefix";
    public static final String JDBC_COMPATIBILITY_MODE_KEY = "jdbc.compatibility.mode";
    public static final String JDBC_NUM_ROWS_VAR = "jdbc.num.rows";
    public static final String JDBC_AUTO_INCREMENT_ID_VAR = "jdbc.auto.increment.id";
}
