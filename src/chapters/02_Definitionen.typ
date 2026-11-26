#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling

= Definitionen 
== STS-Kran
Ein @sts (auch Quay Crane; dt. Containerbrücke) ist ein schienengebundener Portalkran an der Kaikante eines Containerterminals, der Container zwischen dem Schiff und der Landseite des Terminals umschlägt. Er bildet damit die Schnittstelle zwischen wasserseitigem und landseitigem Transport und ist das zentrale Umschlaggerät der Schiffsabfertigung @steenken2004 @brinkmann2005.

Technisch besteht ein @sts aus einem auf Schienen parallel zum Kai verfahrbaren Portal und einem weit über das Schiff auskragenden Ausleger. Auf ihm fährt eine Laufkatze, die den Container mit einem Spreader aufnimmt, über das Schiff bzw. die Kaifläche bewegt und absetzt. Neben Einkatzbrücken gibt es Zweikatzbrücken (Dual-Trolley-Cranes). Bei ihnen übergibt die Hauptkatze den Container an eine Plattform, von der eine zweite Portalkatze ihn an die landseitigen Transportgeräte weitergibt @steenken2004 @stahlbock2008.

Weil die Liegezeit eines Schiffes maßgeblich von der Anzahl und der Leistung der eingesetzten Containerbrücken abhängt, gehören STS-Krane neben den Liegeplätzen zu den zentralen Engpassressourcen der Wasserseite. Ihre Zuordnung zu Schiffen und ihre Einsatzreihenfolge sind deshalb ein eigenes Planungsproblem der Terminallogistik, das Quay Crane Scheduling Problem @bierwirth2010 @guenther2006.

Am Container Terminal Altenwerder (CTA) der HHLA sind Zweikatz-Containerbrücken im Einsatz, die die Container an fahrerlose Transportfahrzeuge (AGV) übergeben. Die zuletzt bei Liebherr bestellten Geräte haben eine Auslegerreichweite von 61 m und eine Hubhöhe von 53 m. Sie arbeiten kabinenlos, also automatisiert und ferngesteuert @liebherr2023.

#figure(
  image("../res/CTA-Bruecke.jpg", height: 9cm),
  caption: "STS-Kran am CTA",
) <korea-image>

== CMS (Crane Management System)
Ein @cms ist ein softwarebasiertes Überwachungs- und Diagnosesystem, das Betriebs-, Zustands- und Störungsdaten eines Krans kontinuierlich erfasst, visualisiert und speichert. Es dient dem Kranfahrer und dem technischen Personal als Bedienoberfläche (Human Machine Interface, HMI) mit einem Gesamtüberblick über den Kran. Zu den typischen Funktionen gehören die laufende Aufzeichnung von Statusdaten, die Meldung und Ursachenanalyse von Störungen und Alarmen, die Unterstützung vorbeugender Instandhaltung und die Erfassung von Umschlagdaten @tmeiccms.

Vom @cms abzugrenzen ist die eigentliche Kransteuerung (speicherprogrammierbare Steuerung, SPS), die die Antriebe des Krans regelt. Das @cms greift auf deren Daten zu, steuert den Kran aber nicht selbst. Ebenso abzugrenzen ist das Terminal Operating System (TOS), das die Umschlagaufträge des gesamten Terminals plant. Auf Terminalebene können die @cms der einzelnen Krane zu einem übergeordneten Leitstand zusammengeführt werden @siemenstss.

Die Bedeutung solcher Systeme wächst mit der Automatisierung von Containerterminals. An STS-Kranen werden über zahlreiche Sensoren und die SPS große Datenmengen erfasst, etwa zu Last, Katzposition, Geschwindigkeit und Getriebeschwingungen. Diese Daten lassen sich für die Abschätzung der Lebensdauer, für Energieanalysen und zur Erkennung von Anomalien nutzen @li2022.

Liebherr bietet für seine STS-Containerbrücken ein eigenes, kundenspezifisches @cms namens DiaMon3D an. Es stellt Diagnose- und Leistungsdaten über eine Bedienoberfläche bereit und bietet Live-3D-Modelle des Krans sowie Fernzugriff auf die Daten @liebherrtransform.


