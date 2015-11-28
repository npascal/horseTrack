package org.pascalot.racePark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.LinkedHashSet;

/**
 * Created by hamisu on 11/24/15.
 */
public class Inventory {

    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    public LinkedHashSet<Horse> getHorseList() {
        return horseList;
    }

    public Hashtable<Money, Integer> getMoneyInventory() {
        return moneyInventory;
    }

    private LinkedHashSet<Horse> horseList;
    private Hashtable<Money,Integer> moneyInventory;

    public Inventory() {
    }

    public Inventory(LinkedHashSet<Horse> horseList, Hashtable<Money, Integer> moneyInventory) {
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
            horseList.add(horse);
            return true;
        }
        return false;
    }

    public boolean removeAllHorses(){
        return horseList != null && !horseList.isEmpty() && horseList.removeAll(this.getHorseList());
    }

    public boolean addMoney(Money money){
        final Boolean found[] = new Boolean[1];
        found[0]=false;
        moneyInventory.entrySet().stream().forEach(entry -> {
            if(entry.getKey().equals(money))
             entry.setValue(entry.getValue()+1);
            found[0] = true;
        });
        return found[0];
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

}
