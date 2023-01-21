FROM adoptopenjdk:11 as build
WORKDIR /workspace/app

COPY . .

RUN --mount=type=cache,target=/root/.gradle ./gradlew build -x test
ARG VERSION=0.0.1
RUN jar -xf build/libs/wehere-${VERSION}.jar

FROM openjdk:11-jre-slim-buster as wrap
COPY --from=build /workspace/app/BOOT-INF/lib /app/lib
COPY --from=build /workspace/app/META-INF /app/META-INF
COPY --from=build /workspace/app/BOOT-INF/classes /app
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "/app:/app/lib/*", "-Dspring.profiles.active=local", "api.epilogue.wehere.WehereApplicationKt"]
