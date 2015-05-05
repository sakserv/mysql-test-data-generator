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
package com.github.sakserv.datagenerator;

import com.github.sakserv.db.Column;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringFileBasedRandomValue implements RandomValue<String> {
    
    File file;
    Random random;
    Column column;
    List<String> fileContents;
    
    public  StringFileBasedRandomValue(Column column) throws IOException {
        random = new Random();
        this.column = column;
        file = new File(column.getDatafile());
        setFileContents();
    }

    public File getFile() {
        return file;
    }
    
    public void setFileContents() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file.getName());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        fileContents = new ArrayList<>();
        String line = null;
        while((line = reader.readLine()) != null) {
            fileContents.add(line);
        }
    }
    
    public String getRandomValue() {
        return "'" + fileContents.get(random.nextInt(fileContents.size())) + "'";
    }
    
    
}
