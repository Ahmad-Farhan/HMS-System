package billing.payments;

import billing.Bill;

public class PaymentByCash implements PaymentStrategy {

	@Override
	public void makepayment(Bill bill) {
	}
	@Override
	public boolean validatePaymentDetails(double amount) {
		return true;
	}
	@Override
	public String getPaymentId() {
		return null;
	}
	@Override
	public double getUpdatedBalance() {
		return 0;
	}
}
