package com.timezerg.app.util;

/**
 * Created by xnx on 2018/5/4.
 */
public class Utils {

    private static final IdWorker worker = new IdWorker(1);

    /**
     * 生成ID
     *
     * @return
     */
    public static String generateId() {
        return String.valueOf(worker.nextId());
    }


}
