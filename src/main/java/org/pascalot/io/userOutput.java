package org.pascalot.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by hamisu on 11/23/15.
 */
public class UserOutput implements Serializable{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);
}
