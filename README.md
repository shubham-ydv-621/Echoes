🚀 Echoes – Spring Boot Journaling Application

A secure journaling platform that lets users record daily thoughts, automatically analyzes their sentiment, and emails them a personalized weekly summary — built with Spring Boot, Kafka, Redis, and MongoDB, and deployed on Heroku.

🧩 1. Project Identity & Infrastructure
Detail	Description
Project Name	Echoes – Spring Boot Journaling Application
Core Function	Secure daily journaling with automated sentiment analysis, caching, and weekly email reporting.
Tech Stack	Java, Spring Boot, MongoDB, Redis, Kafka, Spring Security, JWT, Heroku
Live Link	echoes-app-12345-4ab18d978ca1.herokuapp.com/public

Source Code	GitHub Repository

Deployment	Heroku Cloud – Demonstrates CI/CD and production deployment.
⚙️ 2. Technical Stack & Key Components
Technology	Role in Application	Example Integration
Spring Boot	Core API & microservices framework	Controllers, Services, Configurations
MongoDB	Primary persistence layer	Stores User and EchoesEntry data
Redis	In-memory caching	Caches user journal entries for faster retrieval
Kafka	Asynchronous event streaming	Decouples journaling and sentiment analysis
JWT (Spring Security)	Secure authentication	Stateless user login & authorization
Spring Scheduling	Background automation	Triggers weekly report generation
JavaMail	Email communication	Sends personalized weekly summaries
🔐 3. Authentication & Security Flow

Flow Overview:
Credentials → Spring Security → MongoDB → JWT → Request Header

Step	Component	Action	Data Handled
1️⃣	PublicController	Receives username/password	Credentials (Encrypted)
2️⃣	UserDetailsServiceImpl	Validates credentials from MongoDB	User Entity
3️⃣	JwtUtil	Generates signed JWT token	JWT (username, expiry)
4️⃣	JwtFilter	Intercepts requests, validates token	Security Context (Authenticated User)

🔒 Every secured endpoint (/echoes/**) passes through the JWT filter before controller access.

📓 4. Core Journaling (CRUD) Flow

Data Flow:
Request → Controller → MongoDB → Kafka (Async)

Action	Endpoint	Description	Integration
Create	POST /echoes	Creates a new journal entry and links it to the user.	Triggers Kafka event for sentiment analysis.
Read All	GET /echoes	Retrieves all entries for the logged-in user.	Uses Redis cache for performance.
Update	PUT /echoes/id/{id}	Updates a journal entry’s content or title.	MongoDB
Delete	DELETE /echoes/id/{id}	Deletes a user’s journal entry.	MongoDB
⚡ 5. Redis Caching Flow

Data Flow:
Request → Redis → MongoDB (fallback)

Step	Component	Action	Data Handled
1️⃣	EchoesEntryController	Receives read request	User ID
2️⃣	AppCache	Checks Redis for user’s entries	Key: user:{id}:entries
3️⃣	Cache Hit	Returns data directly from Redis	List<EchoesEntry>
4️⃣	Cache Miss	Fetches data from MongoDB	List<EchoesEntry>
5️⃣	AppCache	Writes fresh data to Redis	Updated cache entries

⚙️ Result: Sub-second read times for frequently accessed journal data.

🔁 6. Kafka Asynchronous Processing Flow

Purpose:
Decouple entry creation from sentiment analysis using Kafka event streaming.

Step	Component	Action	Data
1️⃣	EchoesEntryService (Producer)	Publishes event to Kafka topic sentiment_events	Journal ID / Content
2️⃣	SentimentConsumerService (Consumer)	Listens to Kafka topic, consumes event	Entry Data
3️⃣	SentimentConsumerService	Performs sentiment analysis (POSITIVE/NEGATIVE/NEUTRAL)	Sentiment Result
4️⃣	EchoesEntryRepository	Updates entry in MongoDB with sentiment	Updated EchoesEntry

🧠 Kafka ensures scalability and prevents delays in user response time.

📅 7. Automated Weekly Report Flow
Step	Component	Action	Data
1️⃣	UserScheduler	Scheduled task runs weekly	Cron trigger
2️⃣	UserService	Fetches last 7 days’ entries per user	Weekly journal data
3️⃣	UserService	Aggregates mood metrics (avg sentiment, counts)	Summary text
4️⃣	EmailService	Sends report to user via JavaMail	Weekly summary email

📧 Each user receives a personalized “Weekly Mood Summary” in their inbox automatically.

🧱 8. Code Structure Overview
com.shubham.Echoes
│
├── cache/              # Redis caching layer (AppCache)
├── config/             # Redis, Security, Kafka, and App configs
├── controller/         # REST controllers (EchoesEntry, Public, User)
├── entity/             # MongoDB documents (User, EchoesEntry)
├── enums/              # Enum classes (e.g., Sentiment)
├── filter/             # JWT Filter (for authentication)
├── model/              # Helper models and DTOs
├── repository/         # MongoDB repositories
├── scheduler/          # Spring scheduled jobs (UserScheduler)
├── service/            # Business logic (EchoesEntryService, EmailService)
└── utils/              # Utility classes (JwtUtil, common helpers)

🧠 9. Highlights & Learnings

Implemented JWT-based stateless authentication using Spring Security.

Used Redis caching to optimize read-heavy endpoints.

Built asynchronous pipelines using Kafka to decouple analysis workloads.

Automated reporting using Spring Scheduler and JavaMail.

Deployed to Heroku, showcasing end-to-end deployment experience.

🌐 10. Live Demo & Source

🔗 Live App: https://echoes-app-12345-4ab18d978ca1.herokuapp.com/public

📂 Source Code: GitHub Repository

🧰 11. Future Enhancements

Add frontend UI using React for better user experience.

Extend sentiment analysis using an ML-based NLP model.

Integrate user analytics dashboard.
