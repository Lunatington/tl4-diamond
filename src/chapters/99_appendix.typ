#import "../components/code.typ": codeblock

== GeoEditor Wiki-Page
  #figure(
  [],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [@tge HHLA-Wiki-Page],
) <wiki>

#pagebreak()

== Terminalgeometrie Wiki-Page
  #figure(
  [],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [TerminalGeometrie HHLA-Wiki-Page],
) <wiki-TerminalGeometrie>

#pagebreak()

== GeoEditor N4-XPS Wiki-Page
  #figure(
  [],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [GeoEditor N4-XPS HHLA-Wiki-Page],
) <wiki-Geo-Xps>

#pagebreak()
== N4-XPS Wiki-Page

  #figure(
  [],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [N4-XPS HHLA-Wiki-Page],
) <wiki-Xps>
#pagebreak()

== N4NodeMoveUseCase
  
  #codeblock("../res/UseCaseGeometryModifiedN4Nodebefore.java", "Java")
  #figure(
    [],
    kind: "Anhang",
   supplement: "Anhang",
   caption: [Ausgeführt wenn eine Node bewegt wird (vor den Änderungen)],
  ) <N4-Node-Move-Usecase>
  #pagebreak()
== N4NodeMoveUseCase mit allen Veränderungen

  #codeblock("../res/UseCaseGeometryModifiedN4Node.java", "Java")
  #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [Ausgeführt wenn eine Node bewegt wird (nach den Änderungen)],
  ) <N4-Node-Move-Usecase-with-all-changes>  

== AbstractUseCase
  #codeblock("../res/AbstractUseCase.java", "Java")
  #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [Klasse von der alle UseCases erben],
  ) <AbstractUseCaseJava>  

== Error Log
  #codeblock("../res/Error.java", "Java")
    #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [Error Log, wenn Exception auftritt],
  ) <ErrorLog>  

== AbstractGeometrieEditorTestCase
#codeblock("../res/AbstractTest.java", "Java")
    #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [JUnit Abstracter TestCase von welchem alle Tests erben],
  ) 

== TestNodeMove
#codeblock("../res/testNodeMove.java", "Java")
    #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [JUnit-Test zum reproduzieren des Fehlers],
  ) 

== AbstractSelection()
#codeblock("../res/AbstractSelection.java", "Java")
    #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [AbstractsSelection],
  ) 

== GetSelectedItems() vor dem Fix
#codeblock("../res/GetSelectedItemsbefore.java", "Java")
    #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [GetSelectedItems() vor dem Fix],
  ) 

== GetSelectedItems() nach dem Fix
#codeblock("../res/GetSelectedItemsafter.java", "Java")
    #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [GetSelectedItems() nach dem Fix],
  ) 
