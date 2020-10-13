package com.epam.automation.common.utils;

import java.util.Random;

public class Common {

    public int getRandomNumber() {
        return new Random().nextInt(900) + 100;
    }
}
