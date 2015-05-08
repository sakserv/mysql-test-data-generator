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

import java.util.Random;

public class IntegerRandomValue implements RandomValue<Integer> {
    Column column;
    Integer upperBound = 100;
    Integer lowerBound = 0;

    public IntegerRandomValue(Column column) {
        this.column = column;
    }

    public Integer getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Integer upperBound) {
        this.upperBound = upperBound;
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Integer lowerBound) {
        this.lowerBound = lowerBound;
    }

    @Override
    public Integer getRandomValue() {
        Random rand = new Random();
        return rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;
    }
}
