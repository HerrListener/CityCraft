package de.citycraft.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtils {

    public static ExecutorService SINGLE_THREAD = Executors.newCachedThreadPool();

}
