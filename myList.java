/**
 * This is a program that is meant to take in a list of 5 numbers
 * it then outputs the list, and then reverses, and outputs that list
 * for my Individual Activity #3 Assignment
 * @author Jacob Smith
 */

public class myList {
    private final int size = 5;
    private Node head;

    private class Node {
        private Node next;
        private Node prev;
        private Object data;

        public Node(Object data) {
            this.data = data;
            this.next = this.prev = null;
        }
    }

    /**
     * Adds a new number to the front of a list
     */
    public void addAtFront(Object key) {
        Node newNode = new Node(key);

        if (head != null) {
            newNode.next = head;
            head.prev = newNode;
        }

        head = newNode;
    }

    /**
     * Revereses the order of a list
     */
    public void reverse() {
        Node current = head;
        Node temp = null;

        while (current != null) {
            
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.prev;
        }

        if (temp != null) {
            head = temp.prev;
        }
    }

    // Print the list
    public void printList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    /**
     * Populates a list, then outputs the list,
     * and then outputs it in reverse.
     */
    public static void main(String args[]) {

        myList list = new myList();

        // Populates list
        for (int i = 1; i <= list.size; i++) {
            list.addAtFront(i);
        }

        System.out.println("Original List:");
        list.printList();

        list.reverse();

        System.out.println("Reversed List:");
        list.printList();
    }
}