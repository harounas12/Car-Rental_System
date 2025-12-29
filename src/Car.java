
public abstract class Car implements Rentable{
	protected String brand;
	protected String model;
	private String id;
	protected double baseRate;
	private boolean available = true;
	private boolean isHourly = false;

	public Car(String brand, String model, String id, double baseRate) {
		this.brand = brand;
		this.model = model;
		this.id = id;
		this.baseRate = baseRate;
		}
	public String getid() {return id;}
	public boolean isAvailable() {return available;}
	public void markAvailable() {this.available=true;}
	public void markUnavailable() {this.available=false;}
	public boolean isHourly() {return isHourly;}
	public void setHourly(boolean hourly) {this.isHourly = isHourly;}
}
