# CardMarket

## Panoramica del Sistema
CardMarket è un'applicazione desktop progettata per mettere in contatto venditori e acquirenti di carte collezionabili. Il sistema è ottimizzato per garantire all'acquirente la ricerca delle migliori offerte sul mercato, permettendo la comparazione dei prezzi, il monitoraggio delle variazioni e l'ottimizzazione degli ordini. Contestualmente, fornisce ai venditori un'interfaccia strutturata per la gestione e l'aggiornamento accurato del proprio catalogo.

## Architettura e Scelte Progettuali
Il progetto è stato sviluppato applicando rigorosamente i principi dell'Ingegneria del Software, con particolare attenzione al disaccoppiamento dei componenti e alla modularità:

* **Linguaggio e UI:** Sviluppato in Java, con interfaccia grafica gestita tramite JavaFX.
* **Pattern Architetturali:** Strutturato seguendo il pattern architetturale BCE (Boundary-Control-Entity).
* **Design Patterns (GoF):**
  * **Observer:** Implementato per la gestione asincrona delle notifiche in tempo reale (es. variazioni di prezzo o disponibilità).
  * **Decorator:** Utilizzato per consentire la composizione dinamica dei filtri di ricerca a runtime.
  * **Abstract Factory & Singleton:** Adottati per isolare la logica di business dai dettagli di memorizzazione.
* **Persistenza Flessibile:** Il sistema supporta l'avvio in tre diverse modalità operative senza richiedere modifiche al codice sorgente:
  1. *DB Mode* (connessione a database MySQL)
  2. *FileSystem Mode* (salvataggio dati su file locali)
  3. *Demo Mode* (strutture dati in-memory per esecuzioni di test e fallback)
