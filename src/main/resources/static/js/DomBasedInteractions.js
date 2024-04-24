// Add event listener to the password input field
document.getElementById('password').addEventListener('input', function() {
    var password = document.getElementById('password').value;

    // Determine the strength level and character mixes of the password
    var strengthLevel = getPasswordStrengthLevel(password);
    var characterMixes = getCharacterMixes(password);

    // Dynamically update the password strength message with character mixes guidance
    var strengthMessage = '';

    switch (strengthLevel) {
        case 'weak':
            strengthMessage = 'Password strength: Weak ( Please try to Fullfill the below Requirements )';
            break;
        case 'moderate':
            strengthMessage = 'Password strength: Moderate';
            break;
        case 'strong':
            strengthMessage = 'Password strength: Strong';
            break;
        default:
            strengthMessage = '';
    }

    // Add guidance about character mixes based on the determined mixes
    if (characterMixes.length > 0) {
        strengthMessage += '\nCharacter mixes: ' + characterMixes.join(', ');
    }

    // Update the content of the passwordStrength element
    document.getElementById('passwordStrength').innerText = strengthMessage;
    
    
    // Enable or disable form submission based on strengthLevel
 /* var submitButton = document.getElementById('submitButton'); // Assuming your submit button has this ID
  submitButton.disabled = strengthLevel !== 'strong';*/
    
   
    var submitButton = document.getElementById('submitButton');
  submitButton.disabled = strengthLevel !== 'strong';
  submitButton.textContent = submitButton.disabled ? 'Create Strong Password' : 'Submit';

    
});

// Function to determine the strength level of the password
function getPasswordStrengthLevel(password) {
    if (password.length < 8) {
        return 'weak';
    }

    // Check if the password meets the strong password criteria
    var isStrong = isStrongPassword(password);

    if (isStrong) {
        return 'strong';
    }

    // Check if the password contains upper and lowercase letters or numbers
    if (password.match(/[a-zA-Z]/) && password.match(/\d/)) {
        return 'moderate';
    }

    return 'weak';
}

// Function to check if the password meets the strong password criteria
function isStrongPassword(password) {
    // Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long
    var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return regex.test(password);
}

// Function to get the character mixes present in the password
function getCharacterMixes(password) {
    var mixes = [];
    if (password.match(/[a-z]/)) {
        mixes.push('lowercase letters');
    }
    if (password.match(/[A-Z]/)) {
        mixes.push('uppercase letters');
    }
    if (password.match(/\d/)) {
        mixes.push('numbers');
    }
    if (password.match(/[@$!%*?&]/)) {
        mixes.push('special characters');
    }
    return mixes;
}




  



