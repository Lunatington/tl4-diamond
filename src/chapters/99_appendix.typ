#import "../components/code.typ": codeblock

== GeoEditor Wiki-Pages
  #figure(
  [],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [@tge HHLA-Wiki-Page],
) <wiki>

  #figure(
  [],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [TerminalGeometrie HHLA-Wiki-Page],
) <wiki-TerminalGeometrie>

  #figure(
  [],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [GeoEditor N4-XPS HHLA-Wiki-Page],
) <wiki-Geo-Xps>

  #figure(
  [],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [N4-XPS HHLA-Wiki-Page],
) <wiki-Xps>

== N4NodeMoveUseCase
  
  #codeblock("../res/UseCaseGeometryModifiedN4Nodebefore.java", "Java")
  #figure(
    [],
    kind: "Anhang",
   supplement: "Anhang",
   caption: [Ausgeführt wenn eine Node bewegt wird (vor den Änderungen)],
  ) <N4-Node-Move-Usecase>
  #pagebreak()
== N4NodeMoveseCase mit allen veränderungen

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

== Code 

  == Text
  #lorem(50)