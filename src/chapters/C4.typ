#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= JUnit-Tests
#needsSpelling

Um die Behebung der Exception zu erleichtern, wurde entschieden, einen JUnit‑Test zu schreiben, der das Auftreten des Fehlers schneller überprüfbar macht. Dadurch muss nicht jedes Mal das gesamte Programm gestartet werden, um festzustellen, ob die Exception erneut ausgelöst wird. Ein solcher Test isoliert den relevanten Codeabschnitt und ermöglicht es, die problematische Situation gezielt und wiederholt auszuführen, was den Debugging‑Prozess deutlich beschleunigt.
== Definition
JUnit ist ein weit verbreitetes Testframework für Java, das dabei hilft, Code automatisiert zu überprüfen und Fehler frühzeitig zu erkennen. Es ermöglicht das Schreiben kleiner, wiederholbarer Testmethoden, die einzelne Funktionen oder Abläufe isoliert ausführen. Dadurch lassen sich Fehlerquellen schneller eingrenzen, weil Tests unabhängig vom restlichen Programm laufen und exakt zeigen, welche Methode korrekt funktioniert und welche nicht. @Beck_Gamma_2000 @1383111 @app11167250
== Reproduzierung des Fehlers per JUnit-Test
