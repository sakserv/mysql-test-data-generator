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
package com.github.sakserv.db;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String databaseName;
    private String tableName;
    List<Row> rows = new ArrayList<Row>();
    List<Column> columns = new ArrayList<Column>();
    private String primaryKey;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void addRowToTable(Row row) {
        rows.add(row);
    }

    public void addColToTable(Column column) {
        columns.add(column);
    }

    public String generateCreateTableStatement() {
        StringBuilder sb = new StringBuilder();

        // Create table statement
        sb.append("CREATE TABLE " + tableName + "\n(");

        // Add columns to create table
        for(Column column: columns) {
            sb.append(column.getName() + " " + column.getType());
            if(column.getQualifiers() != null) {
                sb.append(" " + column.getQualifiers());
            }
            sb.append(",\n");
        }

        // Add primary key
        sb.append("PRIMARY KEY (" + primaryKey + "))");

        return sb.toString();
    }
}
