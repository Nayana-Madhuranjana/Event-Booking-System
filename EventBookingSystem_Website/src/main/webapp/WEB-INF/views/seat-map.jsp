<%@ page import="java.util.*,com.eventbooking.model.*" %>
<%@ include file="header.jsp" %>
<% Event e = (Event) request.getAttribute("event"); List<Seat> seats = (List<Seat>) request.getAttribute("seats"); double basePrice = e == null ? 0 : e.getTicketPrice(); %>
<section class="page-head"><span class="eyebrow">Seat Allocation</span><h1>Interactive Seat Map</h1></section>
<section class="section">
<% if(e != null){ %>
    <div class="panel seat-panel"><h2><%=e.getEventName()%></h2><p><%=e.getEventDate()%> - <%=e.getVenueName()%></p>
        <div class="legend"><span class="legend-item available"></span> Standard <span class="legend-item vip"></span> VIP <span class="legend-item selected"></span> Selected <span class="legend-item reserved"></span> Reserved</div>
        <p class="hint">Standard: Rs. <%=String.format("%,.0f", basePrice)%> each | VIP: Rs. <%=String.format("%,.0f", basePrice * 1.7)%> each</p>
        <div class="stage">STAGE</div>
        <form id="seatSelectionForm" method="get" action="<%=ctx%>/book-ticket">
            <input type="hidden" name="eventId" value="<%=e.getEventId()%>">
            <input type="hidden" id="seatNo" name="seatNo" value="">
            <div class="seat-grid" aria-label="Seat selection">
                <% for(Seat s: seats){ boolean reserved=!s.isAvailable(); double seatPrice = basePrice * s.priceMultiplier(); %>
                    <% if(reserved){ %>
                        <button type="button" class="seat reserved-seat" disabled aria-disabled="true" title="Reserved seat"><%=s.getSeatNo()%><small><%=s.getSeatType()%></small></button>
                    <% } else { %>
                        <button type="button" class="seat <%=s.displayClass()%>" data-seat="<%=s.getSeatNo()%>" data-seat-type="<%=s.getSeatType()%>" data-seat-price="<%=String.format(java.util.Locale.US, "%.2f", seatPrice)%>" aria-pressed="false" title="<%=s.getSeatNo()%> - <%=s.getSeatType()%> - Rs. <%=String.format("%,.0f", seatPrice)%>"><%=s.getSeatNo()%><small><%=s.getSeatType()%></small></button>
                    <% } %>
                <% } %>
            </div>
            <div class="seat-actions">
                <p id="selectedSeatText" class="selected-seat-text">No seats selected.</p>
                <span class="seat-count-badge"><span id="selectedSeatCount">0</span> selected</span>
                <span class="seat-count-badge">VIP: <span id="selectedVipCount">0</span></span>
                <span class="seat-count-badge">Standard: <span id="selectedStandardCount">0</span></span>
                <span class="seat-count-badge">Total: <span id="selectedSeatTotal">Rs. 0</span></span>
                <button id="continueBooking" class="btn btn-primary" type="submit" disabled>Continue Booking</button>
            </div>
        </form>
    </div>
<% } else { %>
    <div class="panel"><div class="alert error">Event not found.</div><a class="btn btn-primary" href="<%=ctx%>/events">Back to Events</a></div>
<% } %>
</section>

<script>
(function () {
    function selectedButtons() {
        return Array.prototype.slice.call(document.querySelectorAll('.seat[data-seat].selected-seat'));
    }
    function seats() {
        return selectedButtons().map(function (btn) { return btn.getAttribute('data-seat'); }).filter(Boolean);
    }
    window.updateSelectedSeatSummary = function () {
        var buttons = selectedButtons();
        var selectedSeats = seats();
        var seatInput = document.getElementById('seatNo');
        var text = document.getElementById('selectedSeatText');
        var count = document.getElementById('selectedSeatCount');
        var vipCount = document.getElementById('selectedVipCount');
        var standardCount = document.getElementById('selectedStandardCount');
        var total = document.getElementById('selectedSeatTotal');
        var continueButton = document.getElementById('continueBooking');
        var vip = 0;
        var standard = 0;
        var amount = 0;
        buttons.forEach(function (btn) {
            var type = (btn.getAttribute('data-seat-type') || '').toLowerCase();
            if (type === 'vip') vip++; else standard++;
            amount += parseFloat(btn.getAttribute('data-seat-price') || '0') || 0;
        });
        if (buttons.length >= 3) amount = amount * 0.9;
        if (seatInput) seatInput.value = selectedSeats.join(',');
        if (text) text.textContent = selectedSeats.length ? 'Selected seats: ' + selectedSeats.join(', ') : 'No seats selected.';
        if (count) count.textContent = String(selectedSeats.length);
        if (vipCount) vipCount.textContent = String(vip);
        if (standardCount) standardCount.textContent = String(standard);
        if (total) total.textContent = 'Rs. ' + Math.round(amount).toLocaleString();
        if (continueButton) continueButton.disabled = selectedSeats.length === 0;
    };
    window.toggleSeatSelection = function (button) {
        if (!button || button.disabled || button.classList.contains('reserved-seat')) return;
        button.classList.toggle('selected-seat');
        button.setAttribute('aria-pressed', button.classList.contains('selected-seat') ? 'true' : 'false');
        window.updateSelectedSeatSummary();
    };
    window.initSeatMap = function () {
        var form = document.getElementById('seatSelectionForm');
        if (!form || form.getAttribute('data-initialized') === 'true') return;
        form.setAttribute('data-initialized', 'true');
        Array.prototype.slice.call(form.querySelectorAll('.seat[data-seat]')).forEach(function (btn) {
            btn.addEventListener('click', function () { window.toggleSeatSelection(btn); });
        });
        form.addEventListener('submit', function (event) {
            window.updateSelectedSeatSummary();
            if (seats().length === 0) {
                event.preventDefault();
                alert('Please select at least one available seat.');
            }
        });
        window.updateSelectedSeatSummary();
    };
    if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', window.initSeatMap); else window.initSeatMap();
})();
</script>

<%@ include file="footer.jsp" %>
