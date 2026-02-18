#import "../components/code.typ": codeblock

== GeoEditor Wiki-Page
  #figure(
  [@tge HHLA-Wiki-Page],
  kind: "Anhang",
  supplement: "Anhang",
  caption: [@tge HHLA-Wiki-Page],
) <wiki>
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



  #codeblock("../res/UseCaseGeometryModifiedN4Node.java", "Java"),
  #figure(
    [],
    kind: "Anhang",
    supplement: "Anhang",
    caption: [Ausgeführt wenn eine Node bewegt wird (nach den Änderungen)],
  ) <N4-Node-Move-Usecase-with-all-changes>  

== Code 

  == Text
  #lorem(50)