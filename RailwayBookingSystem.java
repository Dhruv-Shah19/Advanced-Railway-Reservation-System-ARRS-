import java.io.*;
import java.util.*;

class User {
    private String username;
    private String password;
    private List<Ticket> myBookings;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.myBookings = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Ticket> getMyBookings() {
        return myBookings;
    }

    // Method to save user data to a file
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt", true))) {
            writer.println(username + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load user data from a file
    public static List<User> loadFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0], parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}

class Ticket {
    private Train train;
    private int seatNumber;
    private String departureStation;
    private String arrivalStation;
    private Date journeyDate;
    private String passengerName;
    private String mobileNumber;

    public Ticket(Train train, int seatNumber, String departureStation, String arrivalStation, Date journeyDate, String passengerName, String mobileNumber) {
        this.train = train;
        this.seatNumber = seatNumber;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.journeyDate = journeyDate;
        this.passengerName = passengerName;
        this.mobileNumber = mobileNumber;
    }

    public Train getTrain() {
        return train;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public Date getJourneyDate() {
        return journeyDate;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

}

class Train {
    private String name;
    private List<Integer> availableSeats;
    private String departureStation;
    private String arrivalStation;
    private double fare;
    private double foodFare;
    private Date journeyDate;
    private String departureTime;
    private String ArrivalTime;

    public Train(String name, List<Integer> availableSeats, String departureStation, String arrivalStation, double fare, double foodFare, Date journeyDate, String departureTime, String ArrivalTime) {
        this.name = name;
        this.availableSeats = new ArrayList<>(availableSeats);
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.fare = fare;
        this.foodFare = foodFare;
        this.journeyDate = journeyDate;
        this.departureTime = departureTime;
        this.ArrivalTime = ArrivalTime;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getAvailableSeats() {
        return availableSeats;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public double getFare() {
        return fare;
    }

    public double getFoodFare() {
        return foodFare;
    }

    public Date getJourneyDate() {
        return journeyDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }
}

public class RailwayBookingSystem {
    private static List<Train> availableTrains = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;
    private static List<User> users = User.loadFromFile(); // Load existing users from file
    
    public static void main(String[] args) {
        initializeAvailableTickets();
        System.out.println("\nWelcome to ARRS: Advanced Railway Reservation System!\n");
        while (true) {
            if (loggedInUser == null) {

                System.out.println("1. Sign Up");
                System.out.println("2. Log In");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        logIn();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } else {
                System.out.println("\nWelcome, " + loggedInUser.getUsername() + "!");
                System.out.println("1. Book Ticket");
                System.out.println("2. View My Bookings");
                System.out.println("3. Cancel Booking");
                System.out.println("4. View Available Trains");
                System.out.println("5. Search Trains by Source and Destination");
                System.out.println("6. Change Password");
                System.out.println("7. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        bookTicket();
                        break;
                    case 2:
                        viewMyBookings();
                        break;
                    case 3:
                        cancelBooking();
                        break;
                    case 4:
                        viewAvailableTrains();
                        break;
                    case 5:
                        searchTrainsBySourceAndDestination();
                        break;
                    case 6:
                        updateUserProfile();
                        break;
                    case 7:
                        loggedInUser = null;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        }
    }

    private static void initializeAvailableTickets() {
        // Adding available trains with their seat numbers, departure, arrival stations, and departure times
        Train shatabdiExpress = new Train("Shatabdi Express", Arrays.asList(1, 2, 4, 5, 6, 9), "New Delhi", "Mumbai Central", 1500.00, 200.00, new Date(), "09:00", "12:00");
        Train rajdhaniExpress = new Train("Rajdhani Express", Arrays.asList(1, 2, 3, 4, 5), "Kolkata", "New Delhi", 2000.00, 250.00, new Date(), "10:30", "14:30");
        Train durontoExpress = new Train("Duronto Express", Arrays.asList(1, 2, 3, 5, 8, 10), "Chennai Central", "Bangalore City", 1200.00, 150.00, new Date(), "12:00", "15:45");
        Train garibRath = new Train("Garib Rath", Arrays.asList(1, 2, 3, 4, 5), "New Delhi", "Mumbai Central", 1000.00, 120.00, new Date(), "14:30","18:00");
        Train purushottamExpress = new Train("Purushottam Express", Arrays.asList(1, 2, 3, 5, 8, 10), "Puri", "New Delhi", 1800.00, 220.00, new Date(), "16:00","20:15");
        Train humsafarExpress = new Train("Humsafar Express", Arrays.asList(1, 2, 3, 5, 8, 10), "Ahmedabad", "Mumbai Central", 900.00, 100.00, new Date(), "18:00","21:03");
        Train kolkataMail = new Train("Kolkata Mail", Arrays.asList(1, 2, 3, 4, 5), "Kolkata", "Mumbai Central", 1200.00, 150.00, new Date(), "19:30","22:00");
        Train suratExpress = new Train("Surat Express", Arrays.asList(1, 2, 3, 5, 8, 10), "Surat", "Mumbai Central", 800.00, 100.00, new Date(), "08:30", "12:00");
        Train mumbaiAhmedabadExpress = new Train("Mumbai Ahmedabad Express", Arrays.asList(1, 2, 3, 4, 5), "Mumbai Central", "Ahmedabad", 1100.00, 150.00, new Date(), "11:00", "14:35");
        Train hyderabadExpress = new Train("Hyderabad Express", Arrays.asList(1, 2, 3, 5, 6), "Hyderabad", "Mumbai Central", 1300.00, 200.00, new Date(), "13:45","16:34");
        Train suratAhmedabadExpress = new Train("Surat Ahmedabad Express", Arrays.asList(1, 2, 3, 4, 5), "Surat", "Ahmedabad", 700.00, 80.00, new Date(), "07:15","12:00");

        availableTrains.add(shatabdiExpress);
        availableTrains.add(rajdhaniExpress);
        availableTrains.add(durontoExpress);
        availableTrains.add(garibRath);
        availableTrains.add(purushottamExpress);
        availableTrains.add(humsafarExpress);
        availableTrains.add(kolkataMail);
        availableTrains.add(suratExpress);
        availableTrains.add(mumbaiAhmedabadExpress);
        availableTrains.add(hyderabadExpress);
        availableTrains.add(suratAhmedabadExpress);
    }

    private static void signUp() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please choose a different one.");
                return;
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        newUser.saveToFile(); // Save new user to file
        System.out.println("Account created successfully.");
    }

    private static void logIn() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        boolean loggedIn = false;

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful.");
                loggedIn = true;
                break;
            }
        }

        if (!loggedIn) {
            System.out.println("Invalid username or password.");
        }
    }

    private static void bookTicket() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        System.out.print("Enter source station: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination station: ");
        String destination = scanner.nextLine();

        System.out.println("Available Trains for " + source + " to " + destination + ":");
        List<Train> availableTrainsForRoute = getTrainsForRoute(source, destination);
        for (Train train : availableTrainsForRoute) {
            System.out.println(train.getName() + " (" + train.getDepartureStation() + " to " + train.getArrivalStation()
                    + ") - Departure Time: " + train.getDepartureTime() + ", Arrival Time: " + train.getArrivalTime());
        }

        if (availableTrainsForRoute.isEmpty()) {
            System.out.println("No available trains for the selected route.");
            return;
        }

        System.out.print("Enter train name to book: ");
        String trainName = scanner.nextLine();
        Train selectedTrain = null;
        for (Train train : availableTrainsForRoute) {
            if (train.getName().equals(trainName)) {
                selectedTrain = train;
                break;
            }
        }

        if (selectedTrain == null) {
            System.out.println("Invalid train name.");
            return;
        }

        // Ask how many tickets to book
        System.out.print("How many tickets do you want to book? ");
        int numTickets = Integer.parseInt(scanner.nextLine());

        // Check if the number of tickets requested is greater than the available seats
        if (numTickets > selectedTrain.getAvailableSeats().size()) {
            System.out.println("Not enough available seats. Available seats: " + selectedTrain.getAvailableSeats().size());
            return;
        }

        List<Ticket> bookedTickets = new ArrayList<>();
        double totalFare = 0;
        String addFoodChoice = "no";

    for (int i = 0; i < numTickets; i++) {
        System.out.println("Ticket " + (i + 1) + ":");
        System.out.print("Enter passenger name: ");
        String passengerName = scanner.nextLine();
        String mobileNumber;
        while (true) {
            System.out.print("Enter passenger mobile number: ");
            mobileNumber = scanner.nextLine();
            if (mobileNumber.length() == 10 && mobileNumber.charAt(0) != '0' && mobileNumber.matches("[0-9]+")) {
                break;
            } else {
                System.out.println(
                        "Invalid mobile number.");
                continue;
            }
        }

        // Display seat map
        System.out.println("Seat Map for " + selectedTrain.getName() + ":");
        displaySeatMap(selectedTrain);

        int seatNumber;
        while (true) {
            System.out.print("Enter seat number to book (0 to cancel): ");
            try {
                seatNumber = Integer.parseInt(scanner.nextLine());
                if (seatNumber == 0) {
                    System.out.println("Booking cancelled.");
                    return;
                }
                if (!selectedTrain.getAvailableSeats().contains(seatNumber)) {
                    System.out.println("Invalid seat number. Please choose from the available seats.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid seat number.");
            }
        }

        // Calculate fare for this ticket
        double fare = selectedTrain.getFare();
        double foodFare = selectedTrain.getFoodFare();
        double ticketFare = fare;
        System.out.print("Do you want to add food to the ticket? (yes/no): ");
        addFoodChoice = scanner.nextLine().toLowerCase();
        if (addFoodChoice.equals("yes")) {
            ticketFare += foodFare;
            System.out.println("Food fare added: Rs" + foodFare);
        }

        // Accumulate total fare
        totalFare += ticketFare;
        System.out.println("Total fare for Ticket " + (i + 1) + ": Rs" + ticketFare);

        // Create and add ticket to bookedTickets list
        Ticket bookedTicket = new Ticket(selectedTrain, seatNumber, source, destination, new Date(), passengerName, mobileNumber);
        bookedTickets.add(bookedTicket);

        // Remove the booked seat from availableSeats of the selected train
        selectedTrain.getAvailableSeats().remove(Integer.valueOf(seatNumber));
    }

    // Payment options
    System.out.println("Please select payment method:");
    System.out.println("1. Net Banking");
    System.out.println("2. UPI");
    System.out.println("3. Credit Card");
    System.out.print("Enter your choice: ");
    int paymentOption = Integer.parseInt(scanner.nextLine());

    // Perform payment
    boolean paymentSuccessful = performPayment(totalFare, paymentOption);

    if (paymentSuccessful) {
        // Book the tickets
        loggedInUser.getMyBookings().addAll(bookedTickets);

        System.out.println("Tickets booked successfully. Your tickets are confirmed.");
        // Print ticket details
        System.out.println("\nTicket Details:");
        for (Ticket bookedTicket : bookedTickets) {
            System.out.println("Train: " + bookedTicket.getTrain().getName());
            System.out.println("Departure Station: " + bookedTicket.getDepartureStation());
            System.out.println("Arrival Station: " + bookedTicket.getArrivalStation());
            System.out.println("Departure Time: " + bookedTicket.getTrain().getDepartureTime());
            System.out.println("Arrival Time: " + bookedTicket.getTrain().getArrivalTime());
            System.out.println("Seat Number: " + bookedTicket.getSeatNumber());
            System.out.println("Passenger Name: " + bookedTicket.getPassengerName());
            System.out.println("Passenger Mobile Number: " + bookedTicket.getMobileNumber());
            System.out.println("Journey Date: " + bookedTicket.getJourneyDate());
        }
        System.out.println("Total Fare Paid: Rs" + totalFare);
    } else {
        System.out.println("Payment failed. Ticket booking unsuccessful.");
    }
}


    private static void displaySeatMap(Train train) {
        List<Integer> seatNumbers = train.getAvailableSeats();
        int rows = 5; // Number of rows
        int columns = 15; // Number of columns

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                int seatNumber = (i - 1) * columns + j;
                if (seatNumbers.contains(seatNumber)) {
                    System.out.printf("[%d]  ", seatNumber); // Available seat
                } else {
                    System.out.print("[X]  "); // Booked seat
                }
            }
            System.out.println(); // Move to the next row
        }
        System.out.println();
    }


