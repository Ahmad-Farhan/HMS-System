package billing.payments;

import billing.Bill;

public interface PaymentStrategy {
	void makepayment(Bill bill);
	boolean validatePaymentDetails(double amount);
	String getPaymentId();
	double getUpdatedBalance();
}
