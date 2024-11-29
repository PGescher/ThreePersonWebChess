<h1>Exercise 2</h1>

<h2>2.1. Composition over inheritance</h2>

>Examine the Java Open Chess code base and make yourself familiar with the current inheritance hierarchy.
>Find and discuss in your team where applying the principle of favoring composition over inheritance would lead to a better design.

Currently all pieces inherit from the Pieces Class.
Every Class Overrides the allMoves Method.

We could implement the Movement as Composition as well as the Pawn Promotion as Composition. Allowing us to switch the Pawn to any Piece flexibly.

<h2>2.2. Continuous Integration</h2>

Maven - How does Maven work?



<h2>2.3. Build-Server with Executable Jar</h2>

<h2>2.4. Debugger</h2>

>Familiarize yourself with the Java debugger and its integration into your IDE. Take a look at the options for monitoring the software status(watchpoints, stack frame, variable watcher).
>Use breakpoints, conditional breakpoints and the stack trace to track exceptions and find bugs.
>As an example, check the allMoves of class King and watch, at which point wasMotion is changed.

<h2>2.5. Chessboard choice</h2>

>Think about a layout for a three-person chessboard as well as movement rules for the pieces on this board. Feel free to invent new pieces and movement behaviors.

We should stick with the same shaped board pieces. These are our options:
Square, Triangle, Hexagon, Rhomboid.

How is the Chessboard implemented?

Each Square is initalized individually and indexed. The Square is selected through calculation based on the pixel coordinates x and y of the click.

I believe we could implement this for any shape, the math would just be a little bit more complicated.

If we choose a Square, we will not have to redesign all the Moves, I do not think this should be a big issue either way. We woud have to design an interesting board and that might result in us having to redesign the moves anyway. The Triangles we could implement as half of the squares. Rhomboids are also an option, not very interesting with just as much change.

The Decision was a Hexagon.

- Example Hexagon of the board here.

What new Pieces or Rules do we want to implement:

- Existing Pieces:

    - Kill Count of Pieces

    - Stealing of Abilites from Pieces - Maybe exclusive to Pawn.

- New Abilities or Pieces:

    - Transformer Piece - Combine two pieces into one with combined Moveset or different new Rules (Sacrifice)

    - Portal Piece

<h1>Exercise 3</h1>
<h2>3.1. Law of Demeter</h2>

>Examine the Java Open Chess code base. Find violations of the Law Of Demeter and discuss possible Refactorings.

- Each unit should have only limited knowledge of the structure and properties of surrounding units
- Each unit should only talk to its friends; don't talk to strangers(talk=“invoke methods”, etc.)
- Only talk to your immediate friends

So this means we do not want any chained method calls, or atleas limit this. Call methods on objects returned by other methods.

We also do not want to be able to access variables direclty if not through public methods?

Only call Methods from itself, direct properties, objects passed as parameters, objects it creates, objects in scope.

Class:

- Move:

    - Violations:
        - Direct access to name of piece and pozY of square.
    - Solutions:
        - Get method for pozY of Square. Piece method that checks its type, if it is pawn or something.
- Moves:
    - Violations:
        - Again in addMove direct access to pozX, Same with piece of end.\
        ```
        if ((!this.enterBlack && this.game.chessboard.kingBlack.isChecked()) || (this.enterBlack && this.game.chessboard.kingWhite.isChecked()))
        {//if checked
            if ((!this.enterBlack && this.game.chessboard.kingBlack.isCheckmatedOrStalemated() == 1) || (this.enterBlack && this.game.chessboard.kingWhite.isCheckmatedOrStalemated() == 1))
            {//check if checkmated
                locMove += "#";//check mate
            }
            else
            {
            locMove += "+";//check
            }
        }
        ```
        - This in setMoves, direct access to squares of chessboard.\
        `Square[][] squares = this.game.chessboard.squares;`
        - Direct access of Chessboard.bottom, with Chessboard not being previously passed as object or anything else.
        - Creating newMove and Squares:\
        `this.moveBackStack.add(new Move(new Square(begin), new Square(end), begin.piece, end.piece, castlingMove, wasEnPassant, promotedPiece));`
    - Solutions:
        - Functions in chessboard or game like isKingchecked() or alike
        - Function in chessboard to perform actions on squares.
        - Function getChessboard() in game.
        
        - No idea about the last one.

- Piece:
    - Violations:
        - In checkPiece:\
        `if (chessboard.squares[x][y].piece != null && chessboard.squares[x][y].piece.name.equals("King"))`
        Maybe also direct access to squares?
    - Solutions:

- King:
    - Violations:
        - accessing of square and then position.\
        `for (int i = this.square.pozX - 1; i <= this.square.pozX + 1; i++)`
        - Chaining:\
        `if (chessboard.squares[0][this.square.pozY].piece != null && chessboard.squares[0][this.square.pozY].piece.name.equals("Rook"))`
        - Similar chaining to this on multiple occasions.

    - Solutions:
        - Add getter and setter to chessboard for square and to square about its piece.
        - Less direct access.
        - Possibly simplify or refactor Kings isSafe() code, noted as confusing.

- Knight:
    - Violations:
        - chained methods: \
        `if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))`
        - nested properties: \
        `this.square.pozX`
        ´this.square.pozX´

    - Solutions:

