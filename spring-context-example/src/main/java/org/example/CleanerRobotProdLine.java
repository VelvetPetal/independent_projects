package org.example;

public class CleanerRobotProdLine implements RoboProductionLine{
    @Override
    public void work() {
        System.out.println("new cleaner robot build completed");
    }
}
