package Tables;

public class NodeHash {
    Business business; //business of the node
    int key; //useless
    NodeHash next; //next node of the hash table

    /**
     * Constructor of the class NodeHash
     * @param business business of the node
     */
    public NodeHash(Business business, int key) {
        this.business = business;
        this.key = key;
    }

    /**
     * Add a business to the hash table
     */
    public void add() {
        if (next!=null) { //if there is a next node
            next.add();
        }
    }

    /**
     * Delete a business from the hash table
     * @param businessName name of the business to delete
     * @return true if the business was deleted, false otherwise
     */
    public boolean delete(String businessName) {
        if (next != null) { //if there is a next node
            if (next.business.getName().equals(businessName)) { //if the next node is the business to delete
                next = next.next;
                return true; //return true
            }
            else {
                return next.delete(businessName); //try to find the business in the next node
            }
        } else {
            return false; //not found
        }
    }

    /**
     * Find a business in the hash table
     * @param businessName name of the business to find
     * @return true if the business was found, false otherwise
     */
    public boolean findNode(String businessName) {
        if (next != null) { //if there is a next node
            if (next.business.getName().equals(businessName)) { //if the next node is the business to find
                return true; //return true
            }
            else {
                return next.findNode(businessName); //try to find the business in the next node
            }
        } else {
            return false; //not found
        }
    }

}
