package com.vishnu.springbootjms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class description
 *
 * @author : vishnu.g
 * created on : 27/Aug/2020
 */
public class TempStore {
    static final int capacity = 10;
    public static List<QueueMessage> messageStore = new CopyOnWriteArrayList();
}
