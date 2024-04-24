// ( payment module starts from here )first request - to server to create request for payment
const paymentStart = () => {
  console.log("payment started..");
  let amount = $("#payment_field").val();

  console.log(amount);
  if (amount == "" || amount == null) {
    alert("amount is required !!");
    // swal("Oop's", "amount is required !!", "error");
    return;
  }

  // code...
  // we will use ajax to send request to server to create order- jquery
  $.ajax({
    url: "/create_donateus_order",
    data: JSON.stringify({ amount: amount, info: "order_request" }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",

    success: function (response) {
      //invoked when succcess
      console.log(response);
      if (response.status == "created") {
        //open payment form
        let options = {
          key: "rzp_test_aEuqELmH348FIT",
          amount: response.amount,
          currency: response.currency, // or use 'INR' DIRECTLY
          name: "Blog Application",
          description: "Its AN Online Blog Application ",
          image:
            "https://cdn.pixabay.com/photo/2012/05/07/18/57/blog-49006_640.png",
          order_id: response.id,
          handler: function (response) {
            console.log(response.razorpay_payment_id);
            console.log(response.razorpay_order_id);
            console.log(response.razorpay_signature);
            console.log("payment successful !!");
            alert(
              "congrates !! Payment successful !!  but not saved yet Please wait ..."
            );

            //this function is for update the payment status in the server or database
            updatePaymentOnServer(
              response.razorpay_payment_id,
              response.razorpay_order_id,
              "paid"
            );
          },
          prefill: {
            name: "",
            email: "",
            contact: "",
          },
          notes: {
            address: "Blog Application",
          },
          theme: {
            color: "#3399cc",
          },
        };

        let rzrp = new Razorpay(options);

        rzrp.on("payment.failed", function (response) {
          console.log(response.error.code);
          console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);

          alert("Payment Failed!! Something Went Wrong...");
          // swal("Failed!!", "Payment Failed!! Something Went Wrong...", "error");
        });

        rzrp.open();
      }
    },

    error: function (error) {
      //invoked when error
      console.log(error);
      alert("something went wrong !!!.");
      // swal("Failed!!", "Payment Failed!! Something Went Wrong...", "error");
    },
  });
};

// this function for updating payment status in the database or server
function updatePaymentOnServer(payment_id, order_id, status) {
  $.ajax({
    url: "/update_donate_order_toDB",
    data: JSON.stringify({
      payment_id: payment_id,
      order_id: order_id,
      status: status,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      //it will take user to the congrats page after payment done...
      window.location.href = "/thankYouMsg";
    },
    error: function (error) {
      //invoked when error
      console.log(error);
      alert("something went wrong !!");
      // swal(
      //   "Failed!!",
      //   "Your payment is successful but we did not get on server , we will contact you as soon as possible",
      //   "error"
      // );
    },
  });
}
