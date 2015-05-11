package com.github.sakserv.config;/*
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

import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class JsonTableParserTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(JsonTableParserTest.class);

    private String jsonFileName = "tabledef.json";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testMalformedJson() throws ParseException {
        String badJson = "{\"name\": }";

        exception.expect(ParseException.class);
        JsonTableParser.jsonObjectFromJsonString(badJson);
    }

    @Test
    public void testJsonFileName() {
        JsonTableParser jsonTableParser = new JsonTableParser(jsonFileName);
        assertEquals(jsonFileName, jsonTableParser.getJsonFileName());
    }

    @Test
    public void testJsonTableParserConstructor() {
        JsonTableParser jsonTableParser = new JsonTableParser();
    }
}
