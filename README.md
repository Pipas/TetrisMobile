# Tetris Mobile

A reimagining of the classic game of Tetris with new controls on the android platform.

## Getting Started

To play the game just download and install the apk file in your android device from the following link: [TetrisMobile.apk](https://drive.google.com/file/d/0Bzd-0mlUdOgEdGVsQWczTkJ6eGs/view?usp=sharing)

Instructions to review and compile the code are detailed below

### Prerequisites

* [Android Studio](https://developer.android.com/studio/index.html) - The Official IDE for Android

### Installing

Clone or download this branch of the repository to your computer

```
git clone https://github.com/Pipas/LPOO1617_T3G07_TETRIS.git
```

Open Android Studio, select "Open an existing Android Studio Project" and select the cloned folder.

Android Studio will compile the project and it's ready to run.

## User Manual

![Game Screenshots](http://i.imgur.com/HPEcB7z.png)

### Goal

The objective of the game is to manipulate pieces of 4 squares, by moving them sideways or rotating them by 90 degree units, with the aim of creating an horizontal line of ten units without gaps. When such a line is created, it disappears, and any block above the deleted line will fall. When a certain number of lines are cleared, the game enters a new level. As the game progresses, each level causes the pieces to fall faster, and the game ends when the stack of squares reaches the top of the playing field. When a game is over the score of the player is recorded and the aim is to get the highest possible score.

### Controls

The control scheme of the app is very simple, a tap on the top 2 thirds of the screen rotates the falling piece, holding the bottom third of the screen makes the piece fall faster. 

Moving the pieces is done by tilting the device left or right.

A pause function is also available by tapping the hardware 'BACK' button on the android device.

### Scoring

The score is incremented when a line is cleared, if multiple lines are cleared at once it grants the player more points. The current level acts as a multiplier for the points gained as explained below

* 1 Line *(Single)* = 40 points * Current Level
* 2 Lines *(Double)* = 100 points * Current Level
* 3 Lines *(Triple)* = 300 poits * Current Level
* 4 Lines *(Tetris)* = 1200 points * Current Level

The final score of the player is saved in a database if it's one of the 20 highest scores by all users of the app. This scores can be accessed from the Main Menu.


## Unit testing


Unit tests (JUnit 4) were made for the Game package, there was no unit testing for the GUI (screens) and score/database access classes because they used APIs which are difficult to test and cant be tested directly in AndroidStudio.

### Running the tests
 
To run the tests, right-click on package "com.tetris.game" in folder "tests" and click on "Run tests with Coverage" to see coverage in Game package

Of the current tests (in Game package), total class coverage is 100%, total method coverage is 85% and total line coverage is 89%.
    
    
### Test cases
  
  - Simulate game moves (piece movement to the sides and down)
  - Test logic that deletes when a line is completed
  - Test game over when pieces reach the top of the play area (game over)
  - Test score and levels after several lines are completed (several moves)
  - Test when piece has stopped falling (reached floor or other pieces)
 

## Built With

* [LibGDX](https://libgdx.badlogicgames.com/) - A Java game development framework
* [Firebase](https://firebase.google.com/) - Realtime cloud-hosted JSON database 

## Class Diagram
![Class Diagram](http://i.imgur.com/rQZ1aPO.png)

## Behavioural Aspects (Game Cycle)
![Behavioural Aspects](http://i.imgur.com/cK0QZXM.png)

## Difficulty

The biggest struggles found along the way were using the different API's in this project. Namely integrating the Firebase Android API with the LibGDX API.

## Developing Time and Work Distribution

The time spent on this assignment was about a total of 50 Hours between both members with a distribution of work of about 60% by Paulo Correia and 40% by Alexandre Carvalho.

## Authors

* **Paulo Correia** - *up201406006* - [github.com/Pipas](https://github.com/Pipas)
* **Alexandre Carvalho** - *up201506688* - [github.com/ajcarv](https://github.com/ajcarv)
