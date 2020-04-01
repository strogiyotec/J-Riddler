# J-Riddler
J-Riddler is a random data generator for postgres

## Motivation

Despite of database migration tools sometimes you need to have an actual data inside database tables

**J-Riddler** gives you a plain and simple CLI to fill these tables instead of writing huge **INSERT** queries by your own



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

# Example output 

```
INFO: New row for table [test] was created Params
Attribute: id Value 9
Attribute: active Value true
Attribute: long_id Value 9
Attribute: created Value 2020-03-10T15:27:53.348963-07:00
Attribute: surname Value k7mir7lo97
Attribute: age Value 9
Attribute: sum Value 0
Attribute: age2 Value 0

```