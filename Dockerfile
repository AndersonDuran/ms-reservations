FROM openjdk:11-jdk
EXPOSE 8080:8080
RUN mkdir /app

COPY ./build/install/ms-reservations /app/
COPY ./wait-for.sh /app/bin/wait-for.sh

WORKDIR /app/bin
RUN chmod +x ./wait-for.sh
CMD ["./ms-reservations"]