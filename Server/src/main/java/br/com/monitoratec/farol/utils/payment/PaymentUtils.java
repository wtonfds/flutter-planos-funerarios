package br.com.monitoratec.farol.utils.payment;

public class PaymentUtils {
    public static boolean isValidPaymentDay(Integer day) {
        return day != null && day > 0 && day <= 31;
    }
}
