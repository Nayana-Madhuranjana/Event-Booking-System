# Event Booking Management System - Tomcat 10 Java Web Project

Premium professional Event Booking website built for IntelliJ IDEA + Apache Tomcat 10.

## Tech Stack
- Java 17
- Jakarta Servlet API 6.0
- JSP
- Maven WAR project
- TXT file storage
- Tomcat 10+

## Theme Colors
- Emerald: `#059669`
- Dark Emerald: `#064E3B`
- Mint Background: `#ECFDF5`
- Orange Accent: `#F97316`

## How to Run in IntelliJ IDEA
1. Open IntelliJ IDEA.
2. Select **File > Open** and choose this `EventBookingSystem` folder.
3. Let Maven import dependencies.
4. Go to **Run > Edit Configurations**.
5. Add **Tomcat Server > Local**.
6. Choose your installed **Tomcat 10** folder.
7. In **Deployment**, add artifact: `EventBookingSystem:war exploded`.
8. Set Application context to `/EventBookingSystem`.
9. Run Tomcat.
10. Open: `http://localhost:8080/EventBookingSystem/`

## Default Accounts
### Admin
- Email: `admin@eventpro.com`
- Password: `admin123`

### Attendee
- Email: `ahamed@email.com`
- Password: `password123`

## Data Files
Main runtime data files are in:
- `src/main/webapp/WEB-INF/data/Users.txt`
- `src/main/webapp/WEB-INF/data/Admins.txt`
- `src/main/webapp/WEB-INF/data/Events.txt`
- `src/main/webapp/WEB-INF/data/Seats.txt`
- `src/main/webapp/WEB-INF/data/BookingLog.txt`
- `src/main/webapp/WEB-INF/data/Reviews.txt`

A copy is also included in the project root `data/` folder as requested.

## Pages Included
- Home
- Register
- Login
- Profile
- Events Discovery
- Event Details
- Add Event
- Seat Map
- Ticket Purchase
- My Tickets
- Admin Dashboard
- Financial Summary
- Admin Registration
- Reviews
- Ratings Dashboard
- Activity Logs

## OOP Concepts Included
- Encapsulation: User, Event, Ticket, Review classes use private fields and getters/setters.
- Inheritance: Attendee and Admin inherit from User. LiveConcert, Webinar, and Seminar inherit from Event.
- Abstraction: Abstract Seat class and BaseServlet helper methods.
- Polymorphism: VipSeat and StandardSeat override display/price behavior.
