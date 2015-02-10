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
package com.github.sakserv.jdbc;

public class Row {
    
    private Integer id;
    private String firstName;
    private String lastName;
    private String subject;
    private Integer score;
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Id
        if(getId() != null) {
            sb.append(getId());
            sb.append(",");
        }
        
        // First name
        sb.append(getFirstName());
        sb.append(",");

        // Last name
        sb.append(getLastName());
        sb.append(",");

        // Subject
        sb.append(getSubject());
        sb.append(",");

        // Score
        sb.append(getScore());
        sb.append(",");

        // Date
        sb.append(getDate());

        return sb.toString();
    }
}
