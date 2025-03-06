import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

// Patient Class
class Patient {
    private String id;
    private String name;
    private int age;
    private String disease;
    private String medicalHistory;

    public Patient(String id, String name, int age, String disease, String medicalHistory) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.medicalHistory = medicalHistory;
    }

    public String getId() {
        return id;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name + ", Age: " + age + ", Disease: " + disease +
               "\n Medical History: " + medicalHistory;
    }
}

// Appointment Class
class Appointment {
    private String patientId;
    private String doctorName;
    private String date;

    public Appointment(String patientId, String doctorName, String date) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment for Patient ID: " + patientId + " with " + doctorName + " on " + date;
    }
}

// Bill Class (Invoicing)
class Bill {
    private String patientId;
    private List<String> services;
    private List<Double> costs;

    public Bill(String patientId) {
        this.patientId = patientId;
        this.services = new ArrayList<>();
        this.costs = new ArrayList<>();
    }

    public void addService(String service, double cost) {
        services.add(service);
        costs.add(cost);
    }

    public double getTotalAmount() {
        return costs.stream().mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public String toString() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("\n Invoice for Patient ID: ").append(patientId).append("\n");
        for (int i = 0; i < services.size(); i++) {
            invoice.append(" - ").append(services.get(i)).append(": $").append(costs.get(i)).append("\n");
        }
        invoice.append(" Total Amount: $").append(getTotalAmount());
        return invoice.toString();
    }
}

// Inventory Class
class Inventory {
    private String itemName;
    private int quantity;

    public Inventory(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item: " + itemName + ", Quantity: " + quantity;
    }
}

// Staff Class
class Staff {
    private String id;
    private String name;
    private String role;

    public Staff(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Staff ID: " + id + ", Name: " + name + ", Role: " + role;
    }
}

// Main Class
public class HospitalManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<Patient> patients = new ArrayList<>();
    static List<Appointment> appointments = new ArrayList<>();
    static List<Bill> bills = new ArrayList<>();
    static List<Inventory> inventoryList = new ArrayList<>();
    static List<Staff> staffList = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Patient");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Generate Invoice");
            System.out.println("4. Add Inventory Item");
            System.out.println("5. Add Staff");
            System.out.println("6. View Patients (With EHR)");
            System.out.println("7. View Appointments");
            System.out.println("8. View Bills");
            System.out.println("9. View Inventory");
            System.out.println("10. View Staff");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    scheduleAppointment();
                    break;
                case 3:
                    generateInvoice();
                    break;
                case 4:
                    addInventoryItem();
                    break;
                case 5:
                    addStaff();
                    break;
                case 6:
                    viewPatients();
                    break;
                case 7:
                    viewAppointments();
                    break;
                case 8:
                    viewBills();
                    break;
                case 9:
                    viewInventory();
                    break;
                case 10:
                    viewStaff();
                    break;
                case 11:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println(" Invalid choice! Please enter a number between 1-11.");
            }
        }
    }

    public static void registerPatient() {
        String patientId;

        while (true) {
            System.out.print("Enter patient ID (Format: P001, P1001, etc.): ");
            patientId = scanner.nextLine().trim();

            if (!Pattern.matches("P\\d+", patientId)) {
                System.out.println(" Error: Invalid format! Use 'P' followed by numbers (e.g., P001, P1001).");
                continue;
            }

            break;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter disease: ");
        String disease = scanner.nextLine();
        System.out.print("Enter Medical History (EHR): ");
        String medicalHistory = scanner.nextLine();

        patients.add(new Patient(patientId, name, age, disease, medicalHistory));
        System.out.println("Patient registered successfully!");
    }

    public static void scheduleAppointment() {
        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();
        System.out.print("Enter doctor name: ");
        String doctorName = scanner.nextLine();

        LocalDate appointmentDate = null;
        while (appointmentDate == null) {
            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();

            try {
                appointmentDate = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
                if (appointmentDate.isBefore(LocalDate.now())) {
                    System.out.println("Error: Appointment date cannot be in the past.");
                    appointmentDate = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format! Please use YYYY-MM-DD.");
            }
        }

        appointments.add(new Appointment(patientId, doctorName, appointmentDate.toString()));
        System.out.println("Appointment scheduled successfully!");
    }

    public static void generateInvoice() {
        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();
        Bill bill = new Bill(patientId);

        while (true) {
            System.out.print("Enter service (or 'done' to finish): ");
            String service = scanner.nextLine();
            if (service.equalsIgnoreCase("done")) break;
            System.out.print("Enter cost: ");
            double cost = scanner.nextDouble();
            scanner.nextLine();

            bill.addService(service, cost);
        }

        bills.add(bill);
        System.out.println("Invoice generated successfully!");
    }

    public static void addInventoryItem() {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        inventoryList.add(new Inventory(itemName, quantity));
        System.out.println("Inventory item added successfully!");
    }

    public static void addStaff() {
        System.out.print("Enter staff ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();

        staffList.add(new Staff(id, name, role));
        System.out.println("Staff added successfully!");
    }

    public static void viewPatients() { patients.forEach(System.out::println); }
    public static void viewAppointments() { appointments.forEach(System.out::println); }
    public static void viewBills() { bills.forEach(System.out::println); }
    public static void viewInventory() { inventoryList.forEach(System.out::println); }
    public static void viewStaff() { staffList.forEach(System.out::println); }
}
