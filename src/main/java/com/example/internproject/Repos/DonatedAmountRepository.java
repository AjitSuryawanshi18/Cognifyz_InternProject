package com.example.internproject.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.internproject.model.DonatedAmount;
import java.util.List;


public interface DonatedAmountRepository extends JpaRepository<DonatedAmount, Long>{

	DonatedAmount findByOrderId(String orderId);
	
}
