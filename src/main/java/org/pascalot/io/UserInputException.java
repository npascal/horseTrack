package org.pascalot.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hamisu on 11/23/15.
 */
public class UserInputException extends Exception {

    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    public UserInputException(String message) {
        super(message);
    }
}
