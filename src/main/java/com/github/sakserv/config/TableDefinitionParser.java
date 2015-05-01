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

import com.github.sakserv.jdbc.Column;
import com.github.sakserv.jdbc.Table;
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
    private String jsonFileContents;

    public String getJsonFileName() {
        return jsonFileName;
    }

    public void setJsonFileName(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    public String getJsonFileContents() {
        return jsonFileContents;
    }


    public String jsonFileContentsToString() {
        if (jsonFileName == null) {
            LOG.error("ERROR: Must set the json file name");
            throw new IllegalArgumentException("ERROR: Must set the json file name");
        }

        JSONParser jsonParser = new JSONParser();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFileName);
        String fileContents = convertStreamToString(inputStream);
        return fileContents;
    }

    public Table createTableFromJsonString() {

        String fileContents = jsonFileContentsToString();
        Table table = new Table();

        try {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(fileContents);
            JSONObject jsonObject = (JSONObject) obj;

            table.setTableName(jsonObject.get("table_name").toString());

            // Get the columns
            Map<String,Map<String,String>> colNames = (Map<String,Map<String,String>>) jsonObject.get("columns");
            Iterator it = colNames.entrySet().iterator();

            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String colName = (String) pair.getKey();

                Column column = new Column();
                column.setName(colName);

                // Get the column properties
                Map<String, String> values = ((Map<String, Map<String, String>>) jsonObject.get("columns")).get(colName);

                // Handle type
                String type = (String) values.get("type");
                if (type != "") {
                    column.setType(type);
                }

                // Handle qualifiers
                String qualifiers = (String) values.get("qualifiers");
                if (qualifiers != "") {
                    column.setQualifiers(qualifiers);
                }

                // Handle datagenerator
                String datagenerator = (String) values.get("data_generator");
                if (datagenerator != "") {
                    column.setDatagenerator(datagenerator);
                }

                // Handle datafile
                String datafile = (String) values.get("data_file");
                if (datafile != "") {
                    column.setDatafile(datafile);
                }

                table.addColToTable(column);
            }


            // Get the table attributes
            Map<String, String> attributes = (Map<String, String>) jsonObject.get("attributes");
            table.setPrimaryKey(attributes.get("primary_key"));
            LOG.info("PRIMARY KEY: " + table.getPrimaryKey());

        } catch(ParseException e) {
            LOG.error("ERROR: Could not parse JSON file " + jsonFileName);
            e.printStackTrace();
        }
        return table;
    }

    static String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

}
