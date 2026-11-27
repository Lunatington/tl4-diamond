#import "../components/formatting.typ": clickable_link, needsCite, todo, needsSpelling
= Mögliche Lösungsmöglichkeiten

== Microsoft IIS

Die Internet Information Services (IIS) sind der Webserver von Microsoft. Microsoft beschreibt IIS als flexiblen, sicheren und verwaltbaren Webserver, mit dem sich Websites, Dienste und Anwendungen im Internet oder in einem Intranet bereitstellen lassen @mslearniistraining. IIS ist kein eigenständiges Produkt, sondern als Serverrolle „Webserver (IIS)“ in Windows Server enthalten und kann auch auf Windows-Clientsystemen als Funktion aktiviert werden @mslearniistraining. Neben der Auslieferung von Webseiten über HTTP und HTTPS unterstützt IIS unter anderem Webanwendungen auf Basis von ASP.NET und PHP sowie die Dateiübertragung per FTP @mslearniisoverview.

Technisch ist IIS modular aufgebaut. Eingehende Anfragen nimmt zunächst der Treiber HTTP.sys entgegen, der als Teil des Netzwerk-Subsystems von Windows im Kernelmodus arbeitet. Der Windows-Prozessaktivierungsdienst (Windows Process Activation Service, WAS) verwaltet die Konfiguration und startet bei Bedarf Arbeitsprozesse, welche die Anfragen verarbeiten. Diese Arbeitsprozesse sind in Anwendungspools gruppiert, die Anwendungen über Prozessgrenzen voneinander trennen, sodass eine fehlerhafte Anwendung andere Anwendungen auf demselben Server nicht beeinträchtigt. Der Funktionsumfang des Servers ergibt sich aus Modulen, die je nach Bedarf hinzugefügt oder entfernt werden können. Nicht benötigte Module zu entfernen verkleinert die Angriffsfläche und den Speicherbedarf des Servers @mslearniisarch.

IIS ist in der Lage einer Website bzw. einem Rest-API-Endpunkt mehrere URLs zuzuordnen. Dies ist insbesondere dann sinnvoll, wenn die Website bzw. der Endpunkt über mehrere Domains erreichbar sein soll. Die Zuordnung von mehreren URLs zu einer Website bzw. einem Endpunkt kann über die IIS-Manager-GUI oder über PowerShell erfolgen #needsCite. 

Im falle der DiaMon3D installation könnte ein zusätzlicher Endpunkt mit Zertifikat verwendet werden, um den DiaMon3D-Server über eine andere URL mit Zertifikat zu erreichen welches die HHLA verwaltet. Dies würde die Installation von DiaMon3D vereinfachen, da die Konfiguration der hosts-Datei und die Anpassung der Verknüpfung zum HostConfigurator entfallen würden. Der Endpunkt könnte über die bestehende Verwaltungsstruktur des Unternehmens verteilt werden, sodass ein physischer Einsatz an den Arbeitsplätzen nicht mehr erforderlich ist.


== Zusätzlicher Endpunkt mit Zertifikat ohne Microsoft IIS
