-- Script d'insertion des données de test pour Mahdra Backend
-- Ce fichier sera exécuté automatiquement au démarrage de Spring Boot

-- ========================================
-- 1. BRANCHES (Mahoudras)
-- ========================================
INSERT INTO branches (id, nomfr, nomar) VALUES
(1, 'Nouakchott', 'نواكشوط'),
(2, 'Nouadhibou', 'نواذيبو'),
(3, 'Kaedi', 'كيهيدي'),
(4, 'Rosso', 'روصو'),
(5, 'Kiffa', 'كيفة');

-- ========================================
-- 2. CLASSES
-- ========================================
INSERT INTO classes (id, name, type, year_start, created_date, branch_id, niveau, student_count, teacher, description, active) VALUES
-- Classes Nouakchott
(1, 'Classe Coranique Al-Fath', 'Coranique', 2024, '2024-01-15', 1, 'Débutant', 25, 'Cheikh Mohamed Ould Abdallah', 'Apprentissage du Coran pour débutants', true),
(2, 'Classe Franco-Arabe CP1', 'Franco-arabe', 2024, '2024-01-15', 1, 'CP1', 30, 'Fatimetou Mint Ahmed', 'Classe de CP1 bilingue', true),
(3, 'Classe Franco-Arabe CE1', 'Franco-arabe', 2023, '2023-09-01', 1, 'CE1', 28, 'Mamadou Ba', 'Classe de CE1 bilingue', true),
(4, 'Classe Coranique Al-Nour', 'Coranique', 2024, '2024-02-01', 1, 'Intermédiaire', 20, 'Cheikh Oumar Ould Sidi', 'Mémorisation et tajwid', true),

-- Classes Nouadhibou
(5, 'Classe Coranique Al-Iman', 'Coranique', 2024, '2024-01-20', 2, 'Débutant', 18, 'Cheikh Ahmed Ould Mohamed', 'Introduction au Coran', true),
(6, 'Classe Franco-Arabe CP2', 'Franco-arabe', 2024, '2024-01-20', 2, 'CP2', 22, 'Aissatou Diallo', 'Classe de CP2', true),

-- Classes Kaedi
(7, 'Classe Coranique Al-Hidaya', 'Coranique', 2023, '2023-10-01', 3, 'Avancé', 15, 'Cheikh Mahmoud Ould Abdel', 'Niveau avancé de mémorisation', true),
(8, 'Classe Franco-Arabe CE2', 'Franco-arabe', 2024, '2024-01-10', 3, 'CE2', 25, 'Mariam Mint Sidi', 'Classe de CE2', true),

-- Classes Rosso
(9, 'Classe Coranique Al-Baraka', 'Coranique', 2024, '2024-02-15', 4, 'Débutant', 20, 'Cheikh Abdellahi Ould Hamza', 'Apprentissage de base', true),

-- Classes Kiffa
(10, 'Classe Franco-Arabe CP1', 'Franco-arabe', 2024, '2024-01-25', 5, 'CP1', 24, 'Khadija Mint Mohamed', 'Classe de CP1', true);

-- ========================================
-- 3. DONATEURS (DONORS)
-- ========================================
INSERT INTO donors (id, nom, prenom, email, telephone, adresse, type, actif, date_inscription) VALUES
-- Personnes physiques
(1, 'Ould Ahmed', 'Mohamed', 'mohamed.ouldahmed@example.mr', '+22245678901', 'Tevragh Zeina, Nouakchott', 'Personne physique', true, '2024-01-10'),
(2, 'Mint Sidi', 'Fatimetou', 'fatimetou.mintsidi@example.mr', '+22245678902', 'Ksar, Nouakchott', 'Personne physique', true, '2024-01-15'),
(3, 'Ba', 'Mamadou', 'mamadou.ba@example.mr', '+22245678903', 'Cinquième, Nouakchott', 'Personne physique', true, '2024-01-20'),
(4, 'Diallo', 'Aissatou', 'aissatou.diallo@example.mr', '+22245678904', 'Nouadhibou Centre', 'Personne physique', true, '2024-02-01'),
(5, 'Ould Sidi', 'Cheikh', 'cheikh.ouldsidi@example.mr', '+22245678905', 'Kaedi', 'Personne physique', true, '2024-02-05'),

