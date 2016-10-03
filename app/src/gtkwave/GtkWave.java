package gtkwave;

import java.io.IOException;
import java.util.ArrayList;

public class GtkWave extends Thread {
    private Process gtkProcess;
    private final String gtkPath = "gtkwave";

    private String dumpFile;

    private boolean isFinished;
    private boolean isFailed;

    private int exitValue;

    public  GtkWave(String dumpFile) {
        this.dumpFile = dumpFile;
    }

    public void run() {
        ArrayList<String> params = collectOptions();
        String[] commandLine = new String[params.size()];
        commandLine = params.toArray(commandLine);

        try {
            System.out.println("Starting GtkWave process...");
            gtkProcess = Runtime.getRuntime().exec(commandLine);

            isFinished = false;
            isFailed = false;

            while (true) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }

                try {
                    exitValue = gtkProcess.exitValue();
                    break;
                } catch (IllegalThreadStateException e) {
                    sleep(50);
                    continue;
                }
            }

            System.out.println("GtkWave has terminated with exit code: " + exitValue);

            if (0 == exitValue) {
                isFinished = true;
            } else {
                isFailed = true;
            }

        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            finish();
            return;
        } catch (IOException e) {
            System.out.println("GtkWave error: " + e.getMessage());
            e.printStackTrace();
            return;
        }

    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isFailed() {
        return isFailed;
    }

    public boolean isRunning(){
    	return isFinished || isFailed;
    }
    
    public int exitValue() {
        return exitValue;
    }
    
    private ArrayList<String> collectOptions() {
        ArrayList<String> options = new ArrayList<String>();
        options.add(gtkPath);
        options.add("-f");
        options.add(dumpFile);

        return options;
    }

    public void kill() {
        if (gtkProcess != null) {
            gtkProcess.destroy();
        }
    }

    private void finish() {
        if (gtkProcess != null) {
            gtkProcess.destroy();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        finish();

        super.finalize();
    }
}
