package org.pascalot.racePark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hamisu on 11/24/15.
 */
public class Inventory {

    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    public Set<Horse> getHorseList() {
        return horseList;
    }

    public Map<Money, Integer> getMoneyInventory() {
        return moneyInventory;
    }

    private Set<Horse> horseList;
    private Map<Money,Integer> moneyInventory;

    public void setWinningHorse(int winningHorse)
    {
        this.winningHorse = winningHorse;
    }

    public int getWinningHorse()
    {
        return winningHorse;
    }

    private int winningHorse;

    public Inventory() {
    }

    public Inventory(Set<Horse> horseList, Map<Money, Integer> moneyInventory) {
        this.horseList = horseList;
        this.moneyInventory = moneyInventory;
    }

    public boolean addHorse(Horse horse)
    {
        return horseList.add(horse);
    }

    public boolean removeHorse(Horse horse)
    {
        if (horseList.contains(horse)){
            horseList.remove(horse);
            return true;
        }
        return false;
    }

    public boolean removeAllHorses(){
        return horseList != null && !horseList.isEmpty() && horseList.removeAll(this.getHorseList());
    }

    public boolean addMoney(Money money){
        final Boolean added[] = new Boolean[1];
        added[0]=false;
        moneyInventory.entrySet().stream().forEach(entry -> {
            if(entry.getKey().equals(money))
             entry.setValue(entry.getValue()+1);
            added[0] = true;
        });
        return added[0];
    }

    public boolean removeMoney(Money money){
        final Boolean removed[] = new Boolean[1];
        removed[0]=false;
        moneyInventory.entrySet().stream().forEach(entry -> {
            if(entry.getKey().equals(money))
                entry.setValue(entry.getValue()+1);
            removed[0] = true;
        });
        return removed[0];
    }

    public String returnInventoryAsString(){
        StringBuilder inventoryAndRaceResults = new StringBuilder();
        inventoryAndRaceResults.append("Inventory:\n");
        moneyInventory.entrySet().forEach(e -> {
            inventoryAndRaceResults.append(e.getKey().toString());
            inventoryAndRaceResults.append(",");
            inventoryAndRaceResults.append(e.getValue().intValue());
            inventoryAndRaceResults.append("\n");
        });
        return inventoryAndRaceResults.toString();
    }

    public String returnHorseListAsString(){
        StringBuilder inventoryAndRaceResults = new StringBuilder();
        inventoryAndRaceResults.append("Horses:\n");
        horseList.forEach(h -> {
            inventoryAndRaceResults.append(h.getId().intValue());
            inventoryAndRaceResults.append(",");
            inventoryAndRaceResults.append(h.getName());
            inventoryAndRaceResults.append(",");
            inventoryAndRaceResults.append(h.getOdds().intValue());
            inventoryAndRaceResults.append(",");
            if (h.getId().intValue() == winningHorse)
                inventoryAndRaceResults.append("won");
            else
                inventoryAndRaceResults.append("lost");
            inventoryAndRaceResults.append("\n");
        });
        return inventoryAndRaceResults.toString();
    }

    public boolean hasSufficientFunds(int amount) {
        logger.debug(MessageFormat.format("Available fund in inventory: {0, number}", moneyInventory.entrySet().stream().mapToInt(e -> e.getKey().getDenomination() * e.getValue()).sum()));
        if (moneyInventory.entrySet().stream().mapToInt(e -> e.getKey().getDenomination()*e.getValue()).sum() > amount )
        return true;
        else
           return false;

    }

    public boolean hasHorseNumber(Integer i)
    {
        logger.debug(MessageFormat.format("Searcing horse number {0, number} in horse list",i));
        List<String> availableHorses = horseList.stream().filter(h -> h.getId().intValue() == i.intValue()).map(Horse::getName).collect(Collectors.toList());
        if(availableHorses != null &&
                !availableHorses.isEmpty())
            return true;
        else
            return false;
    }

}
