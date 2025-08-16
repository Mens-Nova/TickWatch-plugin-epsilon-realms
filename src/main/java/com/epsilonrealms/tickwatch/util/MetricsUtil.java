package com.epsilonrealms.tickwatch.util;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.util.Locale;

public class MetricsUtil {
    private static OperatingSystemMXBean OS = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    
    private MetricsUtil() {}

    // CPU - System-wide CPU usage
    public static int systemCpuPercent(){
        double v = OS.getCpuLoad();
        
        if(v < 0) return - 1;

        return (int)Math.round(v * 100.0);
    }

    // Current JVM process CPU usage
    public static int processCpuPercent() {
        double v = OS.getProcessCpuLoad();
        if (v < 0) return -1;

        return (int)Math.round(v * 100.0);
    }

    // RAM
    @SuppressWarnings("deprecation")
    public static long systemMemTotalBytes(){
        return OS.getTotalPhysicalMemorySize();
    }

    @SuppressWarnings("deprecation")
    public static long systemMemFreeBytes(){
        return OS.getFreePhysicalMemorySize();
    }

    public static long systemMemUsedBytes(){
        long total = systemMemTotalBytes(), free = systemMemFreeBytes();
        return Math.max(0L, total - free);
    }

    // JVM heap memory
    public static long jvmHeapUsedBytes(){
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public static long jvmHeapMaxBytes(){
        return Runtime.getRuntime().maxMemory();
    }

    // Disk
    public static long diskTotalBytes(Path basePath) {
        File f = basePath.toFile();
        return f.getTotalSpace();
    }

    public static long diskFreeBytes(Path basePath){
        File f = basePath.toFile();
        return f.getFreeSpace();
    }

    public static long diskUsedBytes(Path basePath){
        long t = diskTotalBytes(basePath);
        long f = diskFreeBytes(basePath);
        return Math.max(0L, t - f);
    }

    // Formatting
    public static String fmtBytesMB(long bytes){
        long mb = Math.round(bytes / 1024.0 / 1024.0);
        return mb + "MB";
    }
    
    public static String fmtBytesGB(long bytes){
        double gb = bytes / 1024.0 / 1024.0 / 1024.0;
        return String.format(Locale.US, "%.1fGB", gb);
    }
}
