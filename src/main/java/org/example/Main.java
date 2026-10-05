package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        Scanner sc = new Scanner(System.in);
        Locale.setDefault(Locale.US);

        System.out.println("Enter the contract data:");
        System.out.println("Number:");

        try {
            int number = sc.nextInt();
            sc.nextLine();

            System.out.println("Date (dd/MM/yyyy):");
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(sc.nextLine(), fmt);

            System.out.println("Value of Contract:");
            Double contractValue = sc.nextDouble();

            System.out.println("Enter the number of installments:");
            int numberOfInstallments = sc.nextInt();

            if (numberOfInstallments <=0){
                throw new IllegalArgumentException("The number of installments have to be greater than zero!");
            }

            Contract contract = new Contract(number, date, contractValue);

            PaypalService paypalService = new PaypalService();
            ContractService contractService = new ContractService(paypalService);

            contractService.processContract(contract,numberOfInstallments);

            System.out.println("\nInstallments:");

            for (Installment installment : contract.getInstallments()){
                System.out.println(installment.toString());
            }

        }
        catch (InputMismatchException e){
            System.out.println("Error: Wrong value!");
        }
        catch (DateTimeParseException e){
            System.out.println("Error: invalid date. Use the format dd/MM/yyyy");
        }
        catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            sc.close();
        }


    }
}
