package com.github.sakserv.db;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

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

public class TableTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(TableTest.class);

    private static Table table;
    private static final String databaseName = "default";
    private static final String tableName = "testtable";
    private static final String primaryKey = "id";

    @Before
    public void setUp() throws Exception {
        table = new Table();
    }

    @Test
    public void testGetDatabaseName() throws Exception {
        table.setDatabaseName(databaseName);
        assertEquals(databaseName, table.getDatabaseName());
    }


    @Test
    public void testGetTableName() throws Exception {
        table.setTableName(tableName);
        assertEquals(tableName, table.getTableName());
    }

    @Test
    public void testGetRows() throws Exception {
        Row row = new Row();
        table.addRowToTable(row);
        assertTrue(table.getRows().get(0) instanceof Row);
    }

    @Test
    public void testGetColumns() throws Exception {
        Column column = new Column();
        table.addColToTable(column);
        assertTrue(table.getColumns().get(0) instanceof Column);
    }

    @Test
    public void testGetPrimaryKey() throws Exception {
        table.setPrimaryKey(primaryKey);
        assertEquals(primaryKey, table.getPrimaryKey());
    }

    @Test
    public void testGenerateCreateTableStatement() throws Exception {
        LOG.info(table.generateCreateTableStatement());

    }
}