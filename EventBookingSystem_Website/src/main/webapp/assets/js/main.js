function toggleMenu() {
    const nav = document.getElementById('mainNav');
    if (nav) nav.classList.toggle('open');
}
function validatePasswords() {
    const p = document.getElementById('password');
    const c = document.getElementById('confirmPassword');
    if (p && c && p.value !== c.value) {
        alert('Passwords do not match.');
        return false;
    }
    return true;
}
function calculateTotal() {
    const total = document.getElementById('selectedSeatTotal');
    const totalAmount = document.getElementById('totalAmount');
    if (total && totalAmount) totalAmount.value = total.textContent;
}

function getSelectedSeatButtons() {
    return Array.from(document.querySelectorAll('.seat[data-seat].selected-seat'));
}

function getSelectedSeats() {
    return getSelectedSeatButtons()
        .map(function (btn) { return btn.getAttribute('data-seat'); })
        .filter(Boolean);
}

function updateSelectedSeatSummary() {
    const buttons = getSelectedSeatButtons();
    const selectedSeats = getSelectedSeats();
    const seatInput = document.getElementById('seatNo');
    const text = document.getElementById('selectedSeatText');
    const count = document.getElementById('selectedSeatCount');
    const vipCount = document.getElementById('selectedVipCount');
    const standardCount = document.getElementById('selectedStandardCount');
    const total = document.getElementById('selectedSeatTotal');
    const continueButton = document.getElementById('continueBooking');

    let vip = 0;
    let standard = 0;
    let amount = 0;
    buttons.forEach(function (btn) {
        const type = (btn.getAttribute('data-seat-type') || '').toLowerCase();
        if (type === 'vip') vip += 1;
        else standard += 1;
        amount += parseFloat(btn.getAttribute('data-seat-price') || '0') || 0;
    });
    if (buttons.length >= 3) amount = amount * 0.9;

    if (seatInput) seatInput.value = selectedSeats.join(',');
    if (text) {
        text.textContent = selectedSeats.length
            ? 'Selected seats: ' + selectedSeats.join(', ')
            : 'No seats selected.';
    }
    if (count) count.textContent = selectedSeats.length.toString();
    if (vipCount) vipCount.textContent = vip.toString();
    if (standardCount) standardCount.textContent = standard.toString();
    if (total) total.textContent = 'Rs. ' + Math.round(amount).toLocaleString();
    if (continueButton) continueButton.disabled = selectedSeats.length === 0;
}

function toggleSeatSelection(button) {
    if (!button || button.disabled || button.classList.contains('reserved-seat')) return;
    button.classList.toggle('selected-seat');
    button.setAttribute('aria-pressed', button.classList.contains('selected-seat') ? 'true' : 'false');
    updateSelectedSeatSummary();
}

function initSeatMap() {
    const form = document.getElementById('seatSelectionForm');
    if (!form || form.getAttribute('data-initialized') === 'true') return;
    form.setAttribute('data-initialized', 'true');

    form.querySelectorAll('.seat[data-seat]').forEach(function (seatButton) {
        seatButton.addEventListener('click', function () {
            toggleSeatSelection(seatButton);
        });
    });

    form.addEventListener('submit', function (event) {
        updateSelectedSeatSummary();
        if (getSelectedSeats().length === 0) {
            event.preventDefault();
            alert('Please select at least one available seat.');
        }
    });

    updateSelectedSeatSummary();
}

document.addEventListener('DOMContentLoaded', initSeatMap);
