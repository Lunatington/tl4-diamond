#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Problemvorstellung

== Problem
Am Container Terminal Altenwerder (CTA) der HHLA werden Container wasserseitig über STS-Krane umgeschlagen. Die neuen STS-Krane von Liebherr am CTA sind als Zweikatzbrücken ausgeführt und arbeiten kabinenlos, also automatisiert und ferngesteuert @liebherr2023. Damit hängt ihr Betrieb stärker als bisher von IT-Systemen ab, die den Zustand der Krane erfassen und für Bedienung und Instandhaltung verfügbar machen.

Eines dieser Systeme ist das Crane Management System (CMS) DiaMon3d von Liebherr. Es überwacht und speichert den technischen Zustand jedes neuen STS-Krans am CTA. Verschiedene Ansichten des Systems unterstützen den laufenden Betrieb sowie die technische Optimierung und die Fehleranalyse @liebherrtransform. Für diese Anwendungsfälle wird DiaMon3d auf Arbeitsplätzen in unterschiedlichen Bereichen des Terminals installiert. In dieser Arbeit wird aber nur die Installation auf HHLA-Clients für die HHLA-Techniker und überwacher betrachtet. Die Clients die auf den Geräten vom Hersteller Liebherr - wie z.B. den STS-Kränen und ROS-Desks (Remote Operating Station) - stehen, sind nicht Teil dieser Arbeit, da sie weiterhin über die alte Methode installiert werden.

Das Kernproblem liegt im Installationsprozess selbst. DiaMon3d lässt sich derzeit nicht zentral verteilen, sondern muss direkt am jeweiligen Arbeitsplatz installiert werden. Dafür muss eine Person physisch zum Arbeitsplatz auf dem Terminal gelangen. Vor Ort sind anschließend zahlreiche manuelle Schritte nötig: Die Software muss für den jeweiligen Anwendungsfall konfiguriert werden, und dazu müssen Konfigurationsdateien von Hand angepasst werden. Dieser Ablauf erfordert spezielles Fachwissen und kann pro Arbeitsplatz bis zu einer Stunde in anspruch nehmen. Während der Installation ist der betroffene Arbeitsplatz nicht oder nur eingeschränkt nutzbar, sodass der Betrieb gestört wird.

Daraus ergibt sich die zentrale Frage dieser Arbeit: Wie kann die Installation von DiaMon3d am CTA so strukturiert und automatisiert werden, dass weder ein Einsatz vor Ort noch manuelle Anpassungen an Konfiguration und Code nötig sind und die Anforderungen an Dokumentation und IT-Betrieb wirtschaftlich erfüllt werden? Ziel ist eine automatische oder zumindest teilautomatische Verteilung der Software über die bestehenden Verwaltungsstrukturen des Betriebs, einschließlich der zugehörigen Dokumentation.


== Aktueller Prozess

Die Installation von DiaMon3D auf HHLA-Clients erfolgt derzeit als manueller Vor-Ort-Prozess. Hierfür muss zunächst die aktuelle Softwareversion aus dem zentralen Artefakt-Repository heruntergeladen und lokal entpackt werden. Anschließend wird die Installation über das bereitgestellte Installationsprogramm gestartet. Während des Installationsvorgangs müssen verschiedene Parameter manuell ausgewählt beziehungsweise eingegeben werden. Dazu zählen unter anderem der Installationspfad, die Art des zu installierenden Clients sowie die Konfiguration der Datenbankverbindung. [@Alte-Anleitung]

Nach der eigentlichen Softwareinstallation sind weitere Konfigurationsschritte erforderlich. So müssen Zertifikate manuell in ein bestimmtes Verzeichnis kopiert und bestehende Dateien überschrieben werden. Darüber hinaus ist eine Anpassung der Verknüpfung zum HostConfigurator notwendig, bei der ein Zertifikat-Fingerprint ergänzt wird. Anschließend muss das Werkzeug HostConfigurator mit einem separaten Passwort ausgeführt werden, um hosts-Dateieinträge zu aktualisieren. [@Alte-Anleitung]

Ein weiterer manueller Arbeitsschritt besteht in der Anpassung der lokalen hosts-Datei des Betriebssystems. Hierbei muss sichergestellt werden, dass der Hostname des DiaMon3D-Servers einer festen IP-Adresse zugeordnet ist. Dieser Eingriff erfordert Administratorrechte auf dem jeweiligen Arbeitsplatzrechner. [@Alte-Anleitung]

Abschließend ist die Erstellung und Konfiguration einer Desktop-Verknüpfung erforderlich. Hierzu wird eine Verknüpfung zur ausführbaren Datei angelegt und deren Arbeitsverzeichnis manuell angepasst. Erst nach diesem Schritt kann die Anwendung ohne erhöhte Benutzerrechte gestartet werden. [@Alte-Anleitung]

#figure(
  image("../res/DiaMon3D_BPMN_Swimlane.drawio.svg", width: 17.5cm),
  caption: "Aktueller Installationsprozess von DiaMon3D am CTA",
) <korea-image>

== Gewünschter Prozess

Im Soll-Zustand wird die Installation von DiaMon3D als standardisierter und weitgehend automatisierter Softwarebereitstellungsprozess umgesetzt. Die Software wird hierzu paketiert und über die bestehenden Verwaltungsstrukturen des Unternehmens verteilt. Sämtliche für den jeweiligen Anwendungsfall erforderlichen Konfigurationsschritte werden automatisiert ausgeführt. Hierzu zählen insbesondere die Installation von Zertifikaten, die Erstellung erforderlicher Hosts-Einträge, die Konfiguration der Anwendung sowie die Anlage von Programmverknüpfungen. Ein physischer Einsatz an den Arbeitsplätzen ist dadurch nicht mehr erforderlich. Die Bereitstellung erfolgt reproduzierbar, nachvollziehbar und mit deutlich geringerem Zeitaufwand. Gleichzeitig werden die Dokumentationsanforderungen des IT-Betriebs unterstützt und die Aussagekraft bei internen sowie externen Audits verbessert.

#figure(
  image("../res/DiaMon3D_Sollprozess_BPMN.drawio.svg", width: 17.5cm),
  caption: "Gewünschter Installationsprozess von DiaMon3D am CTA",
) <korea-image>