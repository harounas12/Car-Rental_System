import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class RentalSystemTest {
	private CarInventory inventory;
    private Car testCar;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        // Initialize the environment before each test
        inventory = new CarInventory();
        // Using ElectricCar because Car is abstract
        testCar = new ElectricCar("Tesla", "Model Y", "E01", 100.0, 100);
        testCustomer = new Customer("Harouna", 12345);
        inventory.addCar(testCar);
    }

    @Test
    void testAutomaticUnavailableOnRental() {
        // Initially available
        assertTrue(testCar.isAvailable(), "Car should be available initially");

        // Create rental (This triggers the automatic update)
        Rental rental = new Rental(testCar, testCustomer, 3);

        // Assertions
        assertFalse(testCar.isAvailable(), "Car should automatically become unavailable after rental");
        
        List<Car> availableCars = inventory.getAvailableCars();
        assertFalse(availableCars.contains(testCar), "Inventory should not list rented car as available");
    }

    @Test
    void testAutomaticAvailableOnReturn() {
        Rental rental = new Rental(testCar, testCustomer, 3);
        
        // Return the car with 0 late days
        rental.returnCar(3);

        // Assertions
        assertTrue(testCar.isAvailable(), "Car should automatically become available after return");
        
        List<Car> availableCars = inventory.getAvailableCars();
        assertTrue(availableCars.contains(testCar), "Inventory should list car as available again");
    }

    @Test
    void testCalculateFee() {
        // Electric Car fee: baseRate * days * 0.5
        // 100 * 3 * 0.5 = 150.0
        Rental rental = new Rental(testCar, testCustomer, 3);
        assertEquals(150.0, rental.getTotalFee(), "Base rental fee calculation is incorrect");

        // Add 2 late days ($25 per day penalty)
        // 150 + (2 * 25) = 200.0
        rental.returnCar(2);
        assertEquals(200.0, rental.getTotalFee(), "Late fee calculation is incorrect");
    }

    @Test
    void testCannotRentUnavailableCar() {
        // Rent it once
        new Rental(testCar, testCustomer, 1);

        // Attempt to rent it again should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            new Rental(testCar, testCustomer, 2);
        }, "Should not be able to rent a car that is already unavailable");
    }

	

}
