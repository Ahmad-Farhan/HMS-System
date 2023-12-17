package billing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import billing.payments.PaymentStrategy;
import diagnostics.SchedulableDatabase;
import diagnostics.appointment.Appointment;
import repo.HealthReport;

public class BillingService {
	private ArrayList<Bill> bills;
	private PaymentStrategy strategy;
	
	public BillingService() {
		bills = null;
	}
	public void generateBill(HealthReport report) {
		double amt = 3000;
		String status = "Due";
		String generated_on = getCurrentDate();
		SchedulableItemBill bill = new SchedulableItemBill(amt, status, generated_on, null, report.getAppointment().getId());
		BillingDatabase.saveBill(bill);
	}
	private String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(currentDate);
	}
	public ArrayList<Appointment> getConductedAppointments(){
		return SchedulableDatabase.retrieveConductedAppointments();
	}
	public SchedulableItemBill loadBillDetails(int id) {
		return BillingDatabase.loadBillDetails(id);
	}
	public void setStrategy(PaymentStrategy strat_val) {
		strategy = strat_val;
	}
	public void processBill(SchedulableItemBill bill){
		bill.setPaid(getCurrentDate());
		BillingDatabase.payAppointmentBill(bill);
		
//		BillingDatabase.validatePaymentDetails(strategy);
//		BillingDatabase.retrievePaymentDetails(strategy);
//		double amount = bill.getAmount() + bill.getTax();
//		if(strategy.validatePaymentDetails(amount)) strategy.pay(bill);
//		BillingDatabase.updateStrategyDetails(strategy);
	}	
}
