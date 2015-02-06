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
    public static final String DEFAULT_PROPS_FILE = "env.properties";
    
    // MySQL
    public static final String MYSQL_HOSTNAME_VAR = "mysql.hostname";
    public static final String MYSQL_PORT_VAR = "mysql.port";
    public static final String MYSQL_USER_VAR = "mysql.user";
    public static final String MYSQL_PASSWORD_VAR = "mysql.password";
    public static final String MYSQL_DATABASE_VAR = "mysql.database";
    public static final String MYSQL_TABLE_VAR = "mysql.table";
    public static final String MYSQL_CONNECTION_STRING_PREFIX_VAR = "mysql.connection.string.prefix";
}
