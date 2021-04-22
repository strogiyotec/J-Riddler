# J-Riddler
J-Riddler is a random row generator for postgres

## Motivation

A lot of developers use Database Migration Tools such as [Flyway](https://flywaydb.org/) in order to have the same database schema in each environment. But what if I
need an actual data inside tables ? 

## Problem
Let's say you have a **users** table with the following columns : `id,name,surname,active,birthday` and you want to test that your search engine is able
to find a user with name `Bob`. In order to do this you need a user with this name in table. Plain old solution is to use insert query
`
insert into users(id,name,surname,active,birthday) values (1,'Bob','Anderson',true,now())
` 
If you are tired from writing these huge queries then **J-Riddle** is here to help you.

## Definition

**J-Riddler** gives you a plain and simple CLI to fill these tables instead of writing huge **INSERT** queries by your own. **J-Riddler** is written in Java so you have to have a JVM installed in order to run it. GraalVM support is expected in order to distibute J-Riddler as a single binary executable.

## Solution

The previous insert query could be replaced using **J-Riddler** command

`
 jriddler -table users -Vname=Bob
` 
  Isn't it faster ? This command will create a new user with name **Bob** and random values for other columns (because we are interested in name only).

Another example with Foreign keys. Ley's say you have the following tables: `groups(id,name),students(id,group_id,name)`. 
In order to create a user you would run two insert queries: first to create a group and second to create a user. With **J-Riddle** you
can use this command `jriddler -table students`, it will take care about **Foreign key** dependencies and will create a new group row fow you.


# Supported types
1. Text
2. Varchar
3. Timestamps
4. Numbers (int,double precision)
5. Boolean 

# Features to be implemented
- [X] Support Foreign keys 
- [ ] Support Constraints 

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

If you want to specify custom value for column then use **-V**(custom value) attribute

Example:
`
java -jar target/jriddler.jar -table test -host localhost -port 5432 -name postgres -password 123 -db test -Vemail=test@gmail.com
`

in this case **J-Riddler** will use **test@gmail.com** as a value for **email** and random values for other columns


## TODO
1. Add a native image support  

## Contributions

Contributions are welcome! Please, open an issue before submit any kind (ideas,
documentation, code, ...) of contribution.

Use this command to check your build

```
mvn checkstyle:check test
```
