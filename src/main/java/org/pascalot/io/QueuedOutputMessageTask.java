package org.pascalot.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Created by hamisu on 11/25/15.
 */
public class QueuedOutputMessageTask implements Runnable
{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    private long receivedStamp = System.currentTimeMillis();
    private ConsoleDisplay consoleDisplay;
    private Message message;

    public QueuedOutputMessageTask(ConsoleDisplay consoleDisplay, Message message)
    {
        this.consoleDisplay = consoleDisplay;
        this.message = message;
    }

    @Override
    public void run()
    {
        logger.debug(MessageFormat.format("Displaying horse race park output message received at timestamp: {0,number}", receivedStamp));
        consoleDisplay.displayUserMessage((UserOutput) message);
    }
}
