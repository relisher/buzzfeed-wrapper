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
