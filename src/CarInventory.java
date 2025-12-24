import java.util.ArrayList;
import java.util.List;


public class CarInventory {
	private ArrayList<Car> cars;
	private ArrayList<Rental> rentals;
	private ArrayList<Rental> rentalHistory;
	
	public CarInventory() {
		cars = new ArrayList<>();
		rentals = new ArrayList<>();
		rentalHistory = new ArrayList<>();
	}
	public void addCar(Car car) {
		cars.add(car);
	}
	public Car getCarbyId(String id) {
		for(Car car: cars) {
			if(car.getid().equals(id)) return car;
		}
		return null;
	}
	public List<Car> getAvailableCars(){
		List<Car> available = new ArrayList<>();
		for(Car car: cars) {
			if(car.isAvailable()) available.add(car);
		}
		return available;
	}
	public void processRental(Car car, Customer customer, int days) {
		Rental rental = new Rental(car, customer, days);
		rentals.add(rental);
	}
	public Rental findActiveRental(String id) {
		for(Rental rental: rentals) {
			if(rental.getCar().getid().equals(id)) {
				return rental;
			}
		}
		return null;
	}
	public void removeRental(Rental rental) {
		rentals.remove(rental);
	}
	public List<Rental> getRentalHistory() {
	    
	    return new ArrayList<>(this.rentalHistory);
	}
	public void addRental(Rental rental) {
		rentals.add(rental);	
	}
	public void completeRental(Rental rental) {
	    rentals.remove(rental); 
	    
	    rentalHistory.add(rental); 
	}
	public double calculateTotalRevenue() {
	    double total = 0;
	    for (Rental rental : rentalHistory) {
	        total += rental.getTotalFee();
	    }
	    return total;
	}
	
	

}