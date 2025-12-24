
public class GasCar extends Car {
	private int enginesize;
	public GasCar(String brand, String model, String id, double baseRate,int enginesize) {
		super(brand,model,id,baseRate);
		this.enginesize = enginesize;
	}public int getEnginesize() {return enginesize;}
	public double calculateFee(int days) {
		return baseRate*days*0.9;
	}

}
