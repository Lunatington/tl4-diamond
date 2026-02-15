#let bold_upper(content) = text(weight: 700, upper(content))
#let needsCite = strong(text(red, "Benötigt Zitat"))
#let needsSpelling = strong(text(green, "Benötigt Rechtschreibkontrolle"))
#let todo(content) = block(strong(text(blue, "TODO: " + content)))
#let clickable_link(url, display: none) = if display == none {
  link(url, underline(text(blue, url)))
} else {
  link(url, underline(text(blue, display)))
}