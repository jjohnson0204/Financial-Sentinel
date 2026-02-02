# 🛰️ Financial Sentinel

**Real-time Financial Transaction Monitoring & Anomaly Detection System**

A modern Spring Boot application that leverages Kafka streaming, machine learning capabilities, and real-time analytics to detect anomalies in financial transactions, providing intelligent coaching and insights for better financial decision-making.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Usage](#-usage)
- [API Endpoints](#-api-endpoints)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [License](#-license)

---

## ✨ Features

- **Real-Time Transaction Processing**: Stream and process financial transactions using Apache Kafka
- **Anomaly Detection**: AI-powered detection of unusual spending patterns and suspicious transactions
- **Live Dashboard**: Beautiful web interface with Server-Sent Events (SSE) for real-time alerts
- **Smart Financial Coaching**: Context-aware recommendations based on transaction analysis
- **Category Classification**: Automatic categorization of transactions (Dining, Tech, Travel, etc.)
- **Risk Assessment**: Multi-level risk classification (STABLE, WARNING, CRITICAL)
- **Persistent Storage**: H2 in-memory database for transaction history and analytics
- **Top Spenders Leaderboard**: Real-time analytics showing spending patterns
- **Docker Support**: Easy deployment with Docker Compose for Kafka infrastructure

---

## 🚀 Tech Stack

### Backend
- **Java 21** - Latest LTS version with virtual threads support
- **Spring Boot 4.0.1** - Application framework
- **Spring Data JPA** - Data persistence layer
- **Spring Kafka** - Event streaming integration
- **H2 Database** - In-memory database for development
- **Deep Java Library (DJL)** - AI/ML framework integration (PyTorch)

### Frontend
- **Vanilla JavaScript** - Lightweight and fast
- **Server-Sent Events (SSE)** - Real-time streaming
- **CSS3** - Modern, responsive design

### Infrastructure
- **Apache Kafka** - Event streaming platform
- **Apache Zookeeper** - Kafka coordination
- **Docker & Docker Compose** - Containerization
- **Maven** - Build and dependency management

---

## 🏗️ Architecture

```
┌─────────────┐
│   CSV File  │
└──────┬──────┘
       │
       ▼
┌─────────────────────┐
│  Ingestion Service  │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐         ┌──────────────────┐
│   Kafka Producer    │────────>│   Kafka Topic    │
└─────────────────────┘         └────────┬─────────┘
                                         │
                                         ▼
                              ┌──────────────────────┐
                              │   Kafka Consumer     │
                              └──────────┬───────────┘
                                         │
                    ┌────────────────────┼─────────────────────┐
                    ▼                    ▼                     ▼
          ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
          │ Anomaly Service │  │ Category Service│  │  Coach Service  │
          └────────┬────────┘  └────────┬────────┘  └────────┬────────┘
                   │                    │                     │
                   └────────────────────┼─────────────────────┘
                                        ▼
                                ┌──────────────────┐
                                │  H2 Database     │
                                └──────────────────┘
                                        │
                                        ▼
                                ┌──────────────────┐
                                │  REST API / SSE  │
                                └──────────────────┘
                                        │
                                        ▼
                                ┌──────────────────┐
                                │  Web Dashboard   │
                                └──────────────────┘
```

---

## 🎯 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.8+**
- **Docker Desktop** (for running Kafka)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/jjohnson0204/Financial-Sentinel.git
   cd financial-sentinel
   ```

2. **Start Kafka infrastructure**
   ```bash
   docker-compose up -d
   ```

   This will start:
   - Zookeeper on port 2181
   - Kafka broker on port 9092
   - Kafka UI on port 8081

3. **Build the application**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access the dashboard**
   - Open your browser and navigate to: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
   - Kafka UI: `http://localhost:8081`

---

## 💻 Usage

### Ingesting Transactions

The application includes a sample CSV file (`src/main/resources/test.csv`). Transactions can be ingested programmatically:

```java
@Autowired
private IngestionService ingestionService;

// Process transactions from CSV
ingestionService.processTransactionsFromCSV("classpath:test.csv");
```

### CSV Format

```csv
userId,amount,description,timestamp,location
user123,45.99,Lunch at Italian Restaurant,2025-01-15T12:30:00,New York
user456,1200.00,MacBook Pro 14,2025-01-15T14:00:00,San Francisco
```

### Real-Time Monitoring

1. Access the dashboard at `http://localhost:8080`
2. Watch real-time alerts as transactions are processed
3. View risk levels, categories, and coaching recommendations
4. Check the "Top Spenders" leaderboard for analytics

---

## 🔌 API Endpoints

### Transaction API

- **GET** `/api/transactions/summary` - Get spending summary by user
- **GET** `/api/transactions/user/{userId}` - Get transactions for specific user
- **POST** `/api/transactions/ingest` - Ingest new transaction

### Alert API (SSE)

- **GET** `/api/alerts/stream` - Server-Sent Events stream for real-time alerts

### Health & Monitoring

- **GET** `/actuator/health` - Application health check
- **GET** `/h2-console` - H2 Database console

---

## 📁 Project Structure

```
financial-sentinel/
├── src/
│   ├── main/
│   │   ├── java/com/light1111/sentinel/
│   │   │   ├── config/              # Configuration classes
│   │   │   ├── domain/              # Domain models and services
│   │   │   │   ├── model/          # Transaction, CoachingAdvice, etc.
│   │   │   │   ├── service/        # Business logic services
│   │   │   │   └── exception/      # Custom exceptions
│   │   │   ├── infrastructure/      # External integrations
│   │   │   │   ├── kafka/          # Kafka producer/consumer
│   │   │   │   ├── csv/            # CSV parsing
│   │   │   │   └── repository/     # JPA repositories
│   │   │   └── web/                # Controllers and DTOs
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── test.csv
│   │       └── static/
│   │           └── index.html       # Dashboard UI
│   └── test/                        # Test classes
├── docker-compose.yml               # Kafka infrastructure
├── pom.xml                          # Maven dependencies
└── README.md
```

---

## ⚙️ Configuration

### Application Properties

Key configurations in `src/main/resources/application.properties`:

```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:sentineldb
spring.h2.console.enabled=true

# Kafka
spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=sentinel-group

# Virtual Threads (Java 21)
spring.threads.virtual.enabled=true
```

### Environment Variables

You can override properties using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:h2:file:./data/sentinel
export SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

---

## 🧪 Testing

Run the test suite:

```bash
./mvnw test
```

---

## 🐳 Docker Deployment

The project includes Docker Compose configuration for Kafka infrastructure. To scale or customize:

```bash
# Start services in background
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

---

## 🔮 Future Enhancements

- [ ] Load actual PyTorch ML models for anomaly detection
- [ ] Add user authentication and authorization
- [ ] Integrate with real payment gateways
- [ ] Add email/SMS notifications for critical alerts
- [ ] Implement historical trend analysis
- [ ] Add export functionality (PDF reports)
- [ ] Multi-currency support
- [ ] Mobile application

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is available for use under the MIT License.

---

## 👨‍💻 Author

**Financial Sentinel Team**

For questions or support, please open an issue on GitHub.

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Apache Kafka for reliable event streaming
- Deep Java Library (DJL) for ML integration
- The open-source community

---

**Built with ❤️ using Java 21 and Spring Boot 4**
