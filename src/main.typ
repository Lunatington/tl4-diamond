// Imports
#import "components/transfer_paper.typ": transfer_paper

#show: transfer_paper.with(
	language: "de",
  "2",
	"13445",
	"Ein wundervoller und deskriptiver, aber nicht zu langer Titel, aber immerhin füllt er zwei Zeilen",
	"Technische Informatik, T23a",
	appendix_content: include "chapters/99_appendix.typ",
)

// --- Include content here ---
#include "chapters/C1.typ"
#include "chapters/C2.typ"
#include "chapters/C3.typ"
#include "chapters/C4.typ"
#include "chapters/C5.typ"
#include "chapters/C6.typ"
#include "chapters/C7.typ"
//#include "chapters/01_chapter.typ"
//#include "chapters/02_chapter.typ"
//#include "chapters/03_chapter.typ"
