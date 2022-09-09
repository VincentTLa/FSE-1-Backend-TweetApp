FROM openjdk:11
COPY target/tweetapp.jar tweetapp.jar
ENTRYPOINT ["java","-jar", "/tweetapp.jar"]
