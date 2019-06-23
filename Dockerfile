FROM java:8

EXPOSE 4567
WORKDIR /app
COPY ./build/libs/keystore-all.jar /app

CMD java -jar keystore-all.jar

