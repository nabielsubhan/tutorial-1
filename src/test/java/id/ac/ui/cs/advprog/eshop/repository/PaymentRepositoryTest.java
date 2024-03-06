package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        payments = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9a15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
        Order order3 = new Order("e33ef40-9eff-4da8-9487-8ee697ecbf1e",
                products, 1708570000L, "Bambang Sudrajat");
        orders.add(order3);

        Map<String, String> paymentDataVoucher = new HashMap<>();
        paymentDataVoucher.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER_CODE", orders.getFirst(), paymentDataVoucher);
        payments.add(payment1);

        Map<String, String> paymentDataCashOnDelivery = new HashMap<>();
        paymentDataCashOnDelivery.put("address", "Citayam Raya Street No. 71");
        paymentDataCashOnDelivery.put("deliveryFee", "100000");
        Payment payment2 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "CASH_ON_DELIVERY", orders.getFirst(), paymentDataCashOnDelivery);
        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.getFirst();
        Payment result = paymentRepository.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());

        Payment findResult = paymentRepository.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }
    @Test
    void testGetPaymentIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());
        }

        Payment findResult = paymentRepository.getPayment(payments.getFirst().getId());
        assertEquals(payments.getFirst().getId(), findResult.getId());
        assertEquals(payments.getFirst().getMethod(), findResult.getMethod());
        assertEquals(payments.getFirst().getPaymentData(), findResult.getPaymentData());
        assertEquals(payments.getFirst().getStatus(), findResult.getStatus());
    }
    @Test
    void testGetPaymentIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());
        }

        Payment findResult = paymentRepository.getPayment("zczc");
        assertNull(findResult);
    }
    @Test
    void testGetAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());
        }

        List<Payment> findResult = paymentRepository.getAllPayments();
        for (int i = 0; i < findResult.size(); i++) {
            assertEquals(payments.get(i).getId(), findResult.get(i).getId());
        }
    }
}