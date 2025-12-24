
public class ElectricCar extends Car{
	private int batteryLife;
	public ElectricCar(String brand, String model, String id, double baseRate,int batteryLife) {
		super(brand,model,id,baseRate);
		this.batteryLife = batteryLife;
	}public int getBatteryLife() {return batteryLife;}
	public double calculateFee(int days) {
		return baseRate*days*0.5;
	
	}

}
