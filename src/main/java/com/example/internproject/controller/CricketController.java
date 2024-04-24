// mapping for cricket, donate amount and thank message
package com.example.internproject.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.internproject.Repos.DonatedAmountRepository;
import com.example.internproject.model.DonatedAmount;
import com.example.internproject.service.CricketService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Controller
public class CricketController {
	
	@Autowired
	private CricketService cricketService;
	
	@Autowired
	private DonatedAmountRepository donatedAmountRepository;

    @GetMapping("/live_scores")
    public String getLiveScores(Model model) {
        String liveScores = cricketService.getLiveScores();
        model.addAttribute("liveScores", liveScores);
        return "live_scores"; 
    }
    
    // Donate Us Coding StartS From Here...
    
    //showing donate page after clicking on DonateUs
    @GetMapping("/DonateUs")
    public String DonateUs() {    	
    	return "DonateUs"; 
    }
    
    //now not in use
    @PostMapping("/DonateUs")
    public String processDonation(@RequestParam("amount") double amount, Model model) {
        // Here you can handle the donation amount, for example, save it to a database
        System.out.println("Received donation amount: " + amount);
        
        // You can add a success message to the model if needed
        model.addAttribute("message", "Thank you for your donation!");

        // Redirect to a thank you page or wherever you want
        return "/DonateUs";
    }
    

	//create order mapping 
		 @PostMapping("/create_donateus_order")
		    @ResponseBody
		    public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws Exception {
		    	
//		    	System.out.println("hey order function executed..");
//		    	System.out.println(data);
		    	// getting totalAmount in amount variable for our checkout payment process
		    	int amount = Integer.parseInt(data.get("amount").toString());
//		    	System.out.println( "amount from order created function : "+ amount*100 + " Rupees");
		    	
		    	var client = new RazorpayClient("rzp_test_aEuqELmH348FIT", "sIwHqHaaKRIc6DN7Cg2jfyfs"); //paste your keys here...
		    	
		    	
		    	
		    	// we get random transaction number from here
		    	
		    	 // Create a random object
		        Random random = new Random();
//
		            String transactionNumber = generateTransactionNumber();
		            int randomNumber = random.nextInt(9000) + 1000;
		    	String receiptNo = randomNumber +"_txn_"+transactionNumber;
//		          
//		      
		    	JSONObject ob=new JSONObject();
		    	ob.put("amount", amount*100);//have to put in paise
		    	ob.put("currency", "INR");
		    	ob.put("receipt", receiptNo);
//		    	
//		    	//creating new order
		    	Order order =  client.orders.create(ob);
//		    	System.out.println(order);
		    	
////		    	//we can save this order to the database for order history for our future reference
////		    	
		    	DonatedAmount donatedAmount = new DonatedAmount();
//		    	
//		    	//this is for saving amount in Rupees not in paise 
		    	int preFinalAmount = order.get("amount");
		    	int finalAmount = preFinalAmount/100;
		    	
		    	donatedAmount.setAmount(finalAmount+"");
		    	donatedAmount.setOrderId(order.get("id"));
		    	donatedAmount.setPaymentId(order.get(null));
		    	donatedAmount.setStatus("created");
		    	donatedAmount.setReceipt(order.get("receipt"));
		    	
		    	this.donatedAmountRepository.save(donatedAmount);
		    	
		    	
		    	return order.toString();

		    }
		 
		//method which generate random numbers for transaction
		 public static String generateTransactionNumber() {
		        // length of the transaction number
		        int length = 5; // we adjust the length as needed

		        // Set the characters that can be used in the transaction number
		        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		        // Initialize a StringBuilder to store the generated transaction number
		        StringBuilder transactionNumber = new StringBuilder();

		        // Create a random object
		        Random random = new Random();

		        // Generate random characters until the desired length is reached
		        for (int i = 0; i < length; i++) {
		            // Get a random index from the characters string
		            int index = random.nextInt(characters.length());

		            // Append the character at the random index to the transaction number
		            transactionNumber.append(characters.charAt(index));
		        }

		        // Convert StringBuilder to String and return the transaction number
		        return transactionNumber.toString();
		    }
    
    
		 
		 
		 // updating payment data in the database after payment done before this payment id is null and status is created .
		 @PostMapping("/update_donate_order_toDB")
		 public ResponseEntity<?> updatePaymentOnServer(@RequestBody Map<String, Object> data){
			 
			 DonatedAmount donatedAmount = donatedAmountRepository.findByOrderId(data.get("order_id").toString());
			 
			 donatedAmount.setPaymentId(data.get("payment_id").toString());
			 donatedAmount.setStatus(data.get("status").toString());
			 
			 this.donatedAmountRepository.save(donatedAmount);
			 
//			 System.out.println(data);
			 
			 return ResponseEntity.ok(Map.of("msg","Updated"));
		 }
		 
		 //take user to thank you page after successfully donating amount 
		 @GetMapping("/thankYouMsg")
		 public String thankYouMsg(){
			 
			 return "thankYouMsg";
		 }
		 
		 
}

