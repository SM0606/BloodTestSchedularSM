/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package smbloodtestscheduler;


import java.util.*;

class Person implements Comparable<Person> {
    String name;
    String priority;
    int age;
    boolean fromHospitalWard;
    String gpDetails;

    public Person(String name, String priority, int age, boolean fromHospitalWard, String gpDetails) {
        this.name = name;
        this.priority = priority;
        this.age = age;
        this.fromHospitalWard = fromHospitalWard;
        this.gpDetails = gpDetails;
    }

    @Override
    public int compareTo(Person other) {
        int priorityCompare = getPriorityValue(this.priority) - getPriorityValue(other.priority);
        if (priorityCompare != 0) return -priorityCompare;

        if (this.fromHospitalWard && !other.fromHospitalWard) return -1;
        if (!this.fromHospitalWard && other.fromHospitalWard) return 1;

        return Integer.compare(other.age, this.age); // Older patients first
    }

    private int getPriorityValue(String priority) {
        switch (priority.toLowerCase()) {
            case "urgent": return 3;
            case "medium": return 2;
            case "low": return 1;
            default: return 0;
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Priority: " + priority + ", Age: " + age + 
               ", From Hospital: " + (fromHospitalWard ? "Yes" : "No") + ", GP: " + gpDetails;
    }
}

public class SmBloodTestScheduler {
    // NORMAL QUEUE (FIFO - First In First Out)
    private static Queue<Person> queue = new LinkedList<>();

    // PRIORITY QUEUE (Orders patients based on priority, age, and hospital status)
    private static PriorityQueue<Person> priorityQueue = new PriorityQueue<>();

    // LINKED LIST (Stores last 5 no-shows)
    private static LinkedList<Person> noShowList = new LinkedList<>();

    // STACK (Undo functionality - stores previous states of the normal queue)
    private static Stack<Queue<Person>> history = new Stack<>();

    // ARRAY LIST (Stores all registered patients)
    private static List<Person> allPatients = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {//adding people
        addPerson(new Person("Alice", "Urgent", 65, false, "Dr. Smith"));
        addPerson(new Person("Bob", "Medium", 45, true, "Dr. Jones"));
        addPerson(new Person("Charlie", "Low", 30, false, "Dr. Lee"));
        addPerson(new Person("David", "Urgent", 70, true, "Dr. Brown"));
        addPerson(new Person("Emma", "Medium", 50, false, "Dr. Green"));
        addPerson(new Person("Frank", "Medium", 55, false, "Dr. Carter"));
        addPerson(new Person("Grace", "Low", 40, false, "Dr. Adams"));
        addPerson(new Person("Henry", "Urgent", 75, true, "Dr. Bennett"));
        addPerson(new Person("Ivy", "Medium", 60, true, "Dr. Thompson"));
        addPerson(new Person("Jack", "Low", 35, false, "Dr. Evans"));

        System.out.println("Initial Queue:");
        printQueue(queue);

        for (int i = 0; i < 3; i++) {
            processNextPatient();
        }

        System.out.println("\nUpdated Normal Queue:");
        printQueue(queue);

        System.out.println("\nUpdated Priority Queue:");
        printQueue(new LinkedList<>(priorityQueue));

        System.out.println("\nLast 5 No-Shows:");
        printQueue(noShowList);

        System.out.println("\nAll Registered Patients:");
        printQueue(allPatients);
    }

    // Add a person to all relevant queues and lists
    private static void addPerson(Person person) {
        queue.add(person);  // Normal queue
        priorityQueue.add(person);  // Priority queue
        allPatients.add(person);  // ArrayList of all patients
        saveState();  // Save the state in stack for undo
    }

    // Process the next patient
    private static void processNextPatient() {
        if (priorityQueue.isEmpty()) {
            System.out.println("\nNo clients in the queue.");
            return;
        }

        Person nextPerson = priorityQueue.poll();  // Remove from priority queue
        queue.remove(nextPerson);  // Remove from normal queue

        System.out.println("\nNext patient to be seen: " + nextPerson);
        System.out.print("Was the patient seen? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("no")) {
            queue.add(nextPerson);  // Re-add to normal queue (back of the line)
            priorityQueue.add(nextPerson);  // Re-add to priority queue
            addNoShow(nextPerson);  // Store in no-show list
            System.out.println(nextPerson.name + " marked as a no-show and moved to the bottom of the list.");
        } else {
            System.out.println(nextPerson.name + " has been seen and removed from the list.");
        }
    }

    // Store last 5 no-shows
    private static void addNoShow(Person person) {
        if (noShowList.size() >= 5) {
            noShowList.removeFirst();  // Keep only the last 5 no-shows
        }
        noShowList.add(person);
    }

    // Save queue state (for undo functionality)
    private static void saveState() {
        history.push(new LinkedList<>(queue));  // Save a copy of the queue
    }

    // Undo last action
    private static void undo() {
        if (!history.isEmpty()) {
            queue = history.pop();  // Restore the last saved queue state
            System.out.println("\nLast action undone.");
        } else {
            System.out.println("\nNo previous state to undo.");
        }
    }

    // Print a queue
    private static void printQueue(Collection<Person> queue) {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            for (Person p : queue) {
                System.out.println(p);
            }
        }
    }
}


