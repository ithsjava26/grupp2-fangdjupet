# Fångdjupet

![Tests](https://github.com/ithsjava26/grupp2-fangdjupet/actions/workflows/tests.yml/badge.svg)

Ett textbaserat rollspel i konsolen där en hjälte slåss sig igenom en serie fiender i en fängelsehåla.
Hjälten väljer anfallsstil, hittar vapen, rustningar och drycker efter varje seger, och äventyret
sparas automatiskt så att det kan fortsättas senare.

Gruppuppgift i kursen JAVA01 (JUV26D), grupp 2.

## Krav

- JDK 27
- Maven behöver inte vara installerat. Projektet använder Maven Wrapper, som laddar ner rätt version automatiskt.

## Köra spelet

I IntelliJ: öppna projektet och kör `fangdjupet.Main` via den gröna pilen i `Main.java`.

Från terminalen (Windows):

```
.\mvnw compile exec:java -Dexec.mainClass=fangdjupet.Main
```

## Köra testerna

```
.\mvnw test
```

På macOS och Linux används `./mvnw` i stället för `.\mvnw`. Om bygget beter sig konstigt efter större
ändringar, bygg om allt från början med `.\mvnw clean test`.

Testerna körs också automatiskt med GitHub Actions vid varje pull request.

## Spelet

```
=== Fångdjupet ===
1. Starta nytt äventyr
2. Fortsätt sparat äventyr
3. Visa hjältens utrustning
4. Strid mot nästa fiende
5. Drick läkedryck
0. Avsluta
```

- **Nytt äventyr:** spelaren namnger hjälten och väljer anfallsstil: närstrid, avståndsanfall eller magi.
  Hjälten möter sedan tre fiender i tur och ordning.
- **Strid:** hjälten slår först, och fienden slår tillbaka om den överlever. Varje besegrad fiende lämnar
  ett föremål på samma nivå som fienden.
- **Utrustning:** vapen och rustningar tas på automatiskt om de är bättre än det hjälten bär.
  Övriga föremål hamnar i ryggsäcken.
- **Drycker:** läkedrycker i ryggsäcken återställer hälsa.
- **Sparande:** äventyret sparas automatiskt efter varje händelse och när spelaren avslutar.
  När äventyret är vunnet eller förlorat tas sparfilen bort.
- **Färger:** hälsan visas som en stapel som går från grön till gul till röd, och händelseloggen är färgkodad.
  Färgerna stängs av om miljövariabeln `NO_COLOR` är satt.
- **Topplista:** klasserna `Highscore` och `HighscoreEntry` sparar och sorterar resultat i en fil, med tester.
  Den är förberedd men inte inkopplad i menyn.

## Projektstruktur och ansvar

| Paket | Innehåll | Ansvarig |
|---|---|---|
| `fangdjupet.contract` | Gemensamma interfaces, records och enums som modulerna delar | Hela gruppen |
| `fangdjupet.combat` | Hjälte, fiender, anfallsstrategier och turordning i strid | Jelena (A) |
| `fangdjupet.item` | Föremål, inventarium och föremålsfabrik | Stefan (B) |
| `fangdjupet.game` | Meny, spelmotor, händelselogg och sparfunktion | Peter (C) |

## Designmönster

### Strategy (combat)

AttackStrategy är implementerat med Strategy Pattern. Mönstret gör det möjligt att separera logiken för olika attacktaktiker från karaktärerna i spelet. På så sätt kan hjältar och fiender använda olika attackstrategier utan att deras egna klasser behöver ändras. Vi undviker också duplicerad kod. Interfacet AttackStrategy fungerar som ett kontrakt för alla attacktyper. Varje strategi måste kunna ange sitt namn och beräkna skadan som attacken orsakar. I spelet finns tre attackstrategier: MeleeAttack (närstrid; skada beräknas som attack minus försvar), RangedAttack (avståndsanfall; skada beräknas som attack minus halva försvaret) och MagicAttack(gör 75 % av attackvärdet i skada och ignorerar försvaret helt). Gemensamma regler för skadeberäkning har samlats i klassen DamageRules för att undvika duplicerad kod. Strategy Pattern passar bra här eftersom nya attacktyper kan läggas till utan att ändra befintliga karaktärsklasser.

### Factory (item och combat)

ItemFactory ansvarar för att skapa olika typer av föremål, exempelvis vapen, rustningar och drycker.

EnemyFactory ansvarar för att skapa fiender av olika typer, exempelvis råttor, gobliner och troll, med egenskaper baserade på deras nivå.

### Observer (game)

`Game` skickar händelser (`GameEvent`) till alla registrerade `GameEventListener`, till exempel `EventLog`
som skriver ut dem i konsolen med färg efter händelsetyp. Spellogiken behöver inte veta vem som lyssnar,
och nya lyssnare kan läggas till utan att befintlig kod ändras. `Battle` får bara en `GameEventListener`
och vet inte att `Game` eller `EventLog` finns. I testerna används en vanlig lista som lyssnare
(`events::add`) för att kontrollera vilka händelser som skickades.

## Sparfunktionen

`SaveManager` sparar äventyret som en textfil, `fangdjupet.save`, med en rad per sak:

```
HERO;Peter;40;8;3;33;Magi
EQUIP;WEAPON;2
ITEM;POTION;1
ENEMY;Goblinen;2;18;6;2;18
```

- Ett eget textformat valdes framför Javas inbyggda serialisering, eftersom filen går att läsa och felsöka
  och inte går sönder när en klass ändras.
- Föremål sparas som typ och nivå och återskapas med `ItemFactory`.
- Filen skrivs först till en tillfällig fil, som sedan ersätter den gamla. Om sparandet avbryts finns det
  tidigare sparade äventyret kvar.
- Alla fel samlas i undantaget `SaveGameException`, så att menyn kan visa ett begripligt meddelande
  i stället för att programmet kraschar.

## Tester

JUnit 5-tester finns för den centrala logiken i varje modul, totalt 75 tester:

| Modul | Testklasser | Testar |
|---|---|---|
| `combat` | `MeleeAttackTest`, `RangedAttackTest`, `MagicAttackTest` | Skadeberäkning för varje anfallsstil |
| `combat` | `BattleTest` | Turordning och händelser i strid |
| `combat` | `EnemyFactoryTest` | Fiendernas värden per nivå |
| `item` | `AbstractItemTest`, `ArmorTest`, `PotionTest`, `WeaponTest` | Föremålens värden och kontroller |
| `item` | `ItemFactoryTest` | Föremål och bonusar per typ och nivå |
| `item` | `InventoryTest` | Lägga till, ta bort och filtrera föremål |
| `game` | `GameTest` | Spelmotorn, byte efter strid, utrustning och drycker |
| `game` | `SaveManagerTest` | Spara, läsa in och felhantering, med en tillfällig katalog (`@TempDir`) |
| `game` | `ConsoleStyleTest` | Färggränser och hälsostapeln |
| `game` | `HighscoreTest` | Topplistans lagring och sortering |

## Arbetssätt med Git

Vi följer GitHub flow:

- `main` ska alltid gå att bygga och köra. Den är skyddad, och ändringar kommer bara in via pull requests.
- Allt arbete görs i en feature branch från en uppdaterad `main`, med namn som `feature/<namn>-<vad>`.
- Efter en squash merge skapas alltid en ny branch från `main`. Den gamla branchen återanvänds inte.
- Varje pull request granskas och godkänns av minst en annan gruppmedlem, och testerna ska vara gröna.
- Den som skapade pull requesten mergar den efter godkännandet.
- Vi mergar med squash merge, så att varje pull request blir en commit på `main`.
- Kontraktet i `fangdjupet.contract` ändras bara efter diskussion i gruppen.
- Vi pushar inte till någon annans branch. Förslag på ändringar skrivs som kommentarer i granskningen.
- CodeRabbit granskar varje pull request automatiskt. Varje förslag bedöms av författaren: rättas om det
  kan hända i spelet och rättningen är rimlig, annars avböjs det med en kort motivering.

## Gruppreflektion kring Git-samarbetet

(Vad fungerade bra, vad var svårt, och hur löstes konflikter.)