    private static boolean performPayment(double amount, int paymentOption) {
        // Logic to perform payment
        // Return true if payment is successful, false otherwise
        // For simplicity, let's assume payment is always successful in this example
        return true;
    }


    private static void cancelBooking() {
        // Cancellation code...
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<Ticket> myBookings = loggedInUser.getMyBookings();
        if (myBookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        System.out.println("Your Bookings:");
        for (int i = 0; i < myBookings.size(); i++) {
            Ticket ticket = myBookings.get(i);
            System.out.println((i + 1) + ". Train: " + ticket.getTrain().getName() + ", Journey Date: " + ticket.getJourneyDate() + ", Seat Number: " + ticket.getSeatNumber());
        }

        System.out.print("Enter the number of the ticket to cancel: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice < 1 || choice > myBookings.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Ticket canceledTicket = myBookings.remove(choice - 1);
        Train canceledTrain = canceledTicket.getTrain();
        canceledTrain.getAvailableSeats().add(canceledTicket.getSeatNumber());
        System.out.println("Ticket canceled successfully.");
    }

    private static void viewAvailableTrains() {
        System.out.println("Available Trains:");
        System.out.println("+---------------------------------------+------------------+------------------+-------------------------------------+");
        System.out.println("| Train Name                            | Departure        | Arrival          | Departure Time   | Arrival Time     |");
        System.out.println("+---------------------------------------+------------------+------------------+-------------------------------------+");
        for (Train train : availableTrains) {
            System.out.printf("| %-37s | %-16s | %-16s | %-16s | %-16s |\n", train.getName(), train.getDepartureStation(), train.getArrivalStation(), train.getDepartureTime(),train.getArrivalTime());
        }
        System.out.println("+---------------------------------------+------------------+------------------+-------------------------------------+");
    }

    private static void searchTrainsBySourceAndDestination() {
        System.out.print("Enter source station: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination station: ");
        String destination = scanner.nextLine();

        List<Train> trainsForRoute = getTrainsForRoute(source, destination);
        if (trainsForRoute.isEmpty()) {
            System.out.println("No available trains for the selected route.");
            return;
        }

        System.out.println("Available Trains for " + source + " to " + destination + ":");
        System.out.println("+---------------------------------------+------------------+------------------+-------------------------------------+");
        System.out.println("| Train Name                            | Departure        | Arrival          | Departure Time   | Arrival Time     |");
        System.out.println("+---------------------------------------+------------------+------------------+-------------------------------------+");
        for (Train train : trainsForRoute) {
            System.out.printf("| %-37s | %-16s | %-16s | %-16s | %-16s |\n", train.getName(), train.getDepartureStation(), train.getArrivalStation(), train.getDepartureTime(),train.getArrivalTime());
        }
        System.out.println("+---------------------------------------+------------------+------------------+-------------------------------------+");
    }

    private static void updateUserProfile() {
        // Update user profile code...
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();
        if (!loggedInUser.getPassword().equals(currentPassword)) {
            System.out.println("Incorrect password.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        loggedInUser.setPassword(newPassword);
        System.out.println("Password updated successfully.");
    }

    private static List<Train> getTrainsForRoute(String source, String destination) {
        List<Train> trainsForRoute = new ArrayList<>();
        for (Train train : availableTrains) {
            if (train.getDepartureStation().equalsIgnoreCase(source) && train.getArrivalStation().equalsIgnoreCase(destination)) {
                trainsForRoute.add(train);
            }
        }
        return trainsForRoute;
    }

    private static void viewMyBookings() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }
        List<Ticket> myBookings = loggedInUser.getMyBookings();
        if (myBookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("Your Bookings:");
            for (Ticket ticket : myBookings) {
                Train train = ticket.getTrain();
                System.out.println("Train: " + train.getName());
                System.out.println("Departure Station: " + train.getDepartureStation());
                System.out.println("Arrival Station: " + train.getArrivalStation());
                System.out.println("Departure Time: " + train.getDepartureTime());
                System.out.println("Seat Number: " + ticket.getSeatNumber());
                System.out.println("Journey Date: " + ticket.getJourneyDate());
                System.out.println();
            }
        }
    }
}
