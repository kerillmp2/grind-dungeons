package utils.threads;

import java.util.concurrent.TimeUnit;

import utils.ConsoleInput;

public class ExitThread extends Thread {
    private volatile boolean finishThread = false;

    @Override
    public void run() {
        this.finishThread = false;
        ConsoleInput consoleInput = new ConsoleInput(4, 1, TimeUnit.SECONDS);
        try {
            while (!isFinished()) {
                String input = consoleInput.readLine();
                if (input != null) {
                    if (input.equalsIgnoreCase("exit")
                            || input.equalsIgnoreCase("quit")
                            || input.equalsIgnoreCase("e")
                            || input.equalsIgnoreCase("q")) {
                        finish();
                    }
                }
            }
        } catch (InterruptedException e) {
            finish();
        }
    }

    public void finish() {
        finishThread = true;
    }

    public boolean isFinished() {
        return finishThread;
    }
}
