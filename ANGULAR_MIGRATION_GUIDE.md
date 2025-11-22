# Guide de Migration Angular: Firebase → Spring Boot API

Ce guide vous aide à migrer votre frontend Angular de Firebase/Firestore vers le backend Spring Boot.

## Étape 1: Supprimer Firebase

### 1.1 Désinstaller les dépendances Firebase

```bash
cd votre-projet-angular
npm uninstall @angular/fire firebase
```

### 1.2 Nettoyer app.module.ts

Supprimez les imports Firebase dans `src/app/app.module.ts`:

```typescript
// SUPPRIMER ces lignes:
import { provideFirebaseApp, initializeApp } from '@angular/fire/app';
import { provideFirestore, getFirestore } from '@angular/fire/firestore';

// Dans le tableau providers[], SUPPRIMER:
provideFirebaseApp(() => initializeApp(environment.firebase)),
provideFirestore(() => getFirestore()),
```

### 1.3 Nettoyer environment.ts

Supprimez la configuration Firebase dans `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  // SUPPRIMER:
  // firebase: { ... }

  // AJOUTER:
  apiUrl: 'http://localhost:8080/api'
};
```

## Étape 2: Créer le service HTTP de base

Créez `src/app/services/api.service.ts`:

```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  get<T>(endpoint: string, params?: any): Observable<T> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach(key => {
        if (params[key] !== null && params[key] !== undefined) {
          httpParams = httpParams.set(key, params[key].toString());
        }
      });
    }
    return this.http.get<T>(`${this.apiUrl}/${endpoint}`, { params: httpParams });
  }

  post<T>(endpoint: string, data: any): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, data);
  }

  put<T>(endpoint: string, data: any): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${endpoint}`, data);
  }

  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.apiUrl}/${endpoint}`);
  }
}
```

## Étape 3: Mettre à jour les services

### 3.1 BranchesService

Remplacez le contenu de `src/app/services/branches.service.ts`:

```typescript
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Branch } from '../models/branch.model';

@Injectable({
  providedIn: 'root'
})
export class BranchesService {
  private endpoint = 'branches';

  constructor(private api: ApiService) {}

  getAllBranches(): Observable<Branch[]> {
    return this.api.get<Branch[]>(this.endpoint);
  }

  getBranchById(id: number): Observable<Branch> {
    return this.api.get<Branch>(`${this.endpoint}/${id}`);
  }

  createBranch(branch: Branch): Observable<Branch> {
    return this.api.post<Branch>(this.endpoint, branch);
  }

  updateBranch(id: number, branch: Branch): Observable<Branch> {
    return this.api.put<Branch>(`${this.endpoint}/${id}`, branch);
  }

  deleteBranch(id: number): Observable<void> {
    return this.api.delete<void>(`${this.endpoint}/${id}`);
  }

  searchBranches(keyword: string): Observable<Branch[]> {
    return this.api.get<Branch[]>(this.endpoint, { search: keyword });
  }
}
```

### 3.2 DonorsService

Remplacez le contenu de `src/app/services/donors.service.ts`:

```typescript
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Donor } from '../models/donor.model';

@Injectable({
  providedIn: 'root'
})
export class DonorsService {
  private endpoint = 'donors';

  constructor(private api: ApiService) {}

  getAllDonors(): Observable<Donor[]> {
    return this.api.get<Donor[]>(this.endpoint);
  }

  getDonorById(id: number): Observable<Donor> {
    return this.api.get<Donor>(`${this.endpoint}/${id}`);
  }

  createDonor(donor: Donor): Observable<Donor> {
    return this.api.post<Donor>(this.endpoint, donor);
  }

  updateDonor(id: number, donor: Donor): Observable<Donor> {
    return this.api.put<Donor>(`${this.endpoint}/${id}`, donor);
  }

  deleteDonor(id: number): Observable<void> {
    return this.api.delete<void>(`${this.endpoint}/${id}`);
  }

  getActiveDonors(): Observable<Donor[]> {
    return this.api.get<Donor[]>(this.endpoint, { actif: true });
  }

  getDonorsByType(type: string): Observable<Donor[]> {
    return this.api.get<Donor[]>(this.endpoint, { type });
  }
}
```

