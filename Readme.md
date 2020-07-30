## Persistence pittfalls

An example project for my presentation about persistence pittfalls.

### Howto

To run you need:
 1. Java 11+
 2. Gradle 5+
 3. Postgres 11+
 
To setup the database with docker, just run the `db-setup.sh` script under resources catalog. 
This is going to bootstrap the database in docker container (not persistent - no volume configuration).
