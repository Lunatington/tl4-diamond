#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Fehler
== Beschreibung Fehler
Wenn ein N4 Knotenpunkt, an welchem über 79 Kanten hängen, bewegt wird fliegt eine ConcurrentModificationException. #clickable_link("Video zum Fehler") 

Die N4-Knotenpunkte und N4-Kanten sind eine @hhla addition zum Framework, welche das @tos N4 benötigt. Der @tge exportiert eine sogennante ```"Yard-File"``` welche N4-XPS direkt einlesen kann. 

Der fehler tritt nur bei N4-Kanten und N4-Knotenpunkten auf, 

== Reproduktion des Fehlers
== Analyse Error-Log