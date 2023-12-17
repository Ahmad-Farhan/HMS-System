package billing.payments;

import billing.Bill;

public class PaymentByCreditCard implements PaymentStrategy {
	private String cardno;
	private String ccs;
	private double balance;
	
	PaymentByCreditCard(String cardno_val, String ccs_val){
		cardno = cardno_val;
		ccs = ccs_val;
	}
	@Override
	public void makepayment(Bill bill) {
		balance -= (bill.getAmount() - bill.getTax());
	}
	@Override
	public boolean validatePaymentDetails(double amount) {
		return (balance > amount);
	}
//	@Override
//	public void collectPaymentDetails(double balance_val) {
//		balance = balance_val;
//	}
	@Override
	public String getPaymentId() {
		return cardno;
	}
	@Override
	public double getUpdatedBalance() {
		return balance;
	}
}
