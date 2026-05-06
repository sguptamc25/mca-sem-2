import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentFileSearch {

    public static void main(String[] args) {
        // 1. Validate command line arguments
        if (args.length != 2) {
            System.out.println("Error: Invalid number of arguments.");
            System.out.println("Usage: java ConcurrentFileSearch <directory_path> <search_text>");
            return;
        }

        String directoryPath = args[0];
        String searchText = args[1];

        File rootDirectory = new File(directoryPath);

        if (!rootDirectory.exists() || !rootDirectory.isDirectory()) {
            System.out.println("Error: The provided path is not a valid directory.");
            return;
        }

        System.out.println("Searching for '" + searchText + "' in: " + rootDirectory.getAbsolutePath() + "\n");

        // 2. Create an ExecutorService (Thread Pool)
        // We use a fixed thread pool based on the number of available CPU cores
        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(coreCount);

        // 3. Start recursive directory traversal
        traverseAndSearch(rootDirectory, searchText, executor);

        // 4. Shut down the executor and wait for all tasks to finish
        executor.shutdown();
        try {
            // Wait a reasonable amount of time for all threads to finish their current tasks
            if (!executor.awaitTermination(10, TimeUnit.MINUTES)) {
                executor.shutdownNow(); // Force shutdown if it takes too long
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nSearch complete.");
    }

    // Recursive method to find files and submit them to the thread pool
    private static void traverseAndSearch(File currentFile, String searchText, ExecutorService executor) {
        File[] files = currentFile.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // If it's a directory, recurse into it
                    traverseAndSearch(file, searchText, executor);
                } else {
                    // If it's a file, submit a new search task to the ExecutorService
                    executor.submit(new SearchTask(file, searchText));
                }
            }
        }
    }
}

// Runnable task that handles the actual reading and counting for a single file
class SearchTask implements Runnable {
    private final File file;
    private final String searchText;

    public SearchTask(File file, String searchText) {
        this.file = file;
        this.searchText = searchText;
    }

    @Override
    public void run() {
        int count = 0;
        // Try-with-resources ensures the BufferedReader is closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int index = 0;
                // Count all occurrences in the current line
                while ((index = line.indexOf(searchText, index)) != -1) {
                    count++;
                    index += searchText.length(); // Move past the found word
                }
            }
            
            // Output the result. (Optional: You can wrap this in an 'if (count > 0)' 
            // to only print files that actually contain the text)
            System.out.println("File: " + file.getName() + " | Count: " + count);
            
        } catch (IOException e) {
            // Handle files that can't be read (e.g., permission denied)
            System.err.println("Could not read file: " + file.getName());
        }
    }
}