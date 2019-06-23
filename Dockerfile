FROM java:8

WORKDIR /app
COPY ./build/libs/keystore-all.jar /app

CMD java -jar keystore-all.jar