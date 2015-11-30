package org.pascalot.racePark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by hamisu on 11/23/15.
 */
public class Horse implements Serializable , Comparable<Horse> {

    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    public Horse(Integer id,  String name, Integer odds) {
        this.id = id;
        this.name = name;
        this.odds = odds;
    }

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public Integer getOdds() {
        return odds;
    }

    public String getName() {
        return name;
    }

    private Integer odds;


    @Override
    public String toString()
    {
        return id.toString() + "," + name + "," + odds.toString();
    }

    @Override
    public int compareTo(Horse o) {
        return this.id.compareTo(o.id) & this.name.compareTo(o.name) & this.odds.compareTo(o.odds);
    }

}
