package org.pascalot.io;

import org.pascalot.racePark.HorseRaceParkSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

/**
 * Created by hamisu on 11/25/15.
 */
public class SimulatorMain
{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    public static void main(String [ ] args)
    {
        HorseRaceParkSimulator horseRaceParkSimulator = new HorseRaceParkSimulator();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        logger.debug("\n"+horseRaceParkSimulator.getDefaultUserOutput().toString());
        while (!(userInput.toString().toLowerCase().contains("q"))) {
            if (br == null) {
                System.err.println("No console.");
                System.exit(1);
            }
            logger.debug("Enter input command");
            try
            {
                userInput = br.readLine();
                if (userInput != null && userInput.toString().toLowerCase().contains("q"))
                {
                    System.exit(-1);
                }
                if(userInput.length()==1 && !Character.isWhitespace(userInput.charAt(0)))
                {
                    logger.debug("processing user input: " + userInput);
                    if(Character.isDigit(userInput.charAt(0)))
                    {
                        logger.debug("commandString is a digit: " + userInput);
                        horseRaceParkSimulator.getInputMessageQueue().handleUserInput(new UserInput(Character.valueOf(Character.forDigit(userInput.charAt(0), 10)), null));
                    }
                    else
                        horseRaceParkSimulator.getInputMessageQueue().handleUserInput(new UserInput(Character.valueOf(userInput.charAt(0)),null));
                }
                else if(userInput.length() >= 3)
                {
                    logger.debug("processing user input: " + userInput);
                    boolean isIntegerArgument = false;
                    boolean isIntegerCommand = false;
                    Integer numberCommand;
                    Integer numberArgument;
                    try
                    {
                        numberArgument =Integer.parseInt(userInput.substring(1).trim());
                        isIntegerArgument = true;
                    }catch (NumberFormatException nfe){
                        numberArgument = null;
                        isIntegerArgument = false;
                    }

                    try
                    {
                        numberCommand =Integer.parseInt(userInput.split(" ")[0]);
                        isIntegerCommand = true;
                    }catch (NumberFormatException nfe){
                        numberCommand = null;
                        isIntegerCommand = false;
                    }
                    if(Character.isDigit(userInput.charAt(0)))
                    {
                    	 if(!isIntegerArgument)
                         {
                             logger.debug(MessageFormat.format("Invalid Bet: {0} {1}", userInput.substring(1).trim(),"\n"+horseRaceParkSimulator.getDefaultUserOutput().toString()));
                         }
                    	 else
                        horseRaceParkSimulator.getInputMessageQueue().handleUserInput(new UserInput(Character.valueOf(userInput.charAt(0)), numberArgument));
                    }
                    else if(isIntegerCommand){
                        logger.debug(MessageFormat.format("Invalid Horse Number: {0} {1}", numberCommand,"\n"+horseRaceParkSimulator.getDefaultUserOutput().toString()));
                    }
                }

            }catch (IOException e){
                logger.debug(e.getMessage());
        }
    }

}
}
