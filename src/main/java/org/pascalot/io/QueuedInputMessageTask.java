package org.pascalot.io;

import org.pascalot.racePark.HorseRaceParkSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hamisu on 11/25/15.
 */
public class QueuedInputMessageTask implements Runnable
{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    private long receivedStamp = System.currentTimeMillis();
    private HorseRaceParkSimulator horseRaceParkSimulator;
    private Message message;


    public QueuedInputMessageTask(Message message,HorseRaceParkSimulator horseRaceParkSimulator)
    {
        this.message = message;
        this.horseRaceParkSimulator = horseRaceParkSimulator;
    }

    @Override
    public void run()
    {
        if(horseRaceParkSimulator != null)
        {
            try
            {
                horseRaceParkSimulator.processUserInput((UserInput) message);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
