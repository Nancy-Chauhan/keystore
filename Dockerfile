FROM java:8 as builder

WORKDIR /app
COPY . /app

RUN ./gradlew build
RUN ls -lha ./build/libs

FROM java:8

EXPOSE 4567
WORKDIR /app
COPY --from=builder /app/build/libs/app-all.jar /app

CMD java -jar app-all.jar
