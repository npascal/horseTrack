package org.pascalot.racePark;

import org.pascalot.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by hamisu on 11/23/15.
 */
public class HorseRaceParkSimulator
{
    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);
    private long userInputTimeStamp = System.currentTimeMillis();
    private InputMessageQueue inputMessageQueue;
    private OutputMessageQueue outputMessageQueue;
    private Inventory inventory;

    public HorseRaceParkSimulator()
    {
        inputMessageQueue = new InputMessageQueue(2,2,1, TimeUnit.SECONDS, this);
        outputMessageQueue = new OutputMessageQueue(2,2,1,TimeUnit.SECONDS,new ConsoleDisplay());
        //Initialize cash Inventory and hose list
        initializeInventories();
    }

    private void initializeInventories()
    {
        Map<Money,Integer> moneyHashtable = Collections.synchronizedMap(new LinkedHashMap<>());
        moneyHashtable.put(new Money(Currency.getInstance("USD"),1), 10);
        moneyHashtable.put(new Money(Currency.getInstance("USD"),5), 10);
        moneyHashtable.put(new Money(Currency.getInstance("USD"), 10), 10);
        moneyHashtable.put(new Money(Currency.getInstance("USD"),20), 10);
        moneyHashtable.put(new Money(Currency.getInstance("USD"),100), 10);

        Set<Horse> horseLinkedHashSet = Collections.synchronizedSet(new LinkedHashSet<>());
        horseLinkedHashSet.add(new Horse(1,"That Darn Gray Cat", 5));
        horseLinkedHashSet.add(new Horse(2,"Fort Utopia", 10));
        horseLinkedHashSet.add(new Horse(3,"Count Sheep", 9));
        horseLinkedHashSet.add(new Horse(4,"Ms Traitour", 4));
        horseLinkedHashSet.add(new Horse(5,"Real Princess", 3));
        horseLinkedHashSet.add(new Horse(6,"Pa Kettle", 5));
        horseLinkedHashSet.add(new Horse(7,"Gin Stinger", 6));

        inventory = new Inventory(horseLinkedHashSet, moneyHashtable);
        inventory.setWinningHorse(1);
    }

    public void setWinningNumber(int number)
    {
        inventory.setWinningHorse(number);
    }

    public HorseRaceParkSimulator(OutputMessageQueue outputMessageQueue, InputMessageQueue inputMessageQueue)
    {
        this.outputMessageQueue = outputMessageQueue;
        this.inputMessageQueue = inputMessageQueue;
        //Initialize cash Inventory and hose list
        initializeInventories();
    }

    public int calculateTicketWinnings(UserInput in){
        logger.debug(MessageFormat.format("winning number is {0}", inventory.getWinningHorse()));
            if(Character.digit(in.getCommandString().charValue(),10) == inventory.getWinningHorse())
            {
                List<Horse> winingHorse = inventory.getHorseList().stream().filter(h -> h.getId().intValue()==inventory.getWinningHorse()).collect(Collectors.toList());
                if(winingHorse != null && !winingHorse.isEmpty())
                {
                    return ((Horse)winingHorse.get(0)).getOdds().intValue()*in.getArgument().intValue();
                }
            }
        return -1;
    }


    public void processUserInput(UserInput userInput) throws Exception
    {
        logger.debug(MessageFormat.format("Received user input {0} at timestamp : {1, number}", userInput.toString(), userInputTimeStamp));
        UserInput input = userInput;
        UserOutput output = null;
        switch(input.getCommandString().charValue())
        {
            case 'R':
            case 'r':
            {
                if(input.getArgument() == null)
                {
                    restockMoneyInventory();
                    output = getDefaultUserOutput();
                }
                else
                    output = new UserOutput("Invalid Command: " + userInput.toString());
                break;
            }
            case 'W':
            case 'w':
            {
                if(input.getArgument() != null)
                {
                    setWinningNumber(input.getArgument());
                    output = getDefaultUserOutput();
                }
                else
                    output = new UserOutput("Invalid Command: " + userInput.toString());
                break;
            }
            default:{
                if(Character.isDigit(input.getCommandString().charValue()) && input.getArgument() != null)
                {
                    if(!inventory.hasHorseNumber(Integer.valueOf(input.getCommandString().toString())))
                        output = new UserOutput("Invalid Horse Number: "+ input.getCommandString());
                    else
                    {
                        int res = calculateTicketWinnings(input);
                        output = payout(res);
                    }
                }
                else
                    output = new UserOutput("Invalid Command: " + userInput.toString());
            }

        }
        processUserOutput(output);
    }

    public UserOutput getDefaultUserOutput()
    {
        UserOutput output;
        StringBuilder payoutMsg = new StringBuilder();
        payoutMsg.append(inventory.returnInventoryAsString());
        payoutMsg.append(inventory.returnHorseListAsString());
        output = new UserOutput(payoutMsg.toString());
        return output;
    }

    private UserOutput payout(int res) throws Exception
    {
        if(!inventory.hasSufficientFunds(res))
            return new UserOutput("Insufficient Funds: "+ res);
        StringBuilder payoutMsg = new StringBuilder();
        switch(res){
            case -1:
            {
                payoutMsg.append("No Payout:\n");
                break;
            }
            default:{
                payoutMsg.append("Payout: ");
                payoutMsg.append(
                        inventory.getHorseList().stream().filter(h -> h.getId().intValue() == inventory.getWinningHorse()).map(Horse::getName).collect(Collectors.toList()).get(0)
                        );
                payoutMsg.append(",");
                payoutMsg.append(getCurrencySymbol());
                payoutMsg.append(res);
                payoutMsg.append("\n");
                payoutMsg.append("Dispensing:\n");
                List<String> list = getPayOutDenominations(res);
                for(int i = list.size()-1; i >= 0; i--)
                {
                    payoutMsg.append(list.get(i));
                    payoutMsg.append("\n");
                }
            }

        }
        payoutMsg.append(inventory.returnInventoryAsString());
        payoutMsg.append(inventory.returnHorseListAsString());
        return new UserOutput(payoutMsg.toString());
    }

    private List<String> getPayOutDenominations(int res) throws Exception
    {
        int amount = res;
        StringBuilder payoutMsg = new StringBuilder();
        List<Integer> denominations = inventory.getMoneyInventory().keySet().stream().filter(m -> (!inventory.getMoneyInventory().get(m).equals(Integer.valueOf(0)) || inventory.getMoneyInventory().get(m).intValue() < 0)).map(Money::getDenomination).collect(Collectors.toList());
        for(int i = denominations.size()-1; i >= 0; i --)
        {
            payoutMsg.append(getCurrencySymbol());
            payoutMsg.append(denominations.get(i));
            payoutMsg.append(",");
           if(amount >= denominations.get(i)){
               int d = amount/denominations.get(i);
               final int finalI = i;
               final List<Integer> finalDenominations = denominations;
               inventory.getMoneyInventory().entrySet().stream().forEach(entry -> {
                   if(entry.getKey().getDenomination().equals(finalDenominations.get(finalI)))
                   {
                       entry.setValue(entry.getValue() - d);
                   }
               });

               payoutMsg.append(d);
               payoutMsg.append("\n");
               amount -= (denominations.get(i)* d);

           }
           else
           {
               payoutMsg.append(0);
               payoutMsg.append("\n");
           }
        }
        return Arrays.asList(payoutMsg.toString().split("\n"));
    }

    private String getCurrencySymbol()
    {
        if(inventory.getMoneyInventory().keySet().iterator().hasNext())
               return inventory.getMoneyInventory().keySet().iterator().next().getCurrency().getSymbol();
        else
           return "$";
    }

    private void restockMoneyInventory()
    {
        inventory.getMoneyInventory().entrySet().stream().forEach(m -> {
            if (m.getValue().intValue() != 10)
                m.setValue(10);
        });
    }

    public void processUserOutput(UserOutput userOutput)
    {
        if(outputMessageQueue != null)
        {
            outputMessageQueue.handleUserOuput(userOutput);
        }
    }

    public InputMessageQueue getInputMessageQueue()
    {
        return inputMessageQueue;
    }

    public OutputMessageQueue getOutputMessageQueue()
    {
        return outputMessageQueue;
    }
}
