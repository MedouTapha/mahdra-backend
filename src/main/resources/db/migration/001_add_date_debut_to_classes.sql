-- Migration: Ajouter le champ date_debut à la table classes
-- Date: 2025-11-23
-- Description: Ajoute la colonne date_debut pour gérer la date de début de l'année financière

-- Étape 1: Ajouter la colonne date_debut (nullable au début)
ALTER TABLE classes
ADD COLUMN date_debut DATE;

-- Étape 2: Pour les classes existantes, utiliser createdDate comme dateDebut
-- Si createdDate est null, utiliser le 1er janvier de yearStart
UPDATE classes
SET date_debut = COALESCE(created_date, CONCAT(year_start, '-01-01')::DATE)
WHERE date_debut IS NULL;

-- Étape 3: Rendre le champ optionnel (peut rester null pour compatibilité)
-- Si vous voulez le rendre obligatoire plus tard, décommentez:
-- ALTER TABLE classes
-- ALTER COLUMN date_debut SET NOT NULL;

-- Note: Pour créer une nouvelle année financière pour une classe existante,
-- vous pouvez mettre à jour date_debut avec la nouvelle date de début.
