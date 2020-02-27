package com.jriddler;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

/**
 * Db user.
 */
@AllArgsConstructor
@Getter
final class User {
    /**
     * Name.
     */
    private final String name;

    /**
     * Surname.
     */
    private final String surname;

    /**
     * Birthday.
     */
    private final OffsetDateTime birthday;

    /**
     * Age.
     */
    private final int age;

    /**
     * Id.
     */
    private final long id;

    /**
     * Active.
     */
    private final boolean active;

}
