package Tables;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Map {
    private final NodeHash[] buckets; //buckets of the hash table
    private final int numBuckets; //number of buckets
    private final long[] count; //number of elements per day
    private final long[] avgBusinessPrice; //average price per day

    /**
     * Constructor Map
     * @param numBuckets number of buckets
     */
    public Map(int numBuckets) {
        this.numBuckets = numBuckets;
        buckets = new NodeHash[this.numBuckets]; //initialize the buckets
        this.count = new long[7];
        this.avgBusinessPrice = new long[7];
        Arrays.fill(this.count, 0); //initialize count to 0 for each day
        Arrays.fill(this.avgBusinessPrice, 0); //initialize avgBusinessPrice to 0 for each day
    }


    /**
     * Get the hashCode of the element
     * @param key String name of the business to create the hash code
     * @return hashCode of the element
     */
    int hashCode(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) { //for each character of the key
            hash += (key.charAt(i) * i); //add the character to the hash * position
        }
        return hash % numBuckets;
    }

    /**
     * Add a business to the hash table
     * @param business Business to add
     */
    public void addBusiness(Business business) {
        int hash = hashCode(business.getName()); //get the hashCode of the business
        int position = getPositionDay(business.getDate()); //get the position of the day
        count[position]++; //increment the number of businesses for this day
        avgBusinessPrice[position] += business.getPrice(); //increment the average price for this day
        NodeHash node = buckets[hash];
        if (node == null) { //if the bucket is empty
            buckets[hash] = new NodeHash(business, hash); //add the node
        }
        else { //if the bucket is not empty
            node.add(); //add the node to the next nodes of this position of the array
            node.next = new NodeHash(business, hash); //add to the empty position
        }
    }

    /**
     * List all the businesses of the hash tables
     */
    public void listBusinesses() {
        for (NodeHash node : buckets) {
            while (node != null) {
                System.out.println(node.business.getName());
                node = node.next;
            }
        }
    }

    /**
     * Delete a business from the hash table
     * @param businessName name of the business to delete
     */
    public void deleteBusiness(String businessName) {
        int hash = hashCode(businessName); //get the hashCode of the business
        boolean found = false; //boolean to know if the business was found
        NodeHash node = buckets[hash];
        if (node != null) { //if the bucket is not empty
            if (node.business.getName().equals(businessName)) { //if the business is the first node
                found = true; //set found to true
                buckets[hash] = node.next; //set the next node as the first node
            }
            else { //if the business is not the first node
                found = node.delete(businessName); //delete the business
                buckets[hash] = node;
            }
        }
        if (found) { //if the business was found
            System.out.print("The business has been correctly deleted from the advertising management system\n");
            count[getPositionDay(node.business.getDate())]--;
            avgBusinessPrice[getPositionDay(node.business.getDate())] -= node.business.getPrice();
        }else { //if the business was not found
            System.out.println("Business not found");
        }
    }

    /**
     * Display a specific business
     * @param businessName name of the business to display
     */
    public void check(String businessName) {
        int hash = hashCode(businessName); //get the hashCode of the business
        NodeHash node = buckets[hash];
        boolean found = false;
        if (node != null) { //if the bucket is not empty
            if (node.business.getName().equals(businessName)) { //if the business is the first node
                found = true; //set found to true
            }
            else { //if the business is not the first node
                found = node.findNode(businessName); //find the business
            }
        }
        if (!found) { //if the business was not found
            System.out.println("This business does not exist");
        } else { //if the business was found
            displayBusiness(node.business);
        }
    }

    /**
     * Get the position of the day
     * @param date date of the business
     * @return integer showing the position of the day
     */
    public int getPositionDay(String date) {
        return switch (date) {
            case "Monday" -> 0;
            case "Tuesday" -> 1;
            case "Wednesday" -> 2;
            case "Thursday" -> 3;
            case "Friday" -> 4;
            case "Saturday" -> 5;
            case "Sunday" -> 6;
            default -> -1;
        };
    }

    /**
     * Display Histogram of all the Businesses of the HashTable
     */
    public void BusinessByDay() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        JFrame frame = new JFrame(); //create a new frame
        frame.setSize(350, 300); //size
        frame.getContentPane().add(new HistogramRepresentation(days, count, avgBusinessPrice)); //add the histogram
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); //display the frame
    }

    /**
     * Display a specific business
     * @param business Business to display
     */
    public void displayBusiness(Business business) {
        System.out.println("\nName: " + business.getName());
        System.out.println("Day: " + business.getDate());
        System.out.println("Price: " + business.getPrice());
    }
}
