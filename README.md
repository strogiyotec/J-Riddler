# J-Riddler
J-Riddler is a random data generator for postgres

## Motivation

A lot of developers use Database Migration Tools such as [Flyway](https://flywaydb.org/)

**However** these tools allow you to have the same schema in production and local environments,**not data**.

Let's say you want to test pagination , in order to do this you need 42 rows in table,
you don't care about the quality of data , the only thing you care about is the existence of this data. 



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

The list of columns:

 * **table** - Table name
 * **host** - Postgres host (localhost by default)
 * **port** - Postgres port (5432 by default)
 * **name** - Username
 * **password** - Password
 * **db** - Database name

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
Columns:
Name=id, Value=351544063
Name=name, Value=b449bp9547
Name=active, Value=true
```

## Custom values

If you want to specify custom value for column then use -V(custom value) attribute

Example:
`
java -jar target/jriddler.jar -table test -host localhost -port 5432 -name postgres -password 123 -db test -Vemail=test@gmail.com
`

in this case **J-Riddler** will use **test@gmail.com** as a value for **email** and random values for other columns