-- Personnes morales
(6, 'Association Al-Ihsan', 'N/A', 'contact@alihsan.mr', '+22245678906', 'Route de Rosso, Nouakchott', 'Personne morale', true, '2024-01-05'),
(7, 'Fondation Mauritanienne', 'N/A', 'info@fondation-mr.mr', '+22245678907', 'Avenue Kennedy, Nouakchott', 'Personne morale', true, '2024-01-12'),
(8, 'ONG Education Pour Tous', 'N/A', 'contact@educationpourtous.mr', '+22245678908', 'Nouadhibou', 'Personne morale', true, '2024-01-18'),
(9, 'Banque Al-Baraka', 'N/A', 'corporate@albaraka.mr', '+22245678909', 'Centre-ville, Nouakchott', 'Personne morale', true, '2024-02-10'),
(10, 'Société Minière SNIM', 'N/A', 'rse@snim.mr', '+22245678910', 'Nouadhibou', 'Personne morale', true, '2024-02-15');

-- ========================================
-- 4. ENGAGEMENTS (COMMITMENTS)
-- ========================================
INSERT INTO commitments (id, donor_id, montant, date_engagement, date_echeance, statut, description) VALUES
-- Engagements en cours
(1, 1, 50000, '2024-01-15', '2024-12-31', 'En cours', 'Engagement annuel pour soutien à l''éducation coranique'),
(2, 2, 30000, '2024-01-20', '2024-06-30', 'En cours', 'Soutien au développement des classes franco-arabes'),
(3, 6, 200000, '2024-01-10', '2024-12-31', 'En cours', 'Partenariat annuel pour toutes les branches'),
(4, 7, 150000, '2024-01-25', '2024-12-31', 'En cours', 'Support aux infrastructures éducatives'),

-- Engagements payés
(5, 3, 25000, '2024-01-10', '2024-03-31', 'Payé', 'Don ponctuel pour fournitures scolaires'),
(6, 4, 40000, '2024-02-01', '2024-05-31', 'Payé', 'Soutien trimestriel'),
(7, 8, 100000, '2024-01-15', '2024-06-30', 'Payé', 'Financement de 3 classes'),

-- Engagements en retard
(8, 5, 35000, '2023-12-01', '2024-03-31', 'En retard', 'Engagement de fin d''année'),

-- Engagements futurs
(9, 9, 300000, '2024-03-01', '2025-03-01', 'En cours', 'Engagement annuel entreprise'),
(10, 10, 250000, '2024-02-20', '2025-02-20', 'En cours', 'Programme RSE SNIM');

-- ========================================
-- 5. PAIEMENTS (PAYMENTS)
-- ========================================
INSERT INTO payments (id, donor_id, commitment_id, classe_id, montant, date_paiement, mode_paiement, reference, remarque) VALUES
-- Paiements de janvier 2024
(1, 1, 1, 1, 10000, '2024-01-15', 'Virement', 'VIR20240115001', 'Premier versement de l''engagement annuel'),
(2, 2, 2, 2, 15000, '2024-01-20', 'Espèces', NULL, 'Paiement en espèces'),
(3, 3, 5, 3, 25000, '2024-01-25', 'Chèque', 'CHQ001234', 'Don complet'),
(4, 6, 3, 1, 50000, '2024-01-28', 'Virement', 'VIR20240128001', 'Premier versement Association Al-Ihsan'),

-- Paiements de février 2024
(5, 4, 6, 5, 20000, '2024-02-05', 'Virement', 'VIR20240205001', 'Premier versement'),
(6, 1, 1, 1, 10000, '2024-02-15', 'Virement', 'VIR20240215001', 'Deuxième versement'),
(7, 6, 3, 2, 50000, '2024-02-20', 'Virement', 'VIR20240220001', 'Deuxième versement Association'),
(8, 7, 4, 3, 50000, '2024-02-25', 'Virement', 'VIR20240225001', 'Premier versement Fondation'),

-- Paiements de mars 2024
(9, 4, 6, 5, 20000, '2024-03-05', 'Mobile Money', 'MM20240305001', 'Deuxième versement via Sedad'),
(10, 8, 7, 4, 100000, '2024-03-10', 'Virement', 'VIR20240310001', 'Paiement complet ONG'),
(11, 1, 1, 4, 10000, '2024-03-15', 'Virement', 'VIR20240315001', 'Troisième versement'),
(12, 7, 4, 6, 50000, '2024-03-20', 'Virement', 'VIR20240320001', 'Deuxième versement Fondation'),

-- Paiements d'avril 2024
(13, 1, 1, 1, 10000, '2024-04-15', 'Virement', 'VIR20240415001', 'Quatrième versement'),
(14, 2, 2, 2, 15000, '2024-04-20', 'Espèces', NULL, 'Deuxième versement'),
(15, 6, 3, 7, 50000, '2024-04-25', 'Virement', 'VIR20240425001', 'Troisième versement Association');

