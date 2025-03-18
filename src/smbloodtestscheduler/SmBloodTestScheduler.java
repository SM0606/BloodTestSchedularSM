/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package smbloodtestscheduler;

import java.awt.Color;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import static smbloodtestscheduler.BloodTestSchedulerGUISM.CurrentPatientTA;

public class SmBloodTestScheduler {
    private static int currentPatientIndex = 0;
    // List to store all clients
    private static ArrayList<Person> clientList = new ArrayList<>();
    
    public static List<Person> noShowPatients = new ArrayList<>();
    
    public static Stack<Person> patientStack = new Stack<>();

    // List to store all patients
    private static ArrayList<Person> allPatients = new ArrayList<>();

    // NORMAL QUEUE (FIFO - First In First Out): 
    // Used to store patients waiting for their blood test in the order they arrived
    private static Queue<Person> queue = new LinkedList<>();

    // PRIORITY QUEUE: 
    // Orders patients based on urgency, age, and whether they are in the hospital
    private static PriorityQueue<Person> priorityQueue = new PriorityQueue<>();

    // LINKED LIST:
    // Stores the last 5 patients who did not show up for their appointment
    private static LinkedList<Person> noShowList = new LinkedList<>();

    // STACK:
    // Stores previous states of the normal queue for undo functionality
    private static Stack<Queue<Person>> history = new Stack<>();

    // ARRAY LIST: Stores all registered patients (this is used in various methods)
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Create a list of patient data to be added
        ArrayList<Person> newPatients = new ArrayList<>();
        newPatients.add(new Person("Alice", "Urgent", 65, false, "Dr. Smith"));
        newPatients.add(new Person("Bob", "Medium", 45, true, "Dr. Jones"));
        newPatients.add(new Person("Charlie", "Low", 30, false, "Dr. Lee"));
        newPatients.add(new Person("David", "Urgent", 70, true, "Dr. Brown"));
        newPatients.add(new Person("Emma", "Medium", 50, false, "Dr. Green"));
        newPatients.add(new Person("Frank", "Medium", 55, false, "Dr. Carter"));
        newPatients.add(new Person("Grace", "Low", 40, false, "Dr. Adams"));
        newPatients.add(new Person("Henry", "Urgent", 75, true, "Dr. Bennett"));
        newPatients.add(new Person("Ivy", "Medium", 60, true, "Dr. Thompson"));
        newPatients.add(new Person("Jack", "Low", 35, false, "Dr. Evans"));

        // Add all patients to the main list
        allPatients.addAll(newPatients);

        // Optionally: Display all patients in the console or GUI
        displayClients();

