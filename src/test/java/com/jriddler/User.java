package com.jriddler;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
final class User {
    private final String name;

    private final String surname;

    private final OffsetDateTime birthday;

    private final int age;

    private final long id;

    private final boolean active;

}
