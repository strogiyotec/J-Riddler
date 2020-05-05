package com.jriddler.text;

/**
 * Print help information.
 */
public final class Help implements Text {
    @Override
    public String asString() {
        return String.join(
                "\n",
                "J-Riddler is rows generator for postgres",
                "Supported parameters",
                "-host=Host",
                "\tSpecify postgres host. Localhost by default",
                "-port=Port",
                "\tSpecify postgres port. 5432 by default",
                "-username=Database username",
                "\tSpecify postgres username",
                "-table=Table name",
                "\tSpecify table",
                "-password=User password",
                "\tSpecify password to use",
                "-db=Database name",
                "\tSpecify database name",
                "-version=Version",
                "\tPrint current version",
                "-help=Help",
                "\tShow help information",
                "-V=Custom values for columns"

        );
    }
}