### 3.3 ClassesService

Remplacez le contenu de `src/app/services/classes.service.ts`:

```typescript
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Class } from '../models/class.model';

@Injectable({
  providedIn: 'root'
})
export class ClassesService {
  private endpoint = 'classes';

  constructor(private api: ApiService) {}

  getAllClasses(): Observable<Class[]> {
    return this.api.get<Class[]>(this.endpoint);
  }

  getClassById(id: number): Observable<Class> {
    return this.api.get<Class>(`${this.endpoint}/${id}`);
  }

  createClass(classData: Class): Observable<Class> {
    return this.api.post<Class>(this.endpoint, classData);
  }

  updateClass(id: number, classData: Class): Observable<Class> {
    return this.api.put<Class>(`${this.endpoint}/${id}`, classData);
  }

  deleteClass(id: number): Observable<void> {
    return this.api.delete<void>(`${this.endpoint}/${id}`);
  }

  getClassesByBranch(branchId: number): Observable<Class[]> {
    return this.api.get<Class[]>(this.endpoint, { branchId });
  }

  getClassesByType(type: string): Observable<Class[]> {
    return this.api.get<Class[]>(this.endpoint, { type });
  }
}
```

### 3.4 CommitmentsService

Remplacez le contenu de `src/app/services/commitments.service.ts`:

```typescript
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Commitment } from '../models/commitment.model';

@Injectable({
  providedIn: 'root'
})
export class CommitmentsService {
  private endpoint = 'commitments';

  constructor(private api: ApiService) {}

  getAllCommitments(): Observable<Commitment[]> {
    return this.api.get<Commitment[]>(this.endpoint);
  }

  getCommitmentById(id: number): Observable<Commitment> {
    return this.api.get<Commitment>(`${this.endpoint}/${id}`);
  }

  createCommitment(commitment: Commitment): Observable<Commitment> {
    return this.api.post<Commitment>(this.endpoint, commitment);
  }

  updateCommitment(id: number, commitment: Commitment): Observable<Commitment> {
    return this.api.put<Commitment>(`${this.endpoint}/${id}`, commitment);
  }

  deleteCommitment(id: number): Observable<void> {
    return this.api.delete<void>(`${this.endpoint}/${id}`);
  }

  getCommitmentsByDonor(donorId: number): Observable<Commitment[]> {
    return this.api.get<Commitment[]>(this.endpoint, { donorId });
  }

  getCommitmentsByStatut(statut: string): Observable<Commitment[]> {
    return this.api.get<Commitment[]>(this.endpoint, { statut });
  }
}
```

### 3.5 PaymentsService

Remplacez le contenu de `src/app/services/payments.service.ts`:

```typescript
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Payment } from '../models/payment.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {
  private endpoint = 'payments';

  constructor(private api: ApiService) {}

  getAllPayments(): Observable<Payment[]> {
    return this.api.get<Payment[]>(this.endpoint);
  }

  getPaymentById(id: number): Observable<Payment> {
    return this.api.get<Payment>(`${this.endpoint}/${id}`);
  }

  createPayment(payment: Payment): Observable<Payment> {
    return this.api.post<Payment>(this.endpoint, payment);
  }

  updatePayment(id: number, payment: Payment): Observable<Payment> {
    return this.api.put<Payment>(`${this.endpoint}/${id}`, payment);
  }

  deletePayment(id: number): Observable<void> {
    return this.api.delete<void>(`${this.endpoint}/${id}`);
  }

  getPaymentsByDonor(donorId: number): Observable<Payment[]> {
    return this.api.get<Payment[]>(this.endpoint, { donorId });
  }

  getPaymentsByClasse(classeId: number): Observable<Payment[]> {
    return this.api.get<Payment[]>(this.endpoint, { classeId });
  }

  getPaymentsByDateRange(startDate: string, endDate: string): Observable<Payment[]> {
    return this.api.get<Payment[]>(this.endpoint, { startDate, endDate });
  }

  getTotalPaymentsByDonor(donorId: number): Observable<number> {
    return this.api.get<number>(`${this.endpoint}/total/donor/${donorId}`);
  }

  getTotalPaymentsByClasse(classeId: number): Observable<number> {
    return this.api.get<number>(`${this.endpoint}/total/classe/${classeId}`);
  }
}
```

