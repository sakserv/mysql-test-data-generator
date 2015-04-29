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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class TableDefinitionParser {

    private static final Logger LOG = LoggerFactory.getLogger(TableDefinitionParser.class);

    private String jsonFileName;

    public String getJsonFileName() {
        return jsonFileName;
    }

    public void setJsonFileName(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    public String getCreateTableStatement() {
        if (jsonFileName == null) {
            LOG.error("ERROR: Must set the json file name");
            throw new IllegalArgumentException("ERROR: Must set the json file name");
        }

        JSONParser jsonParser = new JSONParser();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFileName);
        String fileContents = convertStremToString(inputStream);

        try {
            Object obj = jsonParser.parse(fileContents);
            JSONObject jsonObject = (JSONObject) obj;

            // Build up the SQL for the columns
            Map<String,Map<String,String>> colNames = (Map<String,Map<String,String>>) jsonObject.get("columns");
            Iterator it = colNames.entrySet().iterator();
            StringBuilder sb = new StringBuilder();
            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String colName = (String) pair.getKey();
                Map<String, String> values = ((Map<String, Map<String,String>>) jsonObject.get("columns")).get(colName);
                String type = (String) values.get("type");
                String qualifiers = values.get("qualifiers");
                sb.append(colName + " " + type + " " + qualifiers + ",\n");
            }
            LOG.info(sb.toString());

        } catch(ParseException e) {
            LOG.error("ERROR: Could not parse JSON file " + jsonFileName);
            e.printStackTrace();
        }
        return "";
    }

    static String convertStremToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

}
