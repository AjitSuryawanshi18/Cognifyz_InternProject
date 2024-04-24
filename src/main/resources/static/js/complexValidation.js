// this code is for dynamically updating dom using javascript
// Add event listener to the gender select field
document.getElementById('gender').addEventListener('change', function() {
    var gender = document.getElementById('gender').value;
    var message = '';

    // Dynamically update the message based on the selected gender
    switch (gender) {
        case 'male':
            message = 'You have selected Male.';
            break;
        case 'female':
            message = 'You have selected Female.';
            break;
        case 'other':
            message = 'You have selected Other.';
            break;
        default:
            message = 'Please select a gender.';
            break;
    }

    // Update the content of the genderMessage element
    document.getElementById('genderMessage').innerText = message;
});