myApp
==============

Template for a simple Vaadin application that only requires a Servlet 3.0 container to run.


Workflow
========

In the customers_table.sql file there are queries to prepare a necessary sql table for the app to work with.

To compile the entire project, run "mvn install".

To run the application, run "mvn tomcat7:run" and open http://localhost:8888/ .

Paths: http://localhost:8888/ui - for Vaadin ui and http://localhost:8888/rest/customers - for general GET method. Other methods described at the index page.

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean package"
- test the war file with "mvn jetty:run-war"
