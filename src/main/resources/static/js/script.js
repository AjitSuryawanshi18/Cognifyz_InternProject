// Function to open terms and conditions popup
let openTermsPopup = () => {
  var termsUrl = "/terms_and_conditions"; // Replace with the URL of your terms and conditions page
  window.open(
    termsUrl,
    "TermsPopup",
    "width=600,height=400,scrollbars=yes,resizable=yes"
  );
};
