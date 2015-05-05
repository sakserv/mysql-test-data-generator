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
package com.github.sakserv.db;

import com.github.sakserv.datagenerator.RandomValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Column {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(Column.class);

    private String name;
    private String type;
    private String qualifiers;
    private String datagenerator;
    private String datafile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(String qualifiers) {
        this.qualifiers = qualifiers;
    }

    public String getDatagenerator() {
        return datagenerator;
    }

    public void setDatagenerator(String datagenerator) {
        this.datagenerator = datagenerator;
    }

    public String getDatafile() {
        return datafile;
    }

    public void setDatafile(String datafile) {
        this.datafile = datafile;
    }

    public String generateValue() throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
    IllegalAccessException, InvocationTargetException {
        Constructor constructor = Class.forName(this.getDatagenerator()).getConstructor(new Class[]{Column.class});
        RandomValue stringFileBasedRandomValue = (RandomValue) constructor.newInstance(this);
        return stringFileBasedRandomValue.getRandomValue().toString();
    }

    public String toString() {
        return getName() + " " + getType() + " " + getQualifiers() + "\n" + getDatagenerator() + "\n" + getDatafile();
    }
}