- Pawn:
    - Violations:

    - Solutions:

- Queen:
    - Violations: 
        - Again chained method like previously: \
        `if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i]))`
        - `this.square.pozX`

- Rook:
    - Violations:
        - Access to player class for colour of rook:
        `this.player.color == this.player.color.black`
        - Again the King is safe and the square.pozX 

    - Solutions:
        - isWhite() boolean in player class?

- Bishop:
    - Violations:
        - Similar to the other pieces.

- Square:
    - Violations:
        - `this.piece.square = this;`

    - Solutions:
        - Setters?
        - Making field private and accessing through getters and setters.

- Game:
    - Violations:
        - Chaining:
        `if (chessboard.activeSquare.piece.allMoves().indexOf(chessboard.squares[endX][endY]) != -1) //move`
        
- Chessboard:
    - Violations:
    
- Player:

<h2>3.2. Refactoring Techniques</h2>

>Examine the Java Open Chess code base. Find locations in which the application of the following refactoring techniques would be beneficial for the overall code quality.

>a. Extract Method / Extract Class

Extracted a Method in the calculateMove method of Knight that calculated each of the 8 different moves to improve readability.

- Temporary Variables or calculations can be replaced by Methods
- Improve Readability
- Each Class should have a single well defined responsibility.

The King class could benefit from this - specifically isSafe().

Add the King safety method to chessboard maybe?

>b. Convert Constructor to Factory / Factory Method

Havent looked into this too much but logically this only makes sense in the Piece class, right?

Movement Logic, maybe?

Different Chessboards.

>c. Encapsulate Downcast



>d. Replace Value with Reference

Replacing Copying of Objects with passing references of Objects.

<h2>3.3.Dependency Inversion Principle</h2>

>Explain the general steps required to revise your architecture with respect to the Dependency Inversion Principle.

1. Identify dependencies between low and high level modules
2. Abstraction / or interfaces of low level classes like the board setup or promotion.
3. Refactor - repace direct refernces to the low level classes with the abstract ones.

Dependency Injection Pattern - Injection of specific implementations like a specific board setup.

>How could dependency injection be exploited to modularly extend the game with different modes (e.g. different initial board setup, promotion behavior, etc)?

Defining key components as interfaces that can vary. These can then be injected during runtime.

<h2>3.4 Implementation of 3 Person Chess</h2>

>By now, you should have a vision of the game you want to create and an initial plan of how you are going to implement it. For the first milestone in December, we would like to see a first runnable version. The sooner you get started, the better ☺


<h1>Exercise 4</h1>

<h2>4.1. Information Hiding </h2>

>Examine the Java Open Chess code base. Identify candidates for applying the Information Hiding Principle and discuss possible Refactorings.

The Chessboard Squares and Pieces? 

<h2>4.2. Domain Analysis and System Architecture </h2>

>The lecture discussed possible domains and sub-domains for online chess applications. 
>Identify the core, supporting and generic domains for your chess application, decide on respective implementation strategies/technologies and formalize your envisioned architecture in C4 system-context and container diagrams.

Core:

Game Logic - Chessboard, Pieces, Rules?

Supporting:

User Interface?

Generic:

Moves History?
Chat?

>In the e-learning course, you can find the script of last semester’s C4 lecture from the software engineering course.

<h2>4.3. Snapshot vs. Event-Based Microservice State </h2>

>Research the concept of “Event Sourcing”. 

>Discuss the differences compared to Snapshot-based state handling using the example of classic chess-notation. 

>What are the implications on the implementation for each option (datamodel/database, architecture, dataflow)? 

>For which microservices would you select Snapshot- over Event-based solutions and vice-versa?

<h2>4.4. Make JChess a Web-App with a Microservice Architecture </h2>

>You guessed correctly! Stating from this exercise sheet, we expect you to migrate JChess into a web app. To this end, you may get rid of the current GUI components. The existing game logic should be retained where possible by migrating it into the backend microservice(s) of your application. 
>Use docker and docker compose to manage and execute the individual services.

<h1>Exercise 5 </h1>



<h1>Current Notes</h1>

Diagrams: They want Diagrams

<h1>Meeting Notes 22.11</h1>

ToDo:

Architecture Diagramm how we could implement Chess - Front Backend
Understand how Front End Backend works - How does the Frontend Communicate with the backend.
Diagramm in C4 Format, lecture about those on the moodle.

Issues with CI - Look into how Maven works -Maybe Consider Gradle.
Build Server for executable jar.

Change Movement to Composite Behaviour.
Composition over Inheritance
Understand Pawn Promotion -in Chessboard.

Finish Exercise 4.
Information Hiding
Micro Services  - Research Jaffar

Exercise 3
Law of Demeter Violations and refactorings to fix.

Refactorings:
a - Specify more what and how - King isSafe() or alternatives.
b -
c - Understand
d - 

Inverse Dependency




Information Hiding Refactor -


<h1>Current ToDos </h1>

Figure out what we are supposed to have till now.

Figure out where we are at right now.

Dicuss what the next steps should be.

Divide work between us.


Questions:

Hosting, do we host the WebApp.
Heroku App - Hosting, possibly free.

Java - Java Script,
Adding Java Script Front End to Github.