### 3.6 ExpensesService

Remplacez le contenu de `src/app/services/expenses.service.ts`:

```typescript
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Expense } from '../models/expense.model';

@Injectable({
  providedIn: 'root'
})
export class ExpensesService {
  private endpoint = 'expenses';

  constructor(private api: ApiService) {}

  getAllExpenses(): Observable<Expense[]> {
    return this.api.get<Expense[]>(this.endpoint);
  }

  getExpenseById(id: number): Observable<Expense> {
    return this.api.get<Expense>(`${this.endpoint}/${id}`);
  }

  createExpense(expense: Expense): Observable<Expense> {
    return this.api.post<Expense>(this.endpoint, expense);
  }

  updateExpense(id: number, expense: Expense): Observable<Expense> {
    return this.api.put<Expense>(`${this.endpoint}/${id}`, expense);
  }

  deleteExpense(id: number): Observable<void> {
    return this.api.delete<void>(`${this.endpoint}/${id}`);
  }

  getExpensesByClass(classId: number): Observable<Expense[]> {
    return this.api.get<Expense[]>(this.endpoint, { classId });
  }

  getExpensesByBranch(branchId: number): Observable<Expense[]> {
    return this.api.get<Expense[]>(this.endpoint, { branchId });
  }

  getExpensesByPeriod(period: string): Observable<Expense[]> {
    return this.api.get<Expense[]>(this.endpoint, { period });
  }
}
```

## Étape 4: Mettre à jour les Models

Les models doivent maintenant utiliser `id: number` au lieu de `id: string`:

### Exemple: Branch Model

```typescript
export interface Branch {
  id?: number;  // Changé de string à number
  nomfr: string;
  nomar: string;
}
```

Faites la même chose pour tous les models (Donor, Class, Commitment, Payment, Expense).

## Étape 5: Démarrer les serveurs

### Terminal 1: Backend Spring Boot

```bash
cd mahdra-backend
mvn spring-boot:run
```

Le backend démarre sur `http://localhost:8080`

### Terminal 2: Frontend Angular

```bash
cd votre-projet-angular
npm start
```

Le frontend démarre sur `http://localhost:4200`

## Étape 6: Tester

1. Ouvrez `http://localhost:4200` dans votre navigateur
2. Vérifiez que les données s'affichent correctement
3. Testez la création, modification et suppression de données

## Points à vérifier

- [ ] Firebase complètement supprimé du projet
- [ ] Tous les services utilisent maintenant ApiService
- [ ] Les models utilisent `id: number` au lieu de `id: string`
- [ ] `environment.ts` contient `apiUrl`
- [ ] Le backend Spring Boot est démarré
- [ ] Les appels API fonctionnent dans la console du navigateur

## Troubleshooting

### Erreur CORS

Si vous voyez des erreurs CORS dans la console:
- Vérifiez que le backend est bien démarré
- Le backend est configuré pour accepter les requêtes de `http://localhost:4200`

### Erreur 404

Si vous obtenez des 404:
- Vérifiez que l'URL de l'API dans `environment.ts` est correcte
- Vérifiez que les endpoints dans les services correspondent aux controllers

### Erreur de type

Si vous avez des erreurs de type `id`:
- Assurez-vous que tous les models utilisent `id?: number` et non `id?: string`

## Support

Si vous rencontrez des problèmes, vérifiez:
1. La console du backend (erreurs Java)
2. La console du navigateur (erreurs Angular/HTTP)
3. L'onglet Network pour voir les requêtes HTTP
