# Mahdra Backend

Backend API REST pour Mahdra - Système de gestion des branches et classes mauritaniennes.

## Technologies

- **Java 21**
- **Spring Boot 4.0.0** (dernière version stable)
- **Spring Framework 7**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

## Prérequis

- Java 21 ou supérieur (Java 25 supporté)
- Maven 3.9+
- PostgreSQL 14 ou supérieur

## Nouveautés Spring Boot 4.0.0

Ce projet utilise **Spring Boot 4.0.0**, publié le 20 novembre 2025, qui apporte:

- ✅ **Modularisation complète** - JARs plus petits et ciblés
- ✅ **Support Java 25** - Tout en gardant la compatibilité Java 17+
- ✅ **Spring Framework 7** - Nouvelles fonctionnalités du framework
- ✅ **API Versioning** - Support natif du versioning d'API REST
- ✅ **HTTP Service Clients** - Clients REST améliorés
- ✅ **Performances optimisées** - Meilleure gestion des ressources

## Installation

### 1. Installer Java 21

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-21-jdk

# Vérifier la version
java -version
```

### 2. Installer PostgreSQL

```bash
# Ubuntu/Debian
sudo apt install postgresql postgresql-contrib

# macOS (avec Homebrew)
brew install postgresql@14
brew services start postgresql@14
```

### 3. Créer la base de données

```bash
# Se connecter à PostgreSQL
sudo -u postgres psql

# Créer la base de données
CREATE DATABASE mahdra;

# Quitter psql
\q
```

### 4. Configurer l'application

Modifier `src/main/resources/application.properties` avec vos identifiants PostgreSQL si nécessaire:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mahdra
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 5. Compiler et démarrer l'application

```bash
# Compiler le projet
mvn clean install

# Démarrer l'application
mvn spring-boot:run
```

L'API sera disponible sur `http://localhost:8080`

## Structure de l'API

### Branches

- `GET /api/branches` - Liste toutes les branches
- `GET /api/branches/{id}` - Récupère une branche par ID
- `POST /api/branches` - Crée une nouvelle branche
- `PUT /api/branches/{id}` - Met à jour une branche
- `DELETE /api/branches/{id}` - Supprime une branche

### Donateurs (Donors)

- `GET /api/donors` - Liste tous les donateurs
  - `?actif=true` - Filtre les donateurs actifs
  - `?type=Personne physique` - Filtre par type
- `GET /api/donors/{id}` - Récupère un donateur par ID
- `POST /api/donors` - Crée un nouveau donateur
- `PUT /api/donors/{id}` - Met à jour un donateur
- `DELETE /api/donors/{id}` - Supprime un donateur

### Classes

- `GET /api/classes` - Liste toutes les classes
  - `?branchId=1` - Filtre par branche
  - `?type=Coranique` - Filtre par type
- `GET /api/classes/{id}` - Récupère une classe par ID
- `POST /api/classes` - Crée une nouvelle classe
- `PUT /api/classes/{id}` - Met à jour une classe
- `DELETE /api/classes/{id}` - Supprime une classe

### Engagements (Commitments)

- `GET /api/commitments` - Liste tous les engagements
  - `?donorId=1` - Filtre par donateur
  - `?statut=En cours` - Filtre par statut
- `GET /api/commitments/{id}` - Récupère un engagement par ID
- `POST /api/commitments` - Crée un nouvel engagement
- `PUT /api/commitments/{id}` - Met à jour un engagement
- `DELETE /api/commitments/{id}` - Supprime un engagement

### Paiements (Payments)

- `GET /api/payments` - Liste tous les paiements
  - `?donorId=1` - Filtre par donateur
  - `?classeId=1` - Filtre par classe
  - `?startDate=2024-01-01&endDate=2024-12-31` - Filtre par période
- `GET /api/payments/{id}` - Récupère un paiement par ID
- `POST /api/payments` - Crée un nouveau paiement
- `PUT /api/payments/{id}` - Met à jour un paiement
- `DELETE /api/payments/{id}` - Supprime un paiement
- `GET /api/payments/total/donor/{donorId}` - Total des paiements par donateur
- `GET /api/payments/total/classe/{classeId}` - Total des paiements par classe

