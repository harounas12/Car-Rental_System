
public class Rental {
	private Car car;
	private Customer customer;
	private int days;
	private double TotalFee;
	private boolean returned;
	public Rental(Car car, Customer customer, int days) {
		if(days<=0) {
			throw new IllegalArgumentException("Rental days must be greater than 0.");
		}
		if(!car.isAvailable()) {
			throw new IllegalArgumentException("Unvailable car.");
			
		}
		this.car = car;
		this.customer = customer;
		this.days = days;
		this.TotalFee = car.calculateFee(days);
		this.returned = false;
		car.markUnavailable();
		
	}
	public Car getCar() {return car;}
	public double getTotalFee() {return TotalFee;}
	public void returnCar(int latedays) {
		if(returned) {
			throw new IllegalArgumentException("This rental has already been returned.");
		}
		if(latedays<=0) {
			throw new IllegalArgumentException("Late days cannot be less than or equal zero.");
		}
		TotalFee +=latedays*25;
		car.markAvailable();
		returned = true;
	}
	public String toString(){
        return "Rental->Customer: "+customer.getName()+"-"+"ID: "+customer.getidNum()+
        "-"+"Car Brand:"+car.brand+"-"+car.model+"-"+car.getid()+"-"
        +"Days:"+days+"-"+"Total Fee:$"+TotalFee;
    }
	
	

}
