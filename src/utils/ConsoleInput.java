package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsoleInput {
    private final int tries;
    private final int timeout;
    private final TimeUnit unit;

    public ConsoleInput(int tries, int timeout, TimeUnit unit) {
        this.tries = tries;
        this.timeout = timeout;
        this.unit = unit;
    }

    public ConsoleInput() {
        this(-1, -1, TimeUnit.SECONDS);
    }

    public String readLine() throws InterruptedException {
        if (tries < 0 || timeout < 0) {
            Scanner in = new Scanner(System.in);
            try {
                String s = in.next();
                in.close();
                return s;
            } catch (Exception e) {
                in.close();
                return "";
            }
        }
        ExecutorService ex = Executors.newSingleThreadExecutor();
        String input = null;
        try {
            // start working
            for (int i = 0; i < tries; i++) {
                Future<String> result = ex.submit(new ConsoleInputReadTask());
                try {
                    input = result.get(timeout, unit);
                    break;
                } catch (ExecutionException e) {
                    e.getCause().printStackTrace();
                } catch (TimeoutException e) {
                    result.cancel(true);
                }
            }
        } finally {
            ex.shutdownNow();
        }
        return input;
    }

    static class ConsoleInputReadTask implements Callable<String> {
        public String call() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input;
            do {
                try {
                    while (!br.ready()) {
                        Thread.sleep(200);
                    }
                    input = br.readLine();
                } catch (InterruptedException e) {
                    return null;
                }
            } while ("".equals(input));
            return input;
        }
    }
}