-- ========================================
-- 6. DÉPENSES (EXPENSES)
-- ========================================
INSERT INTO expenses (id, class_entity_id, branch_id, montant, date, type, description, period, beneficiaire) VALUES
-- Dépenses Nouakchott - Janvier 2024
(1, 1, 1, 15000, '2024-01-15', 'Fournitures', 'Achat de livres coraniques et ardoises', '2024-01', 'Librairie Al-Qalam'),
(2, 2, 1, 12000, '2024-01-20', 'Fournitures', 'Cahiers, stylos et matériel scolaire', '2024-01', 'Papeterie Moderne'),
(3, 1, 1, 25000, '2024-01-31', 'Salaire', 'Salaire enseignant janvier', '2024-01', 'Cheikh Mohamed Ould Abdallah'),
(4, 2, 1, 30000, '2024-01-31', 'Salaire', 'Salaire enseignante janvier', '2024-01', 'Fatimetou Mint Ahmed'),

-- Dépenses Nouakchott - Février 2024
(5, 3, 1, 28000, '2024-02-28', 'Salaire', 'Salaire enseignant février', '2024-02', 'Mamadou Ba'),
(6, 4, 1, 25000, '2024-02-28', 'Salaire', 'Salaire enseignant février', '2024-02', 'Cheikh Oumar Ould Sidi'),
(7, 1, 1, 8000, '2024-02-15', 'Infrastructure', 'Réparation de tables et chaises', '2024-02', 'Menuiserie Al-Amal'),

-- Dépenses Nouadhibou - Janvier/Février 2024
(8, 5, 2, 24000, '2024-01-31', 'Salaire', 'Salaire enseignant janvier', '2024-01', 'Cheikh Ahmed Ould Mohamed'),
(9, 6, 2, 26000, '2024-01-31', 'Salaire', 'Salaire enseignante janvier', '2024-01', 'Aissatou Diallo'),
(10, 5, 2, 10000, '2024-02-10', 'Fournitures', 'Matériel pédagogique', '2024-02', 'Librairie Nouadhibou'),

-- Dépenses Kaedi - Février 2024
(11, 7, 3, 27000, '2024-02-28', 'Salaire', 'Salaire enseignant février', '2024-02', 'Cheikh Mahmoud Ould Abdel'),
(12, 8, 3, 29000, '2024-02-28', 'Salaire', 'Salaire enseignante février', '2024-02', 'Mariam Mint Sidi'),
(13, 7, 3, 15000, '2024-02-20', 'Infrastructure', 'Installation ventilateurs', '2024-02', 'Électricité Kaedi'),

-- Dépenses Rosso - Février 2024
(14, 9, 4, 25000, '2024-02-28', 'Salaire', 'Salaire enseignant février', '2024-02', 'Cheikh Abdellahi Ould Hamza'),
(15, 9, 4, 9000, '2024-02-18', 'Fournitures', 'Livres coraniques', '2024-02', 'Librairie Islamique Rosso'),

-- Dépenses Kiffa - Mars 2024
(16, 10, 5, 28000, '2024-03-31', 'Salaire', 'Salaire enseignante mars', '2024-03', 'Khadija Mint Mohamed'),
(17, 10, 5, 11000, '2024-03-15', 'Fournitures', 'Matériel scolaire complet', '2024-03', 'Papeterie Kiffa'),

-- Dépenses générales branches - Mars 2024
(18, NULL, 1, 20000, '2024-03-10', 'Infrastructure', 'Peinture et rénovation', '2024-03', 'Entreprise Bâtiment'),
(19, NULL, 2, 18000, '2024-03-15', 'Autre', 'Facture eau et électricité', '2024-03', 'SOMELEC'),
(20, NULL, 3, 12000, '2024-03-20', 'Autre', 'Entretien et nettoyage', '2024-03', 'Service Nettoyage Kaedi');

-- Réinitialiser les séquences auto-increment
SELECT setval('branches_id_seq', (SELECT MAX(id) FROM branches));
SELECT setval('classes_id_seq', (SELECT MAX(id) FROM classes));
SELECT setval('donors_id_seq', (SELECT MAX(id) FROM donors));
SELECT setval('commitments_id_seq', (SELECT MAX(id) FROM commitments));
SELECT setval('payments_id_seq', (SELECT MAX(id) FROM payments));
SELECT setval('expenses_id_seq', (SELECT MAX(id) FROM expenses));
