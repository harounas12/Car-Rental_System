
public class Rental {
	private Car car;
	private Customer customer;
	private int duration;
	private double TotalFee;
	private boolean returned;
	private boolean isHourly;
	public Rental(Car car, Customer customer, int duration,boolean isHourly) {
		if(duration<=0) {
			throw new IllegalArgumentException("Rental duration must be greater than 0.");
		}
		if(!car.isAvailable()) {
			throw new IllegalArgumentException("Unvailable car.");
			
		}
		this.car = car;
		this.customer = customer;
		this.duration = duration;
		this.isHourly = isHourly;
		
		this.returned = false;
		if(isHourly) {
			this.TotalFee = car.calculateHourlyFee(duration);
		}else {
			this.TotalFee = car.calculateFee(duration);
		}
		car.markUnavailable();
		
	}
	public Car getCar() {return car;}
	public boolean isHourly() {
	    return isHourly;
	}
	public double getTotalFee() {return TotalFee;}
	public void returnCar(int lateUnits) {
		if(returned) {
			throw new IllegalArgumentException("This rental has already been returned.");
		}
		if(isHourly) {
			TotalFee +=lateUnits*5;
		}else {
			TotalFee +=lateUnits*25;
		}
		
		car.markAvailable();
		returned = true;
	}
	public String toString(){
		String unitlabel = isHourly ? "Hours" : "Days";
        return "Rental->Customer: "+customer.getName()+"-"+"ID: "+customer.getidNum()+
        "-"+"Car Brand:"+car.brand+"-"+car.model+"-"+car.getid()+"-"
        +"Duration:"+duration+" "+unitlabel+"-"+"Total Fee:$"+TotalFee;
    }
	
	

}
