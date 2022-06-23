FROM openjdk:11
VOLUME /tmp
EXPOSE 8105
ADD ./target/ms-management-saving-account-0.0.1-SNAPSHOT.jar ms-management-saving-account.jar
ENTRYPOINT ["java","-jar","ms-management-saving-account.jar"]