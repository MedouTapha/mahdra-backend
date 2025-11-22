# Guide de Configuration - Mahdra Backend

## Profils Disponibles

Le projet utilise les profils Spring Boot pour gérer différents environnements:

### 1. **Profil `dev` (Développement Local)** ✅ PAR DÉFAUT

Configuration pour le développement local avec PostgreSQL installé localement.

**Base de données:** `localhost:5432/mahdra`

**Activation:**
```properties
# Dans application.properties
spring.profiles.active=dev
```

**Prérequis:**
- PostgreSQL installé et démarré localement
- Base de données `mahdra` créée
- Utilisateur: `postgres` / Mot de passe: `postgres`

**Commandes Windows:**
```bash
# Démarrer PostgreSQL
net start postgresql

# Créer la base de données (dans psql)
CREATE DATABASE mahdra;
```

---

### 2. **Profil `neon` (Cloud Neon)**

Configuration pour utiliser la base de données Neon dans le cloud.

**Base de données:** Neon PostgreSQL (Azure East US 2)

**Activation:**
```properties
# Dans application.properties
spring.profiles.active=neon
```

**Configuration:**
1. Copiez `.env.example` vers `.env`
2. Mettez à jour les identifiants Neon dans `.env`:
```bash
NEON_DATABASE_URL=jdbc:postgresql://...
NEON_DATABASE_USERNAME=neondb_owner
NEON_DATABASE_PASSWORD=votre_mot_de_passe
```
3. Chargez les variables d'environnement avant de démarrer l'application

**Sous Windows (PowerShell):**
```powershell
Get-Content .env | ForEach-Object {
    if ($_ -match '^([^=]+)=(.*)$') {
        [Environment]::SetEnvironmentVariable($matches[1], $matches[2], 'Process')
    }
}
```

**Sous Linux/Mac:**
```bash
export $(cat .env | xargs)
```

---

### 3. **Profil `prod` (Production)**

Configuration pour l'environnement de production.

**Activation:**
```properties
# Dans application.properties
spring.profiles.active=prod
```

**Sécurité:**
- Utilise des variables d'environnement pour tous les identifiants
- `ddl-auto=validate` (pas de modification de schéma)
- Logs minimaux
- Pas de stacktraces exposées

---

## Changement de Profil

Pour changer de profil, éditez le fichier `src/main/resources/application.properties`:

```properties
# Option 1: Développement local
spring.profiles.active=dev

# Option 2: Cloud Neon
spring.profiles.active=neon

# Option 3: Production
spring.profiles.active=prod
```

**OU** via variable d'environnement:
```bash
export SPRING_PROFILES_ACTIVE=neon
```

**OU** via argument de démarrage:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=neon
```

---

## Résumé Rapide

| Profil | Base de données | Variables d'env requises | Cas d'usage |
|--------|----------------|-------------------------|-------------|
| `dev` | localhost:5432 | ❌ Aucune | Développement local |
| `neon` | Neon Cloud | ✅ NEON_DATABASE_* | Test cloud / Démo |
| `prod` | À configurer | ✅ DATABASE_* | Production |

---

## Problème: Branche avec Configuration Différente

Si vous constatez que différentes branches ont des configurations différentes:

1. **Restez sur la branche `main`** - elle a la meilleure architecture avec profils
2. **N'utilisez PAS la branche `claude/spring-boot-backend-migration-013bDDtjEFoeKxAAoXEzgJTx`** pour la configuration - elle a des identifiants hardcodés
3. Pour utiliser Neon depuis `main`: activez simplement le profil `neon`

---

## Sécurité

⚠️ **IMPORTANT:**
- Ne committez JAMAIS le fichier `.env` dans git
- Ne hardcodez JAMAIS les mots de passe dans `application.properties`
- Utilisez toujours des variables d'environnement pour les identifiants sensibles
- Le fichier `.env` est déjà dans `.gitignore`
