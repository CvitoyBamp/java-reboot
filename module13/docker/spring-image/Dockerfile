FROM registry.access.redhat.com/ubi9/openjdk-17@sha256:6c4c45b62bdd43ab8c44bc6398c85dea1b489b20019678fe354ebc5d644b431f

LABEL authors="cvitoybamp"

WORKDIR app

COPY module13-1.0.0.jar module13-1.0.0.jar

ENTRYPOINT ["java", "-jar", "module13-1.0.0.jar"]