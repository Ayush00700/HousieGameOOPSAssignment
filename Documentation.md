
### Constraints: 
- Each Player can win atmost one prize.
- Moderator randomly chooses 3 or 4 or all 5 prizes out of the available 5 prizes.

### Object Oriented Analysis of Code

#### 1. OOP Principles 

- Design Principle 1: `Identify the aspects of your application that vary and separate them from what stays the same`. My code stores what changes in Board.java, which can be accessed by Moderator and Player in a Mutually Exclusive manner. Also we don't need to change our Moderator and Player classes to add new functionalities in this Manner.

- Design Principle 2: `Program to an interface, not an implementation.` All 5 Winning Conditions implement the `WinningCondition.java` interface. This will help us in accessing each Prize using Dynamic Memory Dispatch by creating objects of the Concrete classes and reference it to the interface. Also for new Prize conditions, we just need to add the functionality in a new class rather than modifying previous code. So Extention is preferred over Modifiability in my code.

- Design Principle 3: `Strive for loosely coupled designs between objects that interact.` As we can see in our code, The moderator and Player are loosely coupled as they are not directly dependent on each other, but instead depends on the Board class for accessing, editing and sharing data. This loose coupling will make the code less prone to errors and runtime exceptions as these two classes can act almost independent of each other.

- Design Principle 4: `Classes should be open for extension, but closed for modification.` Here, we can add new Prizes by 'Extention' just by creating a seperate concrete class that implements the 'WinningConditions' interface. This way, we don't need to modify the previously written code, which is always preferred. 

- Design Principle 5:`Favor Composition over inheritance` Here 5 classes, namely TopRow, MiddleRow, BottomRow, Early5, FullHouse has implemented the Winning Condition Class. Here, Moderator and Player class have a reference to this Winning Condition Class. So they can access all these 5 Winning Conditions by themselves without extending it. So inheritance was not used but just a reference variable was the link between the two classes. Composition is preferred, as now we can  with the help of Composition, all the five Winning Condition classes together. The code would have got extremely complicated if only inheritance was used.  

-Design Principle 6: `Depend on abstraction, donot depend on concrete class`. All 5 Winning Conditions implement the `WinningCondition.java` interface. This will help us in accessing each Prize using Dynamic Memory Dispatch by creating objects of the Concrete classes and reference it to the interface. Here, we are achiving abstraction as the refernce to the interface doesn't care about the inner methods of the concrete classes that implement it. Hence, the interface only cares about the methods that it necessarily require.    

#### 2. OOP Design Pattern

Our code resembles a lot of properties with the `Observer Design Pattern`. The Moderator Class is the subject which observes/calls a new random number, it then notifies each Player that a new number has been called and to check the called number on their respective tickets. All the players who have subscribed to the game from the moderator are the observers in this Design Pattern. These observers check their respective tickets for the presence of the called number. 

`Strategy Design Patten` wasn't used in my code, as here the behaviour/ properties of Players and Moderators are limited and this restricts the stategies that these Players can follow. So this pattern wouldn't help us here.

`Decorator Design Pattern` wasn't used in my code, as we don't want the functionality of repeatedly encapsulating the objects at dynamic memory time. This method would turn out to be redundant for our problem in hand.

### Contribution of Each Team Member:

Ayush: 
    - Implementation of Moderator and Player classes with all Functionalities.
    - Implementation of Board.java that stores all variables to be used by everyone in a mutually exclusive manner.
    - Implenting GUI with Java Swings.
    - Documentation and Video Editing. 
    - Code Analysis. 
    - Creating UML Class Diagram and USE Case Diagram.

Yash:
    -Creating interface and classes for all 5 Winning Conditions.
    -Generating Ticket with all Random Numbers.  
    -Implementing Multithreading.
    -Code Cleaning.
    -Code Analysis.
    -Creating UML Sequence Diagram

### Flaws in my implementation:
- Proper and Specific Design Pattern hasn't been thought of before writing the code, as implementation of this code was way before it was taught in class.

- Even though the code resembles Observer Design Pattern, I should have used two seperate interfaces `Subject` and `Observer` which should be implemented by the `Moderator` and the `Player` classes respectively.

-  Assumption is Made that (No of Players >> Prizes) as otherwise, since each player can win atmost one prize, then surely all players will win a prize.

- Moderator class itself is the Main Class. Generally, on reading several patterns, I came to realize that Driver Classes should be preferred which calls Moderator and Player Classes seperately.

- Packages have not been implemented which increases the scalability and would have made the entire code cleaner. This was because I was facing issue with VSCode, so I couldn't implemend packages for this version. But i'm well aware that it will make my code better and cleaner than now.