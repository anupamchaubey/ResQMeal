# ResQMail 🚀

A high-performance, concurrent REST API designed to match food inventory from donors to NGOs in real-time, built with a focus on data integrity and strict auditing.

## ⚙️ Tech Stack
* **Core:** Java 21, Spring Boot 3
* **Database:** MySQL, Spring Data JPA, Hibernate
* **Security:** Stateless JWT, BCrypt, Token Blacklist Architecture
* **Concurrency:** JPA Optimistic Locking (`@Version`)

## 🧠 Core Engineering Highlights
1. **Race Condition Prevention:** Implemented Optimistic Locking at the database layer to strictly prevent double-booking when multiple NGOs attempt to claim the same inventory at the exact same millisecond.
2. **Enterprise-Grade Security:** Engineered a stateless authentication flow using JSON Web Tokens (JWT) coupled with a custom Token Blacklist table to instantly revoke compromised or logged-out sessions.
3. **Automated Lifecycle Management:** Deployed a `@Scheduled` background worker to independently sweep the database and expire outdated inventory, eliminating manual intervention.
4. **Relational Data Auditing:** Enforced strict Foreign Key constraints (`@ManyToOne`) to guarantee every transaction (posting or claiming) is permanently bound to a verifiable user identity.

## 🔌 API Endpoints
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new DONOR or NGO | No |
| POST | `/api/auth/login` | Authenticate and receive JWT | No |
| POST | `/api/auth/logout` | Invalidate current JWT (Blacklist) | Yes |
| POST | `/api/food` | Post new inventory with expiration | Yes (Donor) |
| GET | `/api/food/available` | View unclaimed, unexpired food | Yes |
| PUT | `/api/food/{id}/claim` | Claim specific inventory | Yes (NGO) |
| GET | `/api/food/my-posts` | View specific donor history | Yes (Donor) |