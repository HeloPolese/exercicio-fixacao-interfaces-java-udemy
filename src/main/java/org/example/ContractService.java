package org.example;

import java.time.LocalDate;
import java.util.Locale;

public class ContractService {
    private OnlinePaymentService onlinePaymentService;


    public ContractService(OnlinePaymentService paypalService){
        this.onlinePaymentService = paypalService;
    }


    public void processContract(Contract contract, Integer months){

        double value = contract.getTotalValue() / months;

        for (int i = 1; i <= months; i++) {
            Double interest = onlinePaymentService.interest(value, i);

            Double paymentfee = onlinePaymentService.paymentFee(value);

            double total = value + interest + paymentfee;

            Installment installment = new Installment(contract.getDate().plusMonths(i),total);
            contract.getInstallments().add(installment);
        }
    }
}
