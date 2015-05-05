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

    // Default Table Definition
    public static final String DEFAULT_TABLE_DEFINITION = "tabledef.json";
    
    // JDBC
    public static final String JDBC_HOSTNAME_KEY = "jdbc.hostname";
    public static final String JDBC_PORT_KEY = "jdbc.port";
    public static final String JDBC_TEMP_DIR_KEY = "jdbc.temp.dir";
    public static final String JDBC_USER_KEY= "jdbc.user";
    public static final String JDBC_PASSWORD_KEY = "jdbc.password";
    public static final String JDBC_DATABASE_KEY = "jdbc.database";
    public static final String JDBC_TABLE_KEY = "jdbc.table";
    public static final String JDBC_DRIVER_NAME_KEY = "jdbc.driver.name";
    public static final String JDBC_CONNECTION_STRING_PREFIX_KEY = "jdbc.connection.string.prefix";
    public static final String JDBC_COMPATIBILITY_MODE_KEY = "jdbc.compatibility.mode";
    public static final String JDBC_NUM_ROWS_KEY= "jdbc.num.rows";
    public static final String JDBC_BATCH_SIZE_KEY = "jdbc.batch.size";
    public static final String JDBC_BATCH_COMMIT_DELAY_KEY = "jdbc.batch.commit.delay";
    public static final String JDBC_AUTO_INCREMENT_ID_KEY = "jdbc.auto.increment.id";
    public static final String JDBC_SKIP_DATABASE_CREATE_KEY = "jdbc.skip.database.create";
    public static final String JDBC_SKIP_GRANTS_KEY = "jdbc.skip.grants";

    public String toString() {
        return "ConfigVars";
    }
}
