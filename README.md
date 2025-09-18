# flashPay
FlashPay is a modern digital wallet application designed to make online transactions faster, safer, and smarter. Built using Spring Boot, it provides a seamless way for users to store money, make payments, and manage transactions with just a few taps.  🔑

📌 Features

✅ User onboarding and verification

✅ Wallet creation, balance tracking

✅ Secure fund transfers (credit/debit)

✅ Real-time transaction updates via Kafka

✅ Notification service integration

✅ Dockerized microservices for easy deployment


⚙️ Tech Stack

Layer	Technology

Backend	Spring Boot (Java 21)

Messaging	Apache Kafka

Persistence	JPA, Hibernate

Database	MySQL

Security	Spring Security

Testing	JUnit, Mockito


🧩 Microservices Breakdown

Service	Description

OnboardingService	Manages user sign-up and profile verification

WalletService	Handles wallet creation, balance updates


TransactionService	Processes fund transfers, triggers Kafka events

NotificationService	Sends alerts/notifications to users

Common<img width="4793" height="3437" alt="flash pay" src="https://github.com/user-attachments/assets/e8fb3f7e-0e16-4db7-aa25-5022b03563e2" />
























                          +------------------------+
                          |   API Gateway (Future) |
                        +------------------------+
                                     |
    +-------------+-----------------+-------------------+-------------+
    |             |                 |                   |             |
    ↓             ↓                 ↓                   ↓             ↓
+-----------+  +-----------+   +-------------+   +--------------+  +-------------+
| Onboarding|  | Wallet    |   | Transaction |   | Notification |  |  Common     |
| Service   |  | Service   |   | Service     |   | Service      |  | Module      |
+-----------+  +-----------+   +-------------+   +--------------+  +-------------+
       ↕             ↕               ↕                    ↕              |
     DB: Users     DB: Wallets    DB: Txn Logs           SMTP/SMS        |
                          ↕               ↕                    ↕              |
                      Kafka Topics:  --------------------> Notification Queue
                          └─ TXN_UPDATE_QUEUE

