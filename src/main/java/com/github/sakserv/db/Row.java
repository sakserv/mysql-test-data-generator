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
import java.util.Iterator;
import java.util.List;

public class Row {

    private List<Column> columns = new ArrayList<Column>();
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addColumnToRow(Column column) {
        columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String generateRowInsertStatement() {
        StringBuffer sbCols = new StringBuffer();
        StringBuffer sbVals = new StringBuffer();

        Iterator it = columns.iterator();

        while (it.hasNext()) {
            Column column = (Column) it.next();
            sbCols.append(column.getName());
            sbVals.append(column.getValue());
            if (it.hasNext()) {
                sbCols.append(", ");
                sbVals.append(", ");
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO " + tableName + " (");
        sb.append(sbCols.toString() + ")");
        sb.append(" VALUES (");
        sb.append(sbVals.toString() + ")");
        return sb.toString();

    }

}
