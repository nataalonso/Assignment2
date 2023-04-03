import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;



/**
 * A program that analyzes business data from a CSV file and provides summaries based on user commands.
 */
public class BusinessAnalyzer {

    private List<Business> businesses; // list of all businesses
    private Map<String, List<Business>> naicsMap; // map of businesses by NAICS code
    private Queue<String> commandHistory; // queue of user commands entered so far
    private boolean useArrayList; // flag to indicate whether ArrayList or LinkedList is used

    /**
     * Constructs a BusinessAnalyzer object that reads data from the specified file and uses the specified list implementation.
     * @param fileName the name of the CSV file containing the business data
     * @param useArrayList true if ArrayList should be used false if LinkedList should be used
     */
    public BusinessAnalyzer(String fileName, boolean useArrayList) {
        this.businesses = new ArrayList<>();
        this.naicsMap = new HashMap<>();
        this.commandHistory = new LinkedList<>();
        this.useArrayList = useArrayList;
        readData(fileName);
        processData();
    }

    /**
     * Reads the CSV file into a list of Business objects.
     * @param fileName the name of the CSV file containing the business data
     */
    private void readData(String fileName) {

        try (Scanner scanner = new Scanner(new File(fileName))){
        // skip header row
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");             
                String name = fields[0].trim();          
                String address = fields[1].trim();
                String city = fields[2].trim();
                String state = fields[3].trim();
                String zip = fields[4].trim();
                String naicsCode = fields[5].trim();
                String neighborhood = fields[6].trim();
                LocalDate startDate = LocalDate.parse(fields[7].trim(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalDate closureDate = null;
                if (fields.length >= 9 && !fields[8].isEmpty()) {
                    closureDate = LocalDate.parse(fields[8].trim(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                }
                Business business = new Business(name, address, city, state, zip, naicsCode, neighborhood, startDate, closureDate);
                businesses.add(business);
            }

        } catch (FileNotFoundException e){
            System.err.println("Error: File not found: " + fileName);
        }
    } 

    /**
     * Creates a map of businesses by NAICS code.
     */
    private void processData() {
    
        for (Business business : businesses) {
            String naicsCode = business.getNaicsCode();
            List<Business> businessesForNaicsCode = naicsMap.getOrDefault(naicsCode, new ArrayList<>());
            businessesForNaicsCode.add(business);
            naicsMap.put(naicsCode, businessesForNaicsCode);
        }
    }

    /**
     * Creates a map of business counts by zip code and displays the summary.
     */
    private void summaryByZip() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a zip code: ");
        String zipCode = scanner.nextLine();

        int totalBusinesses = 0;
        Set<String> businessTypes = new HashSet<>();
        Set<String> neighborhoods = new HashSet<>();

        for (Business business : businesses) {
            if (business.getZip().equals(zipCode)) {
                totalBusinesses++;
                businessTypes.add(business.getNaicsCode());
                neighborhoods.add(business.getNeighborhood());
            }
        }

        System.out.println(zipCode + " Business Summary");
        System.out.println("Total Businesses: " + totalBusinesses);
        System.out.println("Business Types: " + businessTypes.size());
        System.out.println("Neighborhoods: " + neighborhoods.size());

    }

    /**
     * Prompts the user for a NAICS code and displays the summary for that code range.
     */
    private void summaryByNaics() {
       
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter NAICS code: ");
        String naicsCode = scanner.nextLine().trim();
        List<Business> businessesForNaicsCode = naicsMap.get(naicsCode);
        if (businessesForNaicsCode == null) {
            System.out.println("No businesses found for NAICS code " + naicsCode);
            return;
        }
        int numBusinesses = businessesForNaicsCode.size();
        Set<String> zipCodes = new HashSet<>();
        Set<String> neighborhoods = new HashSet<>();
        for (Business business : businessesForNaicsCode) {
            zipCodes.add(business.getZip());
            neighborhoods.add(business.getNeighborhood());
        }
       
        
        System.out.println("Total businesses: " + numBusinesses);
        System.out.println("Zip Codes: " + zipCodes.size());
        System.out.println("Neighborhood: " + neighborhoods.size());
    
    }
    /**
     * Checks if a business is closed based on its closure date.
     * Returns true if the business has a closure date and that date
     * is before today, indicating that the business is closed.
     * Returns false otherwise.
     *
     * @param business the business to check
     * @return true if the business is closed, false otherwise
     */
    private boolean isClosed(Business business) {

        LocalDate closureDate = business.getClosureDate();
        if (closureDate == null) {
            // Business has no closure date, so it is not closed
            return false;
        } else {
            // Business has a closure date, so it is closed if that date is before today's date
            LocalDate today = LocalDate.now();
            return closureDate.isBefore(today);
        }
}




    /**
     * Creates a map of business counts by NAICS code range and displays the summary.
     */
    private void generalSummary() {
        // Calculate total businesses and closed businesses
        int totalBusinesses = 0;
        int closedBusinesses = 0;
        for (List<Business> businesses : naicsMap.values()) {
            totalBusinesses += businesses.size();
            for (Business business : businesses) {
                if (isClosed(business)) {
                    closedBusinesses++;
                }
            }
        }

        // Calculate new businesses in the last year
        int newBusinesses = 0;
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        for (List<Business> businesses : naicsMap.values()) {
            for (Business business : businesses) {
                LocalDate startDate = business.getStartDate();
                if (startDate != null && startDate.isAfter(oneYearAgo)) {
                    newBusinesses++;
                }
            }
        }

        // Print summary
        System.out.println("Total Businesses: " + totalBusinesses);
        System.out.println("Closed Businesses: " + closedBusinesses);
        System.out.println("New Business in last year: " + newBusinesses);
}


    /**
     * Displays the queue of user commands entered so far.
     */
    private void history() {
       
        System.out.println("Command history");
        for (String command : commandHistory) {
            System.out.println(command);
        }
    }

    /**
     * Runs the program by reading user commands and executing them.
     */
    public void run() {

        //user uses these inuts
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();
            commandHistory.add(command);
            switch (command) {
                case "summary by zip":
                    summaryByZip();
                    break;
                case "summary by naics":
                    summaryByNaics();
                    break;
                case "general summary":
                    generalSummary();
                    break;
                case "history":
                    history();
                    break;
                case "quit":
                    return;
                default:
                    System.out.println("Command not recognized.");
                    break;
            }
        }
    }

    /**
     * Runs the BusinessAnalyzer program with the specified arguments.
     * @param args the command line arguments (filename and list implementation)
     */
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Usage: java BusinessAnalyzer <filename> <AL|LL>");
            System.exit(1);
        }

        String fileName = args[0];
        boolean useArrayList = args[1].equals("AL");
        BusinessAnalyzer analyzer = new BusinessAnalyzer(fileName, useArrayList);
        analyzer.run();
    }
}