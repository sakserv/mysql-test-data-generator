package com.github.sakserv.db;
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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class DataGeneratorFromReflectionTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(DataGeneratorFromReflectionTest.class);

    @Test
    public void testDataGeneratorFromClassName() throws ClassNotFoundException, IllegalAccessException,
    InstantiationException, NoSuchMethodException, InvocationTargetException {
        Column column = new Column();
        column.setName("firstname");
        column.setType("VARCHAR(255)");
        column.setDatagenerator("com.github.sakserv.datagenerator.StringFileBasedRandomValue");
        column.setDatafile("first-names.txt");
        column.generateValue();
        Pattern pattern = Pattern.compile("[A-Z]+");
        Matcher matcher = pattern.matcher(column.getValue());
        assertTrue(matcher.find());
    }

}
