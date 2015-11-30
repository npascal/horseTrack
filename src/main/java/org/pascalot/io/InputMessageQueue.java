package org.pascalot.io;

import org.pascalot.racePark.HorseRaceParkSimulator;
import org.pascalot.racePark.UserInputListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hamisu on 11/25/15.
 */
public class InputMessageQueue  extends ThreadPoolExecutor implements UserInputListener
{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);
    private HorseRaceParkSimulator horseRaceParkSimulator;

    public InputMessageQueue(int corePoolSize, int maxPoolSize, long keepAlive, TimeUnit unit, HorseRaceParkSimulator horseRaceParkSimulator){
        super(corePoolSize, maxPoolSize, keepAlive, unit, new ArrayBlockingQueue<>(maxPoolSize, true), r -> new Thread(r, "HorseRaceParkOutputMessageQueue"),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        this.horseRaceParkSimulator = horseRaceParkSimulator;
    }

    @Override
    public void handleUserInput(UserInput userInput)
    {
        logger.debug("Received input message on input queue " + userInput.toString());
        if(horseRaceParkSimulator != null)
        {
            submit(new QueuedInputMessageTask(userInput,horseRaceParkSimulator));
        }

    }
}
