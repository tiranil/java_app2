myApp
==============

Template for a simple Vaadin application that only requires a Servlet 3.0 container to run.


Workflow
========

To compile the entire project, run "mvn install".

To run the application, run "mvn tomcat7:run" and open http://localhost:8888/ .

Paths: http://localhost:8888/ui - for Vaadin ui and http://localhost:8888/init/customers - for REST template.

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean package"
- test the war file with "mvn jetty:run-war"
