FROM openjdk:11
VOLUME /tmp
ADD target/*.jar tweetapp.jar
ENTRYPOINT ["java","-jar", "/tweetapp.jar"]
