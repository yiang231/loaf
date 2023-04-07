package com.dl.work.timerDemo;

import java.util.TimerTask;

public class TestTask extends TimerTask {
    @Override
    public void run() {
        new Test().print();
    }
}
