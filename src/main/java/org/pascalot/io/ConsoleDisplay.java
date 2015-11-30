package org.pascalot.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Created by hamisu on 11/25/15.
 */
public class ConsoleDisplay
{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    public void displayUserMessage(UserOutput outputMessage){
        logger.debug(MessageFormat.format("Received user output:\n{0}", outputMessage.toString()));
    }
}
