package com.jriddler;

import java.sql.SQLException;

public interface SqlOperation<T> {

    T perform() throws SQLException;
}
