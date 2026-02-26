package com.dss.ecommerce.dao;

import com.dss.ecommerce.enums.PaymentMode;
import com.dss.ecommerce.enums.PaymentStatus;
import java.util.Map;

public interface PaymentDao {

    public void add(Map<Integer, Integer> orderIdMappedByAmount, PaymentMode mode);

    public void updateStatus(Map<Integer, Integer> orderIdMappedByAmount, PaymentStatus paymentStatus);
}