        // Start the GUI
        SwingUtilities.invokeLater(() -> {
            BloodTestSchedulerGUISM gui = new BloodTestSchedulerGUISM();
            gui.setVisible(true);
        });
    }

    static void addPerson(Person person) {
        // Collects values from the GUI components (text fields)
        String clientName = BloodTestSchedulerGUISM.NameTF.getText().trim();  // Client's name
        String clientAge = BloodTestSchedulerGUISM.AgeTF.getText().trim();    // Client's age
        String gpDetails = BloodTestSchedulerGUISM.GPDetailsTF.getText().trim(); // GP details
        String urgency = (String) BloodTestSchedulerGUISM.LevelCB.getSelectedItem(); // Urgency level
        String hospitalWard = (String) BloodTestSchedulerGUISM.TrueFalseCB.getSelectedItem(); // Hospital ward status

        // Converts the age to an integer (parses from text field)
        int age = -1;
        try {
            age = Integer.parseInt(clientAge); 
        } catch (NumberFormatException e) {
            // If age is not valid, show an error message
            JOptionPane.showMessageDialog(BloodTestSchedulerGUISM.AgeTF, "Age must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creates a new Person object with the provided data
        Person newPerson = new Person(clientName, urgency, age, hospitalWard.equals("Yes"), gpDetails);

        // Adds the new patient to the allPatients list
        allPatients.add(newPerson);
        noShowHistory.push(person);

        // Optionally updates the display or other actions here
        updateQueueDisplay(); // Updates the GUI display with latest patients
    }

    // NEW METHOD: Get a formatted list of all patients (for displaying in a text area)
    public static String getAllPatientsList() {
        StringBuilder sb = new StringBuilder();
        if (allPatients.isEmpty()) {
            return "No patients registered.";
        }

        for (Person p : allPatients) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

    // Updates the queue display in the GUI
    public static void updateQueueDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Person patient : allPatients) {
            sb.append(patient.toString()).append(" | ");
        }
        BloodTestSchedulerGUISM.QueueTa.setText(sb.toString());  // Updates the text area in the GUI
    }

 public static void displayClients() {
    // Define a PriorityQueue with a custom comparator
    PriorityQueue<Person> patientQueue = new PriorityQueue<>(new Comparator<Person>() {
        @Override
        public int compare(Person p1, Person p2) {
            // Compare urgency level
            Map<String, Integer> urgencyMap = Map.of("Urgent", 3, "Medium", 2, "Low", 1);
            int urgencyComparison = Integer.compare(urgencyMap.get(p2.getUrgency()), urgencyMap.get(p1.getUrgency()));
            if (urgencyComparison != 0) return urgencyComparison;

            // Compare hospital ward status (hospitalized first)
            int wardComparison = Boolean.compare(p2.isFromHospitalWard(), p1.isFromHospitalWard());
            if (wardComparison != 0) return wardComparison;

            // Compare age (older patients first)
            return Integer.compare(p2.getAge(), p1.getAge());
        }
    });

    // Add new patients to the queue
    patientQueue.add(new Person("Alice", "Urgent", 65, false, "Dr. Smith"));
    patientQueue.add(new Person("Bob", "Medium", 45, true, "Dr. Jones"));
    patientQueue.add(new Person("Charlie", "Low", 30, false, "Dr. Lee"));
    patientQueue.add(new Person("David", "Urgent", 70, true, "Dr. Brown"));
    patientQueue.add(new Person("Emma", "Medium", 50, false, "Dr. Green"));
    patientQueue.add(new Person("Frank", "Medium", 55, false, "Dr. Carter"));
    patientQueue.add(new Person("Grace", "Low", 40, false, "Dr. Adams"));
    patientQueue.add(new Person("Henry", "Urgent", 75, true, "Dr. Bennett"));
    patientQueue.add(new Person("Ivy", "Medium", 60, true, "Dr. Thompson"));
    patientQueue.add(new Person("Jack", "Low", 35, false, "Dr. Evans"));

    // Add all to the main patient list
    allPatients.addAll(patientQueue);

    // Build a string for displaying the patients in the queue
    StringBuilder sbQueue = new StringBuilder();
    if (patientQueue.isEmpty()) {
        sbQueue.append("No patients registered.");
    } else {
        while (!patientQueue.isEmpty()) {
            sbQueue.append(patientQueue.poll().toString()).append("\n");
        }
    }

    // Display the patients in the Queue JTextArea
    BloodTestSchedulerGUISM.QueueTa.setText(sbQueue.toString());

    // Now, display the no-show patients in the NoShowTA
    StringBuilder sbNoShows = new StringBuilder();
    if (BloodTestSchedulerGUISM.NoShowTA != null && !noShowPatients.isEmpty()) {
        for (Person noShow : noShowPatients) {
            sbNoShows.append(noShow.toString()).append(" has been marked as a no-show.\n");
        }
    } else {
        sbNoShows.append("No patients marked as no-show.");
    }

    // Update the NoShowTA text area to display the no-show patients
    BloodTestSchedulerGUISM.NoShowTA.setText(sbNoShows.toString());
}

public static String getNextPatient() {
    // Sort the patients by urgency, age (descending), and hospital ward status
    allPatients.sort((p1, p2) -> {
        // Prioritize Urgency: "Urgent" > "Medium" > "Low"
        int urgencyCompare = compareUrgency(p2.getUrgency(), p1.getUrgency()); // reversed for sorting
        if (urgencyCompare != 0) return urgencyCompare;

        // If urgency is the same, prioritize by age (older first)
        int ageCompare = Integer.compare(p2.getAge(), p1.getAge());
        if (ageCompare != 0) return ageCompare;

        // If age is also the same, prioritize by hospital ward (hospital patients first)
        return Boolean.compare(p2.isFromHospitalWard(), p1.isFromHospitalWard());
    });

    // Check if the list is not empty and currentPatientIndex is within bounds
    if (!allPatients.isEmpty()) {
        // Get the next patient in the list
        Person nextPatient = allPatients.get(currentPatientIndex);

        // Update the display
        BloodTestSchedulerGUISM.CurrentPatientTA.setText("Next patient: " + nextPatient.toString());

        // Move to the next patient
        currentPatientIndex = (currentPatientIndex + 1) % allPatients.size(); // Wrap around if we reach the end

        return nextPatient.toString();
    } else {
        BloodTestSchedulerGUISM.CurrentPatientTA.setText("No patients available.");
        return "No patients available.";
    }
}

    // Helper method to compare urgency levels
    private static int compareUrgency(String urgency1, String urgency2) {
        Map<String, Integer> urgencyMap = new HashMap<>();
        urgencyMap.put("Urgent", 3);
        urgencyMap.put("Medium", 2);
        urgencyMap.put("Low", 1);
        return Integer.compare(urgencyMap.get(urgency1), urgencyMap.get(urgency2));
    }

// Stack to keep track of no-show patients
private static Stack<Person> noShowHistory = new Stack<>();

public static void NoShows() {
    // Debugging to check values
    System.out.println("Current patient: " + BloodTestSchedulerGUISM.CurrentPatientTA.getText().trim());
    
    String currentPatientString = BloodTestSchedulerGUISM.CurrentPatientTA.getText().trim();

    if (currentPatientString.equals("No patients available.") || patientStack.isEmpty()) {
        JOptionPane.showMessageDialog(BloodTestSchedulerGUISM.CurrentPatientTA, 
                                      "No patients to mark as no-show.",
                                      "No-Show Warning",
                                      JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Remove the current patient from the stack (mark as no-show)
    Person noShowPatient = patientStack.pop();
    String patientName = noShowPatient.getName();

    // Remove the patient from the ArrayList
    for (int i = 0; i < allPatients.size(); i++) {
        if (allPatients.get(i).getName().equals(patientName)) {
            allPatients.remove(i);
            break;
        }
    }

    // Debugging: Confirm that patient has been removed
    System.out.println("Patient removed: " + patientName);

    // Update the NoShowTA text area to record the no-show
    String currentNoShows = BloodTestSchedulerGUISM.NoShowTA.getText();
    String updatedNoShows = currentNoShows + patientName + " has been marked as a no-show.\n";
    BloodTestSchedulerGUISM.NoShowTA.setText(updatedNoShows);

    // Display the next patient
    if (!patientStack.isEmpty()) {
        Person nextPatient = patientStack.peek();
        BloodTestSchedulerGUISM.CurrentPatientTA.setText("Next patient: " + nextPatient.getName() + " - " + nextPatient.getDetails());
    } else {
        BloodTestSchedulerGUISM.CurrentPatientTA.setText("No patients available.");
    }
}


}
