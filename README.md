# Hawkins Library — Application Android

Bibliothèque personnelle native Android pour les vidéos et livres de **David R. Hawkins**.

## 📱 Fonctionnalités

- **Accueil** : tableau de bord avec accès rapide
- **Vidéos** : liste automatique des vidéos du dossier Google Drive, recherche, vue liste/grille
- **Lecteur vidéo natif** : Media3 ExoPlayer, plein écran, +10s/-10s
- **Livres** : import PDF/EPUB depuis le téléphone, lecteur intégré
- **Paramètres** : état de la configuration

## 🔧 Configuration Google Drive API

### Étape 1 — Créer une clé API
1. Va sur https://console.cloud.google.com/
2. Crée un projet (ex. « Hawkins Library »)
3. Menu → **API et services** → **Bibliothèque**
4. Cherche **Google Drive API** → **Activer**
5. Menu → **API et services** → **Identifiants**
6. **+ Créer des identifiants** → **Clé API**
7. Copie la clé générée

### Étape 2 — Ajouter la clé sur GitHub
1. Dépôt GitHub → **Settings** → **Secrets and variables** → **Actions**
2. **New repository secret**
3. Nom : `DRIVE_API_KEY`
4. Valeur : ta clé API
5. **Add secret**

## 🚀 Compilation via GitHub Actions

1. Pousse ce projet sur un dépôt GitHub
2. Ajoute le secret `DRIVE_API_KEY`
3. Onglet **Actions** → **Build Hawkins Library APK** → **Run workflow**
4. Attends ~5 minutes
5. Section **Artifacts** → télécharge l'APK

## 🛠️ Versions utilisées

| Outil | Version |
|---|---|
| Android Gradle Plugin | 8.7.3 |
| Kotlin | 2.0.21 |
| Gradle | 8.9 |
| Java | 17 |
| Compile SDK | 35 |
| Min SDK | 26 |
| Media3 ExoPlayer | 1.5.0 |
| Compose BOM | 2024.12.01 |

## 📋 Limitations connues

- Les vidéos Google Drive doivent être partagées publiquement
- Fichiers > 100 Mo peuvent nécessiter `confirm=t` (déjà géré dans le code)
- MP4 (H.264) recommandé
