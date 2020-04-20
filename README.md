# J-Riddler
J-Riddler is a random data generator for postgres

## Motivation

Despite of database migration tools sometimes you need to have an actual data inside database tables

**J-Riddler** gives you a plain and simple CLI to fill these tables instead of writing huge **INSERT** queries by your own.
The final jar size is 1MB(can be improved using [Graal native images](https://www.graalvm.org/))


## Contributions

Contributions are welcome! Please, open an issue before submit any kind (ideas,
documentation, code, ...) of contribution.

Use this command to check your build

```
mvn checkstyle:check test
```

# Supported types
1. Text
2. Varchar
3. Timestamps
4. Numbers (int,double precision)
5. Boolean 

# Features to be implemented
1. If table has FK constraint then insert row in FK table
2. Check value constraints (bigger than 0, min length etc.)

# Build and run

1. Build project `mvn clean package`
2. Run jar file with params `java -jar target/jriddler.jar -table test -host localhost -port 5432 -name postgres -password 123 -db test`

Example output:

```
Apr 20, 2020 3:19:37 PM com.jriddler.sql.LoggableInsertQuery listen
INFO: 
Execution time 7ms
Insert query:
INSERT INTO test
(id,name,active)
VALUES 
(?,?,?)
Attributes:
Name=id, Value=351544063
Name=name, Value=b449bp9547
Name=active, Value=true
```
If you want to specify custom value for attribute then use -UA(user attribute) attribute

Example:
`
java -jar target/jriddler.jar -table test -host localhost -port 5432 -name postgres -password 123 -db test -DAemail=test@gmail.com
`

in this case **J-Riddler** will use **test@gmail.com** as a value for **email** and random values for other attributes


