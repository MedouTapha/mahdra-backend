# Migrations de Base de Données

Ce dossier contient les scripts de migration SQL pour la base de données.

## Comment Appliquer les Migrations

### Méthode 1: Via psql (PostgreSQL)

```bash
psql -h <host> -U <username> -d <database> -f 001_add_date_debut_to_classes.sql
```

### Méthode 2: Via DBeaver ou pgAdmin

1. Connectez-vous à votre base de données
2. Ouvrez le fichier SQL
3. Exécutez le script

### Méthode 3: Automatique avec Flyway (optionnel)

Si vous souhaitez automatiser les migrations, vous pouvez intégrer Flyway:

1. Ajoutez la dépendance dans `pom.xml`:
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

2. Renommez les fichiers selon le format Flyway: `V1__add_date_debut_to_classes.sql`

3. Configurez dans `application.properties`:
```properties
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

## Liste des Migrations

| Fichier | Date | Description |
|---------|------|-------------|
| 001_add_date_debut_to_classes.sql | 2025-11-23 | Ajoute le champ `date_debut` pour la gestion de l'année financière |
