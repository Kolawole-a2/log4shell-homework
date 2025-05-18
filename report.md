## Log4Shell Exploitation and Mitigation Report

### STRIDE Threat Modeling

| Threat            | Example in Context                                                  | Mitigation                                                   |
|-------------------|----------------------------------------------------------------------|--------------------------------------------------------------|
| Spoofing          | LDAP server impersonating trusted service                           | Validate DNS/IP of external calls                            |
| Tampering         | Injected payload in log parameter (`${jndi:...}`)                    | Disable lookups in logging framework                         |
| Repudiation       | User denies sending malicious input                                 | Log all inputs and store with integrity                      |
| Information Disclosure | Untrusted LDAP reveals Java class info                      | Disable remote class loading                                 |
| DoS               | Flooding endpoint with payloads                                      | Rate limit and alert                                         |
| Privilege Escalation | Exploit runs as system user                                      | Run app with least privileges                                |

### DEFEND & REACT Framework Application
- **DEFEND**: Patch Log4j, isolate containers, sanitize input
- **REACT**: Recognize with logging, contain via firewalls, patch to eliminate

### Reflection
Simulating Log4Shell showed how a simple string can expose systems to RCE. Using Docker helped create a safe, controlled environment to test the vulnerability and understand how different layers of defense play a role in reducing the blast radius of such exploits.


### Simulated Incident Response: REACT Framework CLI Demonstration

Below is a simulated walkthrough of responding to a Log4Shell attack using MITRE's REACT framework and Docker tools:

####  Detect
Check Docker logs for suspicious input like `${jndi:`:
```bash
docker logs <vulnerable_app_container_name> | grep jndi
```

####  Contain
Immediately stop the vulnerable container:
```bash
docker stop <vulnerable_app_container_name>
```

####  Eradicate
Confirm the container is stopped and inspect for residual processes:
```bash
docker ps -a
# Optional: Inspect for strange processes or image layers
```

####  Recover
1. Remove all containers:
```bash
docker-compose down
```

2. Rebuild and relaunch the patched app:
```bash
docker-compose up -d --build
```

3. Verify that normal input no longer causes exploitation:
```bash
curl -X GET "http://localhost:8080/vulnerable?userInput=HelloWorld"
```

#### Summary
- **Detect**: Logs show `${jndi:` attempts
- **Contain**: Stopped affected container
- **Eradicate**: Confirmed no active exploits remain
- **Recover**: Redeployed app with updated libraries and tested normal behavior
