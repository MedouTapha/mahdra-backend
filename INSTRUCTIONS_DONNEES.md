# Instructions pour insérer les données de test

Vous avez **2 options** pour insérer les données dans la base de données:

## Option 1: Automatique via Spring Boot (Recommandé)

### Étape 1: Modifier application.properties

Ouvrez `src/main/resources/application.properties` et changez:

```properties
# Changez cette ligne de 'never' à 'always'
spring.sql.init.mode=always
```

### Étape 2: Redémarrer l'application

```bash
# Arrêter l'application (Ctrl+C)
# Redémarrer
mvn spring-boot:run
```

Les données seront automatiquement insérées au démarrage!

### Étape 3: IMPORTANT - Remettre en 'never'

Après le premier démarrage réussi, remettez `spring.sql.init.mode=never` pour éviter les erreurs de doublons aux prochains démarrages.

---

## Option 2: Manuelle via psql

Si vous préférez insérer les données manuellement avec psql:

```bash
# Se connecter à PostgreSQL
psql -U postgres -d mahdra

# Exécuter le script
\i src/main/resources/data.sql

# Ou depuis PowerShell/CMD
psql -U postgres -d mahdra -f src/main/resources/data.sql
```

---

## Vérifier que les données sont insérées

### Via API REST

```bash
# Vérifier les branches (devrait retourner 5 branches)
curl http://localhost:8080/api/branches

# Vérifier les classes (devrait retourner 10 classes)
curl http://localhost:8080/api/classes

# Vérifier les donateurs (devrait retourner 10 donateurs)
curl http://localhost:8080/api/donors

# Vérifier les engagements (devrait retourner 10 engagements)
curl http://localhost:8080/api/commitments

# Vérifier les paiements (devrait retourner 15 paiements)
curl http://localhost:8080/api/payments

# Vérifier les dépenses (devrait retourner 20 dépenses)
curl http://localhost:8080/api/expenses
```

### Via psql

```sql
-- Se connecter
psql -U postgres -d mahdra

-- Compter les enregistrements
SELECT 'Branches' as table_name, COUNT(*) as count FROM branches
UNION ALL
SELECT 'Classes', COUNT(*) FROM classes
UNION ALL
SELECT 'Donors', COUNT(*) FROM donors
UNION ALL
SELECT 'Commitments', COUNT(*) FROM commitments
UNION ALL
SELECT 'Payments', COUNT(*) FROM payments
UNION ALL
SELECT 'Expenses', COUNT(*) FROM expenses;
```

Vous devriez voir:
- **5 branches**
- **10 classes**
- **10 donateurs**
- **10 engagements**
- **15 paiements**
- **20 dépenses**

---

## Données incluses

### Branches (5)
1. Nouakchott
2. Nouadhibou
3. Kaedi
4. Rosso
5. Kiffa

### Classes (10)
- 4 classes à Nouakchott (2 coraniques, 2 franco-arabes)
- 2 classes à Nouadhibou
- 2 classes à Kaedi
- 1 classe à Rosso
- 1 classe à Kiffa

### Donateurs (10)
- 5 personnes physiques
- 5 personnes morales (associations, fondations, entreprises)

### Engagements (10)
- Statuts: En cours, Payé, En retard
- Montants: de 25,000 à 300,000 MRU

### Paiements (15)
- Janvier à Avril 2024
- Modes: Virement, Espèces, Chèque, Mobile Money
- Total des paiements: 595,000 MRU

### Dépenses (20)
- Types: Salaire, Fournitures, Infrastructure, Autre
- Périodes: Janvier à Mars 2024
- Total des dépenses: 443,000 MRU

---

## Réinitialiser les données

Si vous voulez recommencer à zéro:

```sql
-- Se connecter à psql
psql -U postgres -d mahdra

-- Supprimer toutes les données (dans l'ordre pour respecter les contraintes)
DELETE FROM expenses;
DELETE FROM payments;
DELETE FROM commitments;
DELETE FROM donors;
DELETE FROM classes;
DELETE FROM branches;

-- Réinitialiser les séquences
ALTER SEQUENCE branches_id_seq RESTART WITH 1;
ALTER SEQUENCE classes_id_seq RESTART WITH 1;
ALTER SEQUENCE donors_id_seq RESTART WITH 1;
ALTER SEQUENCE commitments_id_seq RESTART WITH 1;
ALTER SEQUENCE payments_id_seq RESTART WITH 1;
ALTER SEQUENCE expenses_id_seq RESTART WITH 1;
```

Ensuite, réexécutez le script `data.sql` avec l'Option 1 ou 2.
