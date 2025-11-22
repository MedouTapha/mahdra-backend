# 🚀 Configuration Neon Cloud - Guide Complet

## ✅ Étapes Complétées

1. ✅ Profil changé à `neon` dans `application.properties`
2. ✅ Fichier `.env` créé avec vos credentials Neon

## 📋 Configuration dans IntelliJ IDEA

### Méthode 1: Variables d'Environnement dans IntelliJ (RECOMMANDÉ)

1. **Ouvrez la configuration Run/Debug:**
   - Cliquez sur le menu déroulant à côté du bouton Run (en haut à droite)
   - Sélectionnez "Edit Configurations..."

2. **Ajoutez les variables d'environnement:**
   - Dans la fenêtre qui s'ouvre, trouvez votre configuration `MahdraBackendApplication`
   - Cherchez le champ **"Environment variables"**
   - Cliquez sur l'icône de dossier à droite du champ
   - Ajoutez les 3 variables suivantes:

   ```
   NEON_DATABASE_URL=jdbc:postgresql://ep-calm-queen-a85ibf5e-pooler.eastus2.azure.neon.tech/neondb?sslmode=require&options=-c%20channel_binding=require
   NEON_DATABASE_USERNAME=neondb_owner
   NEON_DATABASE_PASSWORD=npg_rcqzwux0OJ8R
   ```

3. **Appliquez et lancez:**
   - Cliquez sur "Apply" puis "OK"
   - Lancez l'application normalement ▶️

### Méthode 2: Plugin EnvFile pour IntelliJ

1. **Installez le plugin EnvFile:**
   - File → Settings → Plugins
   - Recherchez "EnvFile"
   - Installez et redémarrez IntelliJ

2. **Configurez le plugin:**
   - Edit Configurations → Onglet "EnvFile"
   - Cliquez sur "+" pour ajouter un fichier
   - Sélectionnez votre fichier `.env`
   - Apply → OK

3. **Lancez l'application** ▶️

### Méthode 3: Ligne de Commande (Alternative)

Si vous préférez utiliser Maven en ligne de commande:

**PowerShell:**
```powershell
# Charger les variables d'environnement
$env:NEON_DATABASE_URL="jdbc:postgresql://ep-calm-queen-a85ibf5e-pooler.eastus2.azure.neon.tech/neondb?sslmode=require&options=-c%20channel_binding=require"
$env:NEON_DATABASE_USERNAME="neondb_owner"
$env:NEON_DATABASE_PASSWORD="npg_rcqzwux0OJ8R"

# Lancer l'application
mvn spring-boot:run
```

**Ou créez un script PowerShell `start-neon.ps1`:**
```powershell
# start-neon.ps1
Get-Content .env | ForEach-Object {
    if ($_ -match '^([^=]+)=(.*)$') {
        [Environment]::SetEnvironmentVariable($matches[1], $matches[2], 'Process')
    }
}
mvn spring-boot:run
```

Puis exécutez:
```powershell
.\start-neon.ps1
```

---

## 🔍 Vérification

Après avoir configuré et lancé l'application, vous devriez voir dans les logs:

```
The following 1 profile is active: "neon"
HikariPool-1 - Starting...
HikariPool-1 - Start completed.
```

**Au lieu de l'erreur:**
```
ERROR: password authentication failed for user 'neondb_owner'
```

---

## 🔄 Basculer entre Local et Neon

### Pour utiliser LOCAL (PostgreSQL local):
```properties
# Dans application.properties
spring.profiles.active=dev
```

### Pour utiliser NEON (Cloud):
```properties
# Dans application.properties
spring.profiles.active=neon
```

---

## ⚠️ Sécurité

- ✅ Le fichier `.env` est déjà dans `.gitignore`
- ✅ Vos credentials ne seront JAMAIS committés dans git
- ⚠️ Ne partagez JAMAIS le contenu de `.env` publiquement

---

## 🆘 Dépannage

### Erreur: "password authentication failed"
→ Les variables d'environnement ne sont pas chargées
→ Suivez la **Méthode 1** ci-dessus

### Erreur: "Connection refused"
→ Vérifiez votre connexion Internet
→ Vérifiez que l'URL Neon est correcte

### Application démarre mais erreur de connexion
→ Le mot de passe Neon a peut-être expiré
→ Vérifiez vos credentials sur le dashboard Neon
