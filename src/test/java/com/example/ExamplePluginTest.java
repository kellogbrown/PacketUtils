package com.example;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PacketUtils.PacketUtilsPlugin;
import net.runelite.client.externalplugins.ExternalPluginManager;
import net.runelite.client.RuneLite;

import java.io.File;
import java.io.IOException;

public class ExamplePluginTest {
    public static void main(String[] args) throws Exception {
        // Trigger gradle build first
        triggerGradleBuild();
        
        // Enable assertions programmatically
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        
        // Set developer mode properties
        System.setProperty("java.awt.headless", "false");
        
        // Create new args array with additional RuneLite arguments
        String[] runeliteArgs = new String[args.length + 2];
        System.arraycopy(args, 0, runeliteArgs, 0, args.length);
        runeliteArgs[args.length] = "--insecure-write-credentials";
        runeliteArgs[args.length + 1] = "--developer-mode";
        
        ExternalPluginManager.loadBuiltin(EthanApiPlugin.class, PacketUtilsPlugin.class);
        RuneLite.main(runeliteArgs);
    }
    
    private static void triggerGradleBuild() throws IOException, InterruptedException {
        System.out.println("Triggering Gradle build...");
        
        // Get the project root directory
        File projectRoot = new File(System.getProperty("user.dir"));
        
        // Determine the gradle command based on OS
        String gradleCommand = System.getProperty("os.name").toLowerCase().contains("windows") ? 
            "gradlew.bat" : "./gradlew";
        
        // Build the process
        ProcessBuilder processBuilder = new ProcessBuilder(gradleCommand, "FatJar");
        processBuilder.directory(projectRoot);
        processBuilder.inheritIO(); // This will show gradle output in console
        
        // Execute the build
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        
        if (exitCode == 0) {
            System.out.println("Gradle build completed successfully!");
        } else {
            System.err.println("Gradle build failed with exit code: " + exitCode);
            System.exit(exitCode);
        }
    }
}
