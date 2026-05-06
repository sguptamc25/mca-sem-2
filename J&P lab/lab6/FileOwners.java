import java.util.*;

public class FileOwners {

    public static void main(String[] args) {
        // 1. Set up the input "dictionary" (Map)
        Map<String, String> files = new LinkedHashMap<>();
        files.put("Input.txt", "Albert");
        files.put("Code.py", "Stanley");
        files.put("Output.txt", "Albert");
        files.put("btech.txt", "Albert");

        System.out.println("--- Original Files Map ---");
        System.out.println(files);

        // 2. Call our function
        Map<String, List<String>> groupedAndSorted = groupAndSortOwners(files);

        // 3. Print the result
        System.out.println("\n--- Grouped and Sorted by Owner ---");
        System.out.println(groupedAndSorted);
    }

    /**
     * Groups file names by owner and sorts the lists of files alphabetically.
     * * @param files Map where key is file name and value is owner name
     * 
     * @return Map where key is owner name and value is a sorted list of file names
     */
    public static Map<String, List<String>> groupAndSortOwners(Map<String, String> files) {
        Map<String, List<String>> groupedFiles = new HashMap<>();

        // Iterate through each entry in the input map
        for (Map.Entry<String, String> entry : files.entrySet()) {
            String fileName = entry.getKey();
            String owner = entry.getValue();

            // computeIfAbsent is a clean Java 8 feature:
            // If the owner isn't in the map yet, create an empty ArrayList for them.
            // Then, add the current file to that list.
            groupedFiles.computeIfAbsent(owner, k -> new ArrayList<>()).add(fileName);
        }

        // Now that everything is grouped, sort each owner's list of files
        // alphabetically
        for (List<String> fileList : groupedFiles.values()) {
            // By default, Collections.sort sorts Strings alphabetically (case-sensitive)
            // Note: uppercase letters sort before lowercase ('Input' before 'btech')
            Collections.sort(fileList, String.CASE_INSENSITIVE_ORDER);
        }

        return groupedFiles;
    }
}