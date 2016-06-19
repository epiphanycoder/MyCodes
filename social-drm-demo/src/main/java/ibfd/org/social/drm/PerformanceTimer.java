package ibfd.org.social.drm;

import java.io.File;

/**
 * Class to measure elapsed time in milliseconds using Java's <code>System.nanoTime()</code> timer.
 *
 * @author steef
 * @version $Id: PerformanceTimer.java,v 1.1 2015/08/06 07:22:09 steef Exp $
 */
public class PerformanceTimer {
    /**
     * Number of nanoseconds per millisecond.
     */
    private static final long NANOS_PER_MILLISECOND = 1000000;

    /**
     * Number of milliseconds per second
     */
    private static final int MILLIS_PER_SECOND = 1000;

    /**
     * Number of bytes per kilobyte
     */
    private static final int BYTES_PER_MEGABYTE = 1024 * 1024;

    /**
     * Start and end timestamps
     */
    private long t1, t2;

    /**
     * Flag indicating the process has stopped
     */
    private boolean stopped;

    /**
     * Constructor. Starts the timer.
     */
    public PerformanceTimer() {
        start();
    }

    /**
     * Start the timer.
     *
     */
    public void start() {
        stopped = false;
        t1 = System.nanoTime();
    }

    /**
     * Stops the timer.
     *
     * @return this performance timer.
     */
    public PerformanceTimer stop() {
        t2 = System.nanoTime();
        stopped = true;
        return this;
    }

    /**
     * Get the elapsed time in milliseconds. Throws IllegalStateException if the timer was not
     * stopped yet.
     *
     * @return the number of milliseconds.
     */
    public long getMsec() {
        if (!stopped) {
            throw new IllegalStateException("timer not stopped yet");
        }
        return (t2 - t1) / NANOS_PER_MILLISECOND;
    }

    /**
     * Get the elapsed time in nanoseconds. Throws IllegalStateException if the timer was not
     * stopped yet.
     *
     * @return the number of nanoseconds.
     */
    public long getNanoSec() {
        if (!stopped) {
            throw new IllegalStateException("timer not stopped yet");
        }
        return (t2 - t1);
    }

    /**
     * Reports the elapsed time in milliseconds and the number of megabytes processed per second. If
     * the timer was not stopped yet it will be stopped before returning from this method.<br/>
     *
     * @param prefix the prefix text
     * @param name the name of the object to report about
     * @param size the size of the object in bytes
     * @return the time report string
     */
    public String report(String prefix, String name, long size) {
        if (!stopped) {
            stop();
        }
        double lapseTimeInMilliSeconds = (t2 - t1) / NANOS_PER_MILLISECOND;
        long milliSeconds = (long) lapseTimeInMilliSeconds;
        double seconds = lapseTimeInMilliSeconds / MILLIS_PER_SECOND;
        double mbSec = 0;
        try {
            mbSec = size / BYTES_PER_MEGABYTE / seconds;
        } catch (Exception ex) {
            mbSec = 0;
        }
        return prefix + " " + name + " took " + milliSeconds + " mSec. (" + String.format("%3.2f", mbSec) + " Mb/Sec).";
    }

    /**
     * Reports the elapsed time in milliseconds and the number of megabytes processed per second. If
     * the timer was not stopped yet it will be stopped before returning from this method.<br/>
     * Example with a prefix of "<code>processing</code>" and a file named "<code>some.xml</code>"
     * with a size of 96.56 Mb:
     *
     * <pre>
     * processing some.xml took 12672 mSec. (7.62 Mb/Sec).
     * </pre>
     *
     * @param prefix the prefix text
     * @param file the file to report about
     * @return the time report string
     */
    public String report(String prefix, File file) {
        return report(prefix, file.getAbsolutePath(), file.length());
    }

    /**
     * Report the elapsed time in milliseconds. If the timer was not stopped yet it will be stopped
     * before returning from this method. <br/>
     * Example with a prefix of "<code>processing</code>":
     *
     * <pre>
     * processing took 12672 mSec.
     * </pre>
     *
     * @param prefix the prefix text
     * @param format the format of the miliseconds
     * @return the time report string
     */
    public String report(String prefix, String format) {
        if (!stopped) {
            stop();
        }
        float time = getNanoSec() / NANOS_PER_MILLISECOND;
        if (time < 1) {
            time = 1;
            return prefix + " took less than " + String.format(format, time);
        } else {
            return prefix + " took " + String.format(format, time).trim();
        }
    }

    /**
     * Report the elapsed time in milliseconds. If the timer was not stopped yet it will be stopped
     * before returning from this method. <br/>
     * Example with a prefix of "<code>processing</code>":
     *
     * <pre>
     * processing took 12672 mSec.
     * </pre>
     *
     * @param prefix the prefix text
     * @return the time report string
     */
    public String report(String prefix) {
        return report(prefix, "%3.0f mSec.");
    }
}