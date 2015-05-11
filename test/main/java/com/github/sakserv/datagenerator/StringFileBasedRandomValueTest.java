package com.github.sakserv.datagenerator;

import com.github.sakserv.db.Column;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

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

public class StringFileBasedRandomValueTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(StringFileBasedRandomValueTest.class);

    private static Column column;
    private static final String COL_NAME = "id";
    private static final String COL_TYPE = "VARCHAR(255)";
    private static final String COL_DATA_FILE = "first-names.txt";
    private static final String COL_DATA_GENERATOR = "com.github.sakserv.datagenerator.StringFileBasedRandomValue";
    private static StringFileBasedRandomValue stringFileBasedRandomValue;

    @BeforeClass
    public static void setUp() throws IOException {
        column = new Column();
        column.setName(COL_NAME);
        column.setType(COL_TYPE);
        column.setDatafile(COL_DATA_FILE);
        column.setDatagenerator(COL_DATA_GENERATOR);

        stringFileBasedRandomValue = new StringFileBasedRandomValue(column);
    }

    @Test
    public void testGetFile() throws Exception {
        assertEquals(COL_DATA_FILE, stringFileBasedRandomValue.getFile().toString());
    }

    @Test
    public void testSetFileContents() throws Exception {
        stringFileBasedRandomValue.setFileContents();
        assertTrue(stringFileBasedRandomValue.getFileContents() instanceof List);
    }

    @Test
    public void testGetRandomValue() throws Exception {
        assertTrue(stringFileBasedRandomValue.getRandomValue() instanceof String);
    }
}