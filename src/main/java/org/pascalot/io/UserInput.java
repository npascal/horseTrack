package org.pascalot.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by hamisu on 11/23/15.
 */
public class UserInput implements Serializable{

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

    public UserInput(Character commandString, Integer argument) {
        this.commandString = commandString;
        this.argument = argument;
    }

    @Override
    public String toString(){
        if(commandString != null && argument == null)
        return commandString.toString();
        else
            return commandString.toString() + " " + argument.toString();
    }
}
