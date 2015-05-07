package com.github.sakserv.datagenerator;

import com.github.sakserv.db.Column;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class StringDateRandomValueTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(StringDateRandomValueTest.class);

    private static Column column;
    private static final String COL_NAME = "dt";
    private static final String COL_TYPE = "DATE";
    private static final String COL_DATA_GENERATOR = "com.github.sakserv.datagenerator.StringDateRandomValue";
    private static StringDateRandomValue stringDateRandomValue;

    @BeforeClass
    public static void setUp() throws IOException {
        column = new Column();
        column.setName(COL_NAME);
        column.setType(COL_TYPE);
        column.setDatagenerator(COL_DATA_GENERATOR);

        stringDateRandomValue = new StringDateRandomValue(column);
    }

    @Test
    public void testGetStartYear() throws Exception {
        stringDateRandomValue.setStartYear(2014);
        assertEquals(2014, stringDateRandomValue.getStartYear());
    }

    @Test
    public void testGetEndYear() throws Exception {
        stringDateRandomValue.setEndYear(2015);
        assertEquals(2015, stringDateRandomValue.getEndYear());
    }

    @Test
    public void testGetRandomValue() throws Exception {
        String randomValue = stringDateRandomValue.getRandomValue().toString();
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(randomValue);
        assertTrue(matcher.find());
    }
}