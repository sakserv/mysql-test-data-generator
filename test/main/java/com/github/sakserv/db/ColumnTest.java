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

import static org.junit.Assert.assertEquals;

public class ColumnTest {

    private String name = "test_table";
    private String type = "STRING";
    private String qualifiers = "NOT NULL AUTO_INCREMENT";
    private String datagenerator = "com.github.sakserv.datagenerator.StringFileBasedRandomValue";
    private String datafile = "school-subjects.txt";
    private String value = "test";

    private Column column = new Column();

    @Test
    public void testColumnName() {
        column.setName(name);
        assertEquals(name, column.getName());
    }

    @Test
    public void testColumnType() {
        column.setType(type);
        assertEquals(type, column.getType());
    }

    @Test
    public void testColumnQualifiers() {
        column.setQualifiers(qualifiers);
        assertEquals(qualifiers, column.getQualifiers());
    }

    @Test
    public void testColumnDataGenerator() {
        column.setDatagenerator(datagenerator);
        assertEquals(datagenerator, column.getDatagenerator());
    }

    @Test
    public void testColumnDataFile() {
        column.setDatafile(datafile);
        assertEquals(datafile, column.getDatafile());
    }

    @Test
    public void testColumnValue() {
        column.setValue(value);
        assertEquals(value, column.getValue());
    }

    @Test
    public void testToString() {
        String colString = column.getName() + " " + column.getType() + " " +
                column.getQualifiers() + "\n" + column.getDatagenerator() + "\n" +
                column.getDatafile();
        assertEquals(colString, column.toString());
    }

}
