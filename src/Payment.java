
public class Payment {
	private double amount;
	public Payment(double amount) {
		this.amount = amount;
	}public double getAmount() {return amount;}
	public void payment() {
	System.out.println("Payment of $"+" "+amount+" "+"succesfully landed");
	}
	

}
