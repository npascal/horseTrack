package org.pascalot.racePark;

import org.pascalot.io.UserInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hamisu on 11/23/15.
 */
public class HorseRacePark implements UserInputListener{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);


    public void setWinningNumber(int number)
    {

    }

    public int calculateTicketWinnings(){

        return 0;
    }

    @Override
    public void handleUserInput(UserInput userInput) {

    }

}
