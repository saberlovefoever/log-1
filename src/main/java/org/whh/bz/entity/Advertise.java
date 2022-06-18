package org.whh.bz.entity;

import lombok.Data;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class Advertise {
    String ad_id;
    String company;
    String about;
    String mediaType;//phone、pc、pad
    URL url;
    Timestamp time;     //start time
    Timestamp  toWhen;  //over time
}
