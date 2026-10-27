# Fångdjupet

![Tests](https://github.com/ithsjava26/grupp2-fangdjupet/actions/workflows/tests.yml/badge.svg)

Ett textbaserat rollspel i konsolen där en hjälte slåss sig igenom en serie fiender i en fängelsehåla.
Gruppuppgift i kursen JAVA01 (JUV26D), grupp 2.

## Krav

- JDK 27
- Maven behöver inte vara installerat. Projektet använder Maven Wrapper, som laddar ner rätt version automatiskt.

## Köra spelet

I IntelliJ: öppna projektet och kör `fangdjupet.Main` via den gröna pilen i `Main.java`.

## Köra testerna

```
.\mvnw test
```

På macOS och Linux används `./mvnw` i stället för `.\mvnw`. Om bygget beter sig konstigt efter större
ändringar, bygg om allt från början med `.\mvnw clean test`.

Testerna körs också automatiskt med GitHub Actions vid varje pull request.

## Projektstruktur och ansvar

| Paket | Innehåll | Ansvarig |
|---|---|---|
| `fangdjupet.contract` | Gemensamma interfaces, records och enums som modulerna delar | Hela gruppen |
| `fangdjupet.combat` | Hjälte, fiender, anfallsstrategier och turordning i strid | Jalena (A) |
| `fangdjupet.item` | Föremål, inventarium och föremålsfabrik | Stefan (B) |
| `fangdjupet.game` | Meny, spelmotor, händelselogg och sparfunktion | Peter (C) |

## Designmönster

### Strategy (combat)
(Fylls i av Jalena: vilka strategier som finns och varför mönstret passar.)

### Factory (item och combat)
(Fylls i av Stefan: vad fabrikerna skapar och vad anroparen slipper veta.)

### Observer (game)
`Game` skickar händelser (`GameEvent`) till alla registrerade `GameEventListener`, till exempel `EventLog`
som skriver ut dem i konsolen med färg efter händelsetyp. Spellogiken behöver inte veta vem som lyssnar,
och nya lyssnare kan läggas till utan att befintlig kod ändras.

## Arbetssätt med Git

Vi följer GitHub flow:

- `main` ska alltid gå att bygga och köra. Den är skyddad, och ändringar kommer bara in via pull requests.
- Allt arbete görs i en feature branch från en uppdaterad `main`, med namn som `feature/<namn>-<vad>`.
- Varje pull request granskas och godkänns av minst en annan gruppmedlem, och testerna ska vara gröna.
- Vi mergar med en vanlig merge commit, så att varje branch syns i historiken.
- Kontraktet i `fangdjupet.contract` ändras bara efter diskussion i gruppen.
- Vi pushar inte till någon annans branch. Förslag på ändringar skrivs som kommentarer i granskningen.

## Gruppreflektion kring Git-samarbetet

(Vad fungerade bra, vad var svårt, och hur löstes konflikter.)
