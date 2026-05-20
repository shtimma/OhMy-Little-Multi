# Oh My Little Multi 🌈

**Oh My Little Multi** è un'app educativa Android per bambini che trasforma l'apprendimento delle tabelline in un'entusiasmante avventura 3D.

## ✨ Caratteristiche del Progetto

### 🎨 Design e Interfaccia Utente
- **UI 3D Pixel-Perfect**: L'interfaccia si ispira ai classici giochi tattili. Ogni pulsante ha un volume reale, ombre e riflessi lucidi.
- **Mascotte Interattive**:
    - **Il Gufo Saggio**: Un mentore che fornisce suggerimenti e monitora la correttezza delle risposte. Il suo design è protetto e realizzato con complessi gradienti radiali.
    - **Drako (Il Drago)**: Un compagno fedele che incoraggia il giocatore durante tutto il gioco.
- **Ambiente Immersivo**: Nuvole fluttuanti, colline sullo sfondo e animazioni fluide degli elementi creano un mondo di gioco vivo.

### 🧠 Metodologia di Apprendimento (Sistema Leitner)
Il gioco implementa un sistema di ripetizione spaziata scientificamente provato — l'**Algoritmo Leitner (5 Scatole)**:
1. Le attività nuove o difficili vengono inserite nella **Scatola 1** e appaiono frequentemente.
2. Le risposte corrette promuovono l'attività alla scatola successiva (Scatola 2, 3, ecc.).
3. Le risposte errate retrocedono immediatamente l'attività alla **Scatola 1**.
4. I progressi sono salvati nelle `SharedPreferences`, consentendo una padronanza efficiente delle equazioni più difficili.

### 🎮 Gameplay
- **Scheda dell'Equazione**: Una scheda centrale che mostra il problema con uno "slot 3D" per la risposta selezionata.
- **Answer Bank**: Un pannello dedicato contenente le opzioni di risposta, in stile "vassoio".
- **Sistema di Suggerimenti**: Permette ai giocatori di vedere l'ultima cifra della risposta o ricevere un indizio logico al costo di una piccola penalità nel punteggio.
- **Celebrazione della Vittoria**: Il completamento di una serie di 5 domande attiva un'esplosione di coriandoli (utilizzando la libreria Konfetti).

## 🛠 Stack Tecnico
- **Linguaggio**: Kotlin
- **Architettura**: ViewBinding, navigazione basata su Fragment.
- **UI**: ConstraintLayout, Custom Vector Drawables (stile 3D), Material 3.
- **Animazioni**: ObjectAnimator, animazioni XML (pulse, float, pop).
- **Archiviazione Dati**: SharedPreferences (stato del sistema Leitner).

## 🚀 Come Eseguire
1. Clona il repository.
2. Apri il progetto in **Android Studio Ladybug (o versioni successive)**.
3. Avvia su un emulatore o un dispositivo reale con API 24+.

---
*Creato con amore per i dettagli e la matematica!* 🍎✨
