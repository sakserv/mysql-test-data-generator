package com.github.sakserv.db;/*
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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class RowTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(RowTest.class);

    private String tableName = "test_table";

    private String name1 = "id";
    private String type1 = "VARCHAR(255)";
    private String qualifiers1 = "NOT NULL AUTO_INCREMENT";
    private String datagenerator1 = "com.github.sakserv.datagenerator.StringFileBasedRandomValue";
    private String datafile1 = "school-subjects.txt";
    private String value1 = "1";

    private String name2 = "firstname";
    private String type2 = "VARCHAR(255)";
    private String qualifiers2 = "NOT NULL AUTO_INCREMENT";
    private String datagenerator2 = "com.github.sakserv.datagenerator.StringFileBasedRandomValue";
    private String datafile2 = "school-subjects.txt";
    private String value2 = "shane";

    @Test
    public void testAddColumn() {
        Row row = new Row();
        Column column = new Column();
        row.addColumnToRow(column);
        assertEquals(1, row.getColumns().size());
    }

    @Test
    public void testTableName() {
        Row row = new Row();
        row.setTableName(tableName);
        assertEquals(tableName, row.getTableName());
    }

    @Test
    public void testGenerateRowInsertStatement() {
        Column column1 = new Column();
        column1.setName(name1);
        column1.setType(type1);
        column1.setQualifiers(qualifiers1);
        column1.setDatagenerator(datagenerator1);
        column1.setDatafile(datafile1);
        column1.setValue(value1);

        Column column2 = new Column();
        column2.setName(name2);
        column2.setType(type2);
        column2.setQualifiers(qualifiers2);
        column2.setDatagenerator(datagenerator2);
        column2.setDatafile(datafile2);
        column2.setValue(value2);

        Row row = new Row();
        row.setTableName(tableName);
        row.addColumnToRow(column1);
        row.addColumnToRow(column2);
        LOG.info(row.generateRowInsertStatement());
    }

}
