# GEOCOMPLY DEMO
This project is the automation test project using for GeoComply Demo

#### Project installation

Once you have configured your project in your IDE you can build it from there. However if you prefer you can use maven from the command line. In that case you could be interested in this short list of commands:

```
mvn clean install -U -Dmaven.test.skip=true
```

If you need more information please take a look at this [quick tutorial](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

#### Test executor
The default test tags is "@ui"
 - Tag can be edit at the class [ParallelRunnerTest](src/test/java/ParallelRunnerTest.java)

Test can be run in your IDE with JUnit test executor or can be run with maven command 

```
$ mvn clean test -Denvironment=dev -Dbrowser=chrome -Dcucumber.filter.tags="@uploadNoTerm" 
```

- If you are running the test on Windows, please make sure all the paths are updated correctly with your os
    - Example:
        -   MacOS: src/test/resources/data/config/generateData.json
        -   Windows: src\\test\\resources\\data\\config\\generateData.json
        
#### Test report generator
After executing all tests with maven command above, test reports are generated with the folder "report/dd-MMM-YY"
* **Example:**
"reports 01-02-38"

            -  reports
                - 25-Jun-23
                    - test-output
                        - screenshots
                        - SparkReport

