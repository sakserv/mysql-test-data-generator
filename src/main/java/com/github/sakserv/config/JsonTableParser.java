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
package com.github.sakserv.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Scanner;

public class JsonTableParser {

    private static final Logger LOG = LoggerFactory.getLogger(JsonTableParser.class);

    private String jsonFileName;
    private String jsonFileContents;

    public JsonTableParser() {
        this.jsonFileName = ConfigVars.DEFAULT_TABLE_DEFINITION;
        setJsonFileContentsFromFile();
    }

    public JsonTableParser(String jsonFileName) {
        this.jsonFileName = jsonFileName;
        setJsonFileContentsFromFile();
    }

    public String getJsonFileName() {
        return jsonFileName;
    }

    public void setJsonFileContentsFromFile() {
        JSONParser jsonParser = new JSONParser();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFileName);
        jsonFileContents = convertStreamToString(inputStream);
    }

    public String getJsonFileContents() {
        return jsonFileContents;
    }

    public static JSONObject jsonObjectFromJsonString(String jsonString) throws ParseException {
        JSONObject jsonObject = new JSONObject();

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(jsonString);
        jsonObject = (JSONObject) obj;

        return jsonObject;
    }

    private static String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

}
