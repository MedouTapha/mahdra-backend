# 🧹 Guide de Nettoyage de la Base de Données Neon

## ⚠️ Problème rencontré

Le paramètre `spring.sql.init.mode=always` a causé l'insertion multiple des données à chaque redémarrage de l'application.

**Résultat:** Au lieu de 5 branches, vous avez maintenant des milliers de doublons.

## ✅ Solution en 3 étapes

### Étape 1: Arrêter l'application Spring Boot

Dans IntelliJ IDEA ou PowerShell:
- Arrêtez l'application en cours d'exécution
- Assurez-vous qu'aucune instance ne tourne

### Étape 2: Nettoyer la base de données Neon

**Option A: Via psql (ligne de commande)**

```bash
# Se connecter à Neon
psql 'postgresql://neondb_owner:npg_rcqzwux0OJ8R@ep-calm-queen-a85ibf5e-pooler.eastus2.azure.neon.tech/neondb?sslmode=require'

# Exécuter le script de nettoyage
\i src/main/resources/cleanup.sql

# Exécuter le script de données
\i src/main/resources/data.sql

# Quitter
\q
```

**Option B: Via interface Neon (recommandé)**

1. Allez sur https://console.neon.tech/
2. Connectez-vous à votre projet
3. Cliquez sur "SQL Editor"
4. Copiez-collez le contenu de `src/main/resources/cleanup.sql`
5. Cliquez sur "Run"
6. Ensuite, copiez-collez le contenu de `src/main/resources/data.sql`
7. Cliquez sur "Run"

**Option C: Via DBeaver ou autre client SQL**

1. Créez une nouvelle connexion avec ces paramètres:
   - **Host:** ep-calm-queen-a85ibf5e-pooler.eastus2.azure.neon.tech
   - **Port:** 5432
   - **Database:** neondb
   - **Username:** neondb_owner
   - **Password:** npg_rcqzwux0OJ8R
   - **SSL:** Require

2. Ouvrez `cleanup.sql` et exécutez-le
3. Ouvrez `data.sql` et exécutez-le

### Étape 3: Vérifier et redémarrer

1. **Vérifier la configuration**

   Dans `application.properties`, assurez-vous que:
   ```properties
   spring.sql.init.mode=never
   ```

2. **Redémarrer l'application**

   ```bash
   mvn clean spring-boot:run
   ```

3. **Tester avec Postman**

   ```
   GET http://localhost:8080/api/branches
   ```

   Vous devriez voir **EXACTEMENT 5 branches** maintenant!

## 📊 Données attendues après nettoyage

- ✅ 5 Branches
- ✅ 10 Classes
- ✅ 10 Donors
- ✅ 10 Commitments
- ✅ 15 Payments
- ✅ 20 Expenses

**Total: 70 enregistrements**

## 🔒 Prévenir ce problème à l'avenir

**RÈGLE D'OR:**
```properties
# Utilisez 'always' UNIQUEMENT pour la PREMIÈRE exécution
# Ensuite, remettez IMMÉDIATEMENT à 'never'
spring.sql.init.mode=never
```

## 🆘 En cas de problème

Si vous voyez encore des doublons:
1. Répétez l'Étape 2 (nettoyage de la base)
2. Vérifiez que `spring.sql.init.mode=never`
3. Redémarrez l'application UNE SEULE FOIS
4. Testez immédiatement avec Postman

## 📝 Vérification rapide

Pour compter les branches dans la base de données:

```sql
SELECT COUNT(*) FROM branches;
-- Résultat attendu: 5
```

Si le résultat est différent de 5, répétez le nettoyage!
