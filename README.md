# buzzfeed-wrapper
Challenge question for application

## How to run

This is a Java Maven project. In order to run this, you will need Java 8 and Maven installed on your computer.
Once you have Maven and Java, do the following:
Navigate to the root directory, with the pom.xml. Run the following from command line.

     mvn install
  
This will install all the prerequiste packages to running this project. Afterwards run the following:

     mvn compile
  
This compiles the program and runs all the tests. Finally run the following:

     mvn exec:java -Dexec.mainClass="com.mycompany.buzzfeed.rest.wrapper.Application"
  
This runs the Application class as the main class, and should set up a server at localhost:8080. You can test if the server is working
by using your browser to go to localhost:8080/api/test/

## Program Specifics

There are four endpoints I have made beyond the test endopint. The following sections show how to POST to each endpoint.

### /api/timefeed

This endpoint takes a feed, a startTime and an endTime as URL Paramters (the times are formatted in the following format: yyyy-MM-dd-hh:mm:ss). An example of the URL you would be posting to is the following: /api/threshold?feed=lol&startTime=2016-12-01-01:00:00&endTime=2017-01-11-01:00:00 This should give you all buzzes posted in a feed from startTime to endTime

### /api/threshold

This endpoint additionally takes a treshold of comments that each buzz should have as a URL paramter. An example URL would be /api/threshold?feed=lol&startTime=2016-12-01-01:00:00&endTime=2017-01-11-01:00:00&threshold=29

### /api/keywords

This additionally to the timefeed endpoint takes a list of keywords as a part of the body. A sample body looks as follows:
     
     {"keywords": ["rowling","j"]}
 
It is very important that the keywords are formated as such. I've built a POJO to consume the JSON body and it expects this format.

### /error

You should only see this endpoint if you run into trouble

## Technology

This is built using Java Spring Boot in order to easily create a REST API in Java. I've used this framework at my last two positions, and while it isn't the best for small hackathon projects, I decided to take my time with this assignment and build a nice robust program, which includes custom objects, and a POJO for JSON consumption, all hallmarks of Java API design. This also uses OKHTTP for easy interaction with the Buzzfeed API, and a bit of Java 8 syntactic sugar.

## Testing 

TODO - add JUnit tests (hand tested up until this point)
 
