FROM java:8
LABEL maintainer="Piotr Tarasinski"
COPY Lab4.java .
RUN curl -L -o /mysql-connector-java-8.0.13.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.13/mysql-connector-java-8.0.13.jar
RUN javac Lab4.java
CMD java Lab4 
CMD ["java", "-classpath", "mysql-connector-java-8.0.13.jar:.","Lab4"]
