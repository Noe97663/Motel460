compile: FrontEnd.java BackEnd.java DatabaseInit.java DatabasePopulate.java 
	javac -cp .:/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar *.java