package com.github.sakserv.datagenerator;

import com.github.sakserv.db.Column;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
public class IntegerRandomValueTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(IntegerRandomValueTest.class);

    private static Column column;
    private static final String COL_NAME = "id";
    private static final String COL_TYPE = "INTEGER";
    private static final String COL_DATA_GENERATOR = "com.github.sakserv.datagenerator.IntegerRandomValue";
    private static IntegerRandomValue integerRandomValue;

    @BeforeClass
    public static void setUp() throws IOException {
        column = new Column();
        column.setName(COL_NAME);
        column.setType(COL_TYPE);
        column.setDatagenerator(COL_DATA_GENERATOR);

        integerRandomValue = new IntegerRandomValue(column);
    }

    @Test
    public void testGetUpperBound() throws Exception {
        integerRandomValue.setUpperBound(100);
        assertEquals(100, (int) integerRandomValue.getUpperBound());
    }

    @Test
    public void testSetUpperBound() throws Exception {
        integerRandomValue.setLowerBound(100);
        assertEquals(100, (int) integerRandomValue.getLowerBound());
    }

    @Test
    public void testGetRandomValue() throws Exception {
        integerRandomValue.setLowerBound(10);
        integerRandomValue.setUpperBound(20);
        Integer randomValue = integerRandomValue.getRandomValue();
        assertTrue(10 <= randomValue && randomValue <= 20);

    }
}