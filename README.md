MySQL Test Data Generator
-------------------------

Provides scripts for setting up mysql-sandbox, defining databases and tables, and loading data for testing

Usage
-----

The following outlines how to use this project.

* Clone the repo:
```
cd /tmp && git clone https://github.com/sakserv/mysql-test-data-generator.git
```

* Start the mysql-sandbox test mysql instance
```
cd /tmp/mysql-test-data-generator && bash -x bin/mysql_sandbox.sh
```

* Install Java 7 (if not already installed)
```
cd /tmp/mysql-test-data-generator && bash -x bin/install_java.sh
```

* Build the project
```
cd /tmp/mysql-test-data-generator && bash -x bin/build.sh
```

* Copy and populate the template
```
cd /tmp/mysql-test-data-generator && cp ./src/main/resources/sandbox.properties /tmp/
vi /tmp/foo.properties
```

* Run the Main class to populate the database
```
cd /tmp/mysql-test-data-generator/target/
java -jar ./mysql-test-data-generator-0.0.1-SNAPSHOT.jar -c /tmp/foo.properties
```

Test Data Sources
---------------------
First Names - Source: deron.meranda.us/data/

Last Names - Source: deron.meranda.us/data/

School Subjects - Source: simple.wikipedia.org/wiki/Subject_(school)