### Dépenses (Expenses)

- `GET /api/expenses` - Liste toutes les dépenses
  - `?classId=1` - Filtre par classe
  - `?branchId=1` - Filtre par branche
  - `?period=2024-01` - Filtre par période
- `GET /api/expenses/{id}` - Récupère une dépense par ID
- `POST /api/expenses` - Crée une nouvelle dépense
- `PUT /api/expenses/{id}` - Met à jour une dépense
- `DELETE /api/expenses/{id}` - Supprime une dépense

## Exemples d'utilisation

### Créer une branche

```bash
curl -X POST http://localhost:8080/api/branches \
  -H "Content-Type: application/json" \
  -d '{
    "nomfr": "Nouakchott",
    "nomar": "نواكشوط"
  }'
```

### Créer un donateur

```bash
curl -X POST http://localhost:8080/api/donors \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Diallo",
    "prenom": "Mohamed",
    "email": "mohamed.diallo@example.com",
    "telephone": "+22212345678",
    "type": "Personne physique",
    "actif": true
  }'
```

### Récupérer toutes les classes d'une branche

```bash
curl http://localhost:8080/api/classes?branchId=1
```

### Calculer le total des paiements d'un donateur

```bash
curl http://localhost:8080/api/payments/total/donor/1
```

## Configuration CORS

Le backend est configuré pour accepter les requêtes depuis:
- `http://localhost:4200` (Angular dev server)
- `http://localhost:8080` (Swagger/Tests)

## Base de données

Le schéma de base de données est généré automatiquement par Hibernate (`ddl-auto=update`). Les tables créées:

- `branches` - Branches/Mahoudras
- `classes` - Classes (Coranique ou Franco-arabe)
- `donors` - Donateurs
- `commitments` - Engagements financiers
- `payments` - Paiements
- `expenses` - Dépenses

### Relations

- Une `Branch` peut avoir plusieurs `Classes`
- Un `Donor` peut avoir plusieurs `Commitments` et `Payments`
- Un `Commitment` peut avoir plusieurs `Payments`
- Une `Class` peut avoir plusieurs `Payments` et `Expenses`

## Développement

### Hot Reload

Le projet utilise Spring Boot DevTools pour le hot reload automatique lors des modifications de code.

### Logs

Les logs SQL sont activés en mode DEBUG. Consultez la console pour voir les requêtes SQL générées.

### Format des dates

Toutes les dates utilisent le format ISO 8601: `yyyy-MM-dd` (exemple: `2024-01-15`)

## Tests

```bash
# Exécuter les tests
mvn test

# Exécuter les tests avec coverage
mvn clean test jacoco:report
```

## Production

Pour déployer en production:

1. Créer un fichier `application-prod.properties` avec la configuration de production
2. Compiler le JAR:
   ```bash
   mvn clean package -DskipTests
   ```
3. Exécuter avec le profil de production:
   ```bash
   java -jar -Dspring.profiles.active=prod target/mahdra-backend-1.0.0.jar
   ```

## Stack technique moderne

Ce backend utilise les dernières versions stables:

- **Java 21 LTS** - Support jusqu'en septembre 2028
- **Spring Boot 4.0.0** - Version publiée en novembre 2025
- **Spring Framework 7** - Fondation complètement modulaire
- **PostgreSQL** - Base de données relationnelle robuste

### Avantages de Spring Boot 4.0

1. **Modularisation** - Code mieux organisé, déploiements plus légers
2. **Performance** - Optimisations significatives
3. **API Versioning** - Gestion native des versions d'API
4. **Support Java moderne** - Compatible Java 17 à 25
5. **Sécurité** - Derniers patches et améliorations

## Documentation

- [Guide de migration Angular](ANGULAR_MIGRATION_GUIDE.md) - Comment migrer le frontend de Firebase vers ce backend
- [Spring Boot 4.0 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Release-Notes)
- [Spring Framework 7 Documentation](https://docs.spring.io/spring-framework/docs/7.0.0/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

## Support

Pour toute question ou problème, consultez:
1. Les logs du backend pour les erreurs Java
2. La console du navigateur pour les erreurs frontend
3. L'onglet Network pour les requêtes HTTP

## Licence

Propriétaire
