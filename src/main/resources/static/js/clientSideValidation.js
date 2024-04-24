
document.getElementById('registrationForm').addEventListener('submit', function(event) {
	var isValid = true;
	var fullName = document.getElementById('full_name').value.trim();
	var email = document.getElementById('email').value.trim();
	var dob = document.getElementById('date_of_birth').value;
	var gender = document.getElementById('gender').value;
	var phone = document.getElementById('phone').value.trim();
	var password = document.getElementById('password').value;
	var confirmPassword = document.getElementById('confirm_password').value;
	var termsAgreement = document.getElementById('terms_agreement').checked;

	// Full Name validation ( i am setting fullname validation length as 1 here and 2 at server side for checking server side validation )
	if (fullName.length < 1) {
		document.getElementById('fullNameError').innerText = 'Full Name must be at least 2 characters.';
		isValid = false;
	} else {
		document.getElementById('fullNameError').innerText = '';
	}

	// Email validation
	if (!isValidEmail(email)) {
		document.getElementById('emailError').innerText = 'Enter a valid email address.';
		isValid = false;
	} else {
		document.getElementById('emailError').innerText = '';
	}

	// Date of Birth validation
	var today = new Date();
	var dobDate = new Date(dob);
	if (dobDate >= today) {
		document.getElementById('dobError').innerText = 'Date of Birth must be in the past.';
		isValid = false;
	} else {
		document.getElementById('dobError').innerText = '';
	}

	// Gender validation
	if (gender === '') {
		document.getElementById('genderError').innerText = 'Please select a gender.';
		isValid = false;
	} else {
		document.getElementById('genderError').innerText = '';
	}

	// Phone Number validation (optional)
	if (phone !== '' && !isValidPhoneNumber(phone)) {
		document.getElementById('phoneError').innerText = 'Enter a valid phone number.';
		isValid = false;
	} else {
		document.getElementById('phoneError').innerText = '';
	}



	// Confirm Password validation
	if (password !== confirmPassword) {
		document.getElementById('confirmPasswordError').innerText = 'Passwords do not match.';
		isValid = false;
	} else {
		document.getElementById('confirmPasswordError').innerText = '';
	}

	// Terms Agreement validation
	if (!termsAgreement) {
		document.getElementById('termsError').innerText = 'You must agree to the terms and conditions.';
		isValid = false;
	} else {
		document.getElementById('termsError').innerText = '';
	}

	if (!isValid) {
		event.preventDefault(); // Prevent form submission if validation fails
	} else {
		// Show the wave container if validation passes
		document.getElementById("waveContainer").style.display = "flex";

		// After 5 seconds, hide the wave container and submit the form
		setTimeout(function() {
			document.getElementById("waveContainer").style.display = "none";
			document.getElementById("registrationForm").submit();

		}, 5000);
		
		/* this is for showing submitting text on the submit button when data is getting checked and our submitting wave aniamtion is running */
		var submitButton = document.getElementById('submitButton');
		submitButton.textContent = 'Submitting...';

		// Prevent form submission while waiting for the wave animation
		event.preventDefault();
	}



});

function isValidEmail(email) {
	var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	return regex.test(email);
}

function isValidPhoneNumber(phone) {
	var regex = /^\d{10}$/;
	return regex.test(phone);
}
















