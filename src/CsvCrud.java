import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvCrud {
	private String filePath;
    private static final String CSV_HEADER = "Type,Brand,Model,ID,BaseRate,BatteryOrEngine";

    public CsvCrud(String filePath) {
        this.filePath = filePath;
    }

    // CREATE / UPDATE: Save the entire list of cars to CSV
    public void saveCars(List<Car> cars) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(CSV_HEADER);
            for (Car car : cars) {
                String type = (car instanceof ElectricCar) ? "Electric" : "Gas";
                int specificValue = (car instanceof ElectricCar) ? 
                                    ((ElectricCar) car).getBatteryLife() : 
                                    ((GasCar) car).getEnginesize();
                
                writer.printf("%s,%s,%s,%s,%.2f,%d%n", 
                    type, car.brand, car.model, car.getid(), car.baseRate, specificValue,car.isAvailable());
            }
        } catch (IOException e) {
            System.err.println("Error saving to CSV: " + e.getMessage());
        }
    }
    public List<Car> loadCars() {
        List<Car> loadedCars = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) return loadedCars;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 7) continue; 

                String type = data[0];
                String brand = data[1];
                String model = data[2];
                String id = data[3];
                double rate = Double.parseDouble(data[4]);
                int specificVal = Integer.parseInt(data[5]);
                boolean isAvailable = Boolean.parseBoolean(data[7]);

                Car car; 
                if (type.equalsIgnoreCase("Electric")) {
                    car = new ElectricCar(brand, model, id, rate, specificVal);
                } else {
                    car = new GasCar(brand, model, id, rate, specificVal);
                }

                if (isAvailable) {
                    car.markAvailable();
                } else {
                    car.markUnavailable();
                }
                
                loadedCars.add(car);
            } 
        } 
        catch (IOException e) {
            System.err.println("Error loading CSV: " + e.getMessage());
        } 
        
        return loadedCars;
    }
    } 
