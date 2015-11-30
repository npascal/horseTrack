package org.pascalot.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hamisu on 11/23/15.
 */
public class UserInput implements Message{

    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    public Character getCommandString() {
        return commandString;
    }

    public Integer getArgument() {
        return argument;
    }

    private Character commandString;
    private Integer argument;

    public UserInput(Character commandString, Integer argument)
    {
        this.commandString = commandString;
        this.argument = argument;
    }

    @Override
    public String toString(){
        if(Character.isDigit(commandString.charValue()))
        logger.debug("commandString is a digit " + Character.digit(commandString.charValue(),10));
        if(commandString != null && !commandString.toString().isEmpty())
            if(argument == null)
        return commandString.toString();
        else
            return commandString.toString() + " " + argument.toString();
        else
            return null;
    }
}
