package com.library.springbootlibrary.service;

import com.library.springbootlibrary.dao.PaymentRepository;
import com.library.springbootlibrary.entity.Payment;
import com.library.springbootlibrary.requestmodels.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PaymentService {

    private PaymentRepository paymentRepository ;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, @Value("${Stripe.key.secret}") String secretKey){
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfoRequest.getAmount());
        params.put("currency", paymentInfoRequest.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }
    //PaymentIntent is a class for stripe.

    //After payment this method set amount to 0 for user
    public ResponseEntity<String> stripePayment(String userEmail) throws Exception{
        Payment payment= paymentRepository.findByUserEmail(userEmail);

        if(payment == null){
            throw new Exception("Payment information is missing");
        }
        payment.setAmount(00.00);
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
