-- Script de nettoyage de la base de données Neon
-- À exécuter UNE SEULE FOIS pour supprimer toutes les données dupliquées

-- Désactiver temporairement les contraintes de clés étrangères
SET session_replication_role = 'replica';

-- Supprimer toutes les données dans l'ordre inverse des dépendances
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

-- Réactiver les contraintes
SET session_replication_role = 'origin';

-- Message de confirmation
SELECT 'Base de données nettoyée avec succès!' AS status;
