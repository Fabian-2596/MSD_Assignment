
Classes:
AddCard

AddCardDeck
AddDeck
AllCards
AllDecks
Card
CardClicked
CardCursorAdapter:
Custom Cursor Adapter to show the symbol belonging to the color,
convert the image bytearray to a bitmap to set as source of the imageview

CardDatabaseHelper
CardDatabaseManager
Deck
DeckClicked

The extra features I included:

Upload picture from files
Spinner for input
Dialog

Description of what I would add if I had more time:

-Non Creature cards don't have the power and toughness traits,
 e.g. Lands have none, Planeswalker have loyalty instead of power/toughness, etc.
 I thought about implementing a Fragment that changes the Layout of the power and toughness input fields
 depending on which type is selected on the spinner and make all different types an own class which inherits from the base card class.
-Another function I would want to add is to add Cards only from a picture with an api that reads the text directly from the image.
-Couldn't really implement to m,n multiplicity from Card to Deck,
 I just have three tables and when deleting a card that already is in a deck I also have to delete it from the deck
 instead of just using foreign keys for that.
-RecyclerView with Slide function to delete one card

Video Link: