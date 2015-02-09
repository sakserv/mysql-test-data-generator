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

* Setup mysql-sandbox and create the test database/table
```
cd /tmp/mysql-test-data-generator && bash -x bin/mysql_sandbox.sh
```

* Install java 1.7 and maven 3.2 (optional if java 1.7 and maven 3.2 are installed)
```
cd /tmp/mysql-test-data-generator && bash -x bin/install_maven_java.sh
```

* Build the project
```
cd /tmp/mysql-test-data-generator && bash -x bin/run_mvn_build.sh
```

Test Data Attribution
---------------------
First Names - Source: deron.meranda.us/data/

Last Names - Source: deron.meranda.us/data/

School Subjects - Source: simple.wikipedia.org/wiki/Subject_(school)
