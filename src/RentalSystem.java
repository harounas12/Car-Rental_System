import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class RentalSystem {
	private CarInventory inventory;
	private Scanner scanner;
	private CsvCrud csvHandler;
	private final String FILE_NAME = "inventory2.csv";
	
	public RentalSystem() {
		inventory = new CarInventory();
		scanner = new Scanner(System.in);
		csvHandler = new CsvCrud(FILE_NAME);
		List<Car> loadedCars = csvHandler.loadCars();
        if (loadedCars.isEmpty()) {
            Data(); 
            csvHandler.saveCars(inventory.getAvailableCars()); // Save initial data
        } else {
            for (Car c : loadedCars) {
            	c.markAvailable();
            	inventory.addCar(c);
            }
        }
    }
	public void Data() {
		inventory.addCar(new ElectricCar("Tesla", "Model Y", "E01", 100, 100));
        inventory.addCar(new GasCar("Toyota", "Camry", "G01", 80, 2000));
        inventory.addCar(new GasCar("Audi", "RS6", "G02", 150, 4000));
	
	}
	public void start() {
		boolean running = true;
		while(running) {
			displayMenu();
			int choice = getIntInput("Make a choice: ");
			
			switch(choice) {
			case 1 -> showAvailableCars();
			case 2 -> handleRental();
			case 3 -> handleReturn();
			case 4 -> showRentalHistory();
			case 5 -> {
				System.out.println("Exiting System. Goodbye!");
				csvHandler.saveCars(inventory.getAvailableCars());
				running = false;
			}
			default -> System.out.println("Invalid choice. Try again.");
			}
		
		}
	}
	private void displayMenu() {
        System.out.println("\n----- CAR RENTAL SYSTEM -----");
        System.out.println("1. View Available Cars");
        System.out.println("2. Rent a Car (Daily or Hourly)");
        System.out.println("3. Return a Car");
        System.out.println("4. Show Rental History");
        System.out.println("5. Exit");
    }
	public void showAvailableCars() {
		List<Car> available = inventory.getAvailableCars();
		if(available.isEmpty()) {
			System.out.println("No cars currently available.");
		}else {
			System.out.println("\n--- Available Inventory ---");
			for(Car car: available) {
				System.out.println("[" + car.getid() + "] " + car.brand + " " + car.model  );
				
            }
			}
		}
	private void handleRental() {
        System.out.print("Enter Car ID: ");
        String id = scanner.nextLine();
        Car selected = inventory.getCarbyId(id);

        if (selected != null) {
        	System.out.println("Select Rental type:");
        	System.out.println("1. Daily Rental");
        	System.out.println("2. Hourly Rental");
        	int typechoice = getIntInput("Choice: ");
        	boolean isHourly = (typechoice == 2);
        	String unitlabel = isHourly ? "Hours" : "Days";
            System.out.print("Customer Name: ");
            String name = scanner.nextLine();
            int idNum = getIntInput("Customer ID: ");
            int duration = getIntInput("Enter Rental duration in " + unitlabel + ": ");

            try {
                Customer customer = new Customer(name,idNum);
                Rental rental = new Rental(selected, customer, duration, isHourly);
                selected.markUnavailable();
                inventory.addRental(rental);
                csvHandler.saveCars(inventory.getAvailableCars());
                System.out.println("\nSuccess! Rental Details:");
                System.out.println(rental);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Car not found or already rented.");
        }
    }

    private void handleReturn() {
        System.out.print("Enter Car ID to return: ");
        String id = scanner.nextLine();
        
        Rental rental = inventory.findActiveRental(id);
        
        
        if(rental!=null) {
        	boolean isHourly = rental.isHourly();
        	String unitlabel = isHourly ? "Hours" : "Days";
        	System.out.println("Enter number of late "+ unitlabel + "(0 if none): ");
        	int lateUnits = getIntInput("");
        	try {
        		rental.returnCar(lateUnits);
        		csvHandler.saveCars(inventory.getAvailableCars());
        		System.out.println("Succes! car: " +id+ " "+"is back in inventory.");
            	System.out.println("Final Details: " + rental);
            	inventory.completeRental(rental);
        	}catch(Exception e){
        		System.out.println("Error during return: "+e.getMessage());
        		
        	}
     
        }else {
        	System.out.println("Error No active rental found for for Car ID: " + id);
        }
    }
    public void showRentalHistory() {
    	List<Rental> rentalHistory = inventory.getRentalHistory();
    	System.out.println("\n--- Completed Rental History ---");
    	if(rentalHistory.isEmpty()) {
    		System.out.println("No completed rentals in the records.");
    	}else {
    		double totalRevenue = 0;
    		for(Rental rental: rentalHistory) {
    			System.out.println(rentalHistory.toString());
    			totalRevenue +=rental.getTotalFee();  		
    			}
    		System.out.println("---- RentalHistory ----");
    		System.out.println("Total revenue collected: $"+totalRevenue);
    	}
    	
    }
    

   
    private int getIntInput(String prompt) {
    	while (true) {
            try {
                System.out.print(prompt);
                int val = scanner.nextInt();
                scanner.nextLine(); 
                return val;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number (e.g., 1, 2, 3).");
                scanner.nextLine(); 
            }
        }
    }
       
		
	
}
