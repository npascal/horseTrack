package org.pascalot.io;

import org.pascalot.racePark.OutputMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by hamisu on 11/25/15.
 */
public class OutputMessageQueue extends ThreadPoolExecutor implements OutputMessageListener
{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);
    private ConsoleDisplay consoleDisplay;

    public OutputMessageQueue(int corePoolSize, int maxPoolSize, long keepAlive, TimeUnit unit, ConsoleDisplay consoleDisplay)
    {
        super(corePoolSize, maxPoolSize, keepAlive, unit, new ArrayBlockingQueue<>(maxPoolSize, true), r -> new Thread(r, "HorseRaceParkOutputMessageQueue"),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        this.consoleDisplay = consoleDisplay;
    }
    @Override
    public void handleUserOuput(UserOutput userOutput)
    {
        //logger.debug("Received output message on output queue" + userOutput.toString());
        if(consoleDisplay != null)
            submit(new QueuedOutputMessageTask(consoleDisplay,userOutput));
    }
}
