package com.jriddler.cli;

import com.beust.jcommander.IStringConverter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Convert input from user to list of dynamic attrs.
 */
@SuppressWarnings("LineLength")
final class UserAttrConverter implements IStringConverter<List<UserAttribute>> {
    @Override
    public List<UserAttribute> convert(final String value) {
        final String[] attrs = value.split(",");
        return Stream.of(attrs)
                .map(UserAttribute::new)
                .collect(Collectors.toList());
    }
}
