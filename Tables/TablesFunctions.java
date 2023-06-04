package Tables;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class TablesFunctions {

    private static final Path path = Path.of("src/Tables/TablesFiles/tablesXXL.paed"); //path of the file

    /**
     * Reads the file and creates the map
     * @return the map
     */
    public static Map read() throws FileNotFoundException {
        File f = new File(String.valueOf(path));
        Scanner parameter = new Scanner(f);
        String name, date, tmp, y;
        int price, N, i;
        String[] all;
        N = parameter.nextInt();
        Map map = new Map(N);
        tmp = parameter.nextLine();

        for (i = 0; i < N; i++) {
            y = parameter.nextLine();
            all = y.split("[;]+");
            name = all[0];
            date = all[1];
            price = Integer.parseInt(all[2]);
            Business business = new Business(name, date, price);
            map.addBusiness(business);
        }

        return map;
    }

    /**
     * Ask for the information of the business to add
     * @param map the map
     */
    public static void addBusiness(Map map) {
        Scanner sc = new Scanner(System.in);
        String name, date;
        int price;
        System.out.print("Enter the name of the business to add:");
        name = sc.nextLine();
        System.out.print("Enter the day of the week they are interested in: ");
        date = sc.nextLine();
        System.out.print("Enter the price they are willing to pay, in euros:");
        price = sc.nextInt();
        Business business = new Business(name, date, price);
        long startTime = System.nanoTime();
        map.addBusiness(business); //add the business to the map
        System.out.println("\nProgram time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
        System.out.println("The business has been correctly added to the advertising management system.\n");
    }

    /**
     * Ask for the information of the business to delete
     * @param map the map
     */
    public static void removeBusiness(Map map) {
        Scanner sc = new Scanner(System.in);
        String name;
        System.out.print("Enter the name of the business to delete: ");
        name = sc.nextLine();
        long startTime = System.nanoTime();
        map.deleteBusiness(name); //delete the business from the map
        System.out.println("\nProgram time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
    }

    /**
     * Ask for the information of the business to check
     * @param map the map
     */
    public static void checkBusiness(Map map) {
        Scanner sc = new Scanner(System.in);
        String name;
        System.out.print("Enter the name of the business to query: ");
        name = sc.nextLine();
        long startTime = System.nanoTime();
        map.check(name); //check the business from the map
        System.out.println("\nProgram time: "+((System.nanoTime()- startTime)/1000000000 )+"s "+(((System.nanoTime()- startTime)/1000000 )%1000)+"ms "+(((System.nanoTime()- startTime)/1000 )%1000)+"us");
    }

    /**
     * Starts histogram
     * @param map the map
     */
    public static void showBusinessByDay(Map map) {
        System.out.println("Generating histogram...");
        map.BusinessByDay(); //show the histogram
    }

    /**
     * list all businesses
     * @param map the map
     */
    public static void list(Map map) {
        map.listBusinesses();
    }
}
