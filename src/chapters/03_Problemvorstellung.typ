#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Problemvorstellung

== Problem
Am Container Terminal Altenwerder (CTA) der HHLA werden Container wasserseitig über STS-Krane umgeschlagen. Die neuen STS-Krane von Liebherr am CTA sind als Zweikatzbrücken ausgeführt und arbeiten kabinenlos, also automatisiert und ferngesteuert @liebherr2023. Damit hängt ihr Betrieb stärker als bisher von IT-Systemen ab, die den Zustand der Krane erfassen und für Bedienung und Instandhaltung verfügbar machen.

Eines dieser Systeme ist das Crane Management System (CMS) DiaMon3d von Liebherr. Es überwacht und speichert den technischen Zustand jedes neuen STS-Krans am CTA. Verschiedene Ansichten des Systems unterstützen den laufenden Betrieb sowie die technische Optimierung und die Fehleranalyse @liebherrtransform. Für diese Anwendungsfälle wird DiaMon3d auf Arbeitsplätzen in unterschiedlichen Bereichen des Terminals installiert. Die Anforderungen an die Installation und damit die IT-betrieblichen Details unterscheiden sich dabei je nach Anwendungsfall stark. #todo("Bereiche und Anzahl der Arbeitsplätze nennen, z. B. Leitstand, Technik/Instandhaltung, Fernsteuerplätze.")

Das Kernproblem liegt im Installationsprozess selbst. DiaMon3d lässt sich derzeit nicht zentral verteilen, sondern muss direkt am jeweiligen Arbeitsplatz installiert werden. Dafür muss eine Person physisch zum Arbeitsplatz auf dem Terminal gelangen. Vor Ort sind anschließend zahlreiche manuelle Schritte nötig: Die Software muss für den jeweiligen Anwendungsfall konfiguriert werden, und dazu müssen Konfigurationsdateien und teilweise Code von Hand geändert und angepasst werden #todo("typische Schritte kurz aufzählen, z. B. welche Dateien, welche Parameter") Dieser Ablauf erfordert spezielles Fachwissen und dauert pro Arbeitsplatz mehrere Stunden #todo("konkrete Dauer bzw. Schätzung") Während der Installation ist der betroffene Arbeitsplatz nicht oder nur eingeschränkt nutzbar, sodass der Betrieb gestört wird.

Daraus ergibt sich die zentrale Frage dieser Arbeit: Wie kann die Installation von DiaMon3d am CTA so strukturiert und automatisiert werden, dass weder ein Einsatz vor Ort noch manuelle Anpassungen an Konfiguration und Code nötig sind und die Anforderungen an Dokumentation und IT-Betrieb wirtschaftlich erfüllt werden? Ziel ist eine automatische oder zumindest teilautomatische Verteilung der Software über die bestehenden Verwaltungsstrukturen des Betriebs, einschließlich der zugehörigen Dokumentation.


== Aktueller Prozess

== Gewünschter Prozess

Um DiaMon3D zu installieren, ist aktuell vie manuelle arebit erforderlich. [@Alte-Anleitung]