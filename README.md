# BM Elite Force 2
This is the second game in the BM Elite Force series (not much related to each other). BM Elite Force has been my hobby project where I create a new game in a new (for me) language every christmas dedicated to my good old friends. This year the game was written in __Scala__ using the Java library Slick2D (built on LWJGL).

## Builds & Requirements
Pre-compiled versions for OS X, Windows and Linux is found in the project root. Please see bugs for Windows and Linux below.

## Controls
This game is meant to be played with and __Xbox 360 controller__. You really should use one to enjoy this game (analog precision makes all the difference). There is however keyboard bindings avaliable if you just want to try it out.

### Xbox 360 controller
 * Steering: Left stick
 * Change color: Colored buttons A, B, X, Y or Right stick
 * Shoot: Right trigger
 * Toggle health: Press left stick

### Keyboard
 * Steering: Arrow keys
 * Change color: W, A, S, D
 * Shoot: Space or Left Shift (see Windows bug note below)
 * Toggle health: Right Shift (see Windows bug note below)

## Bugs
There are some bugs which I know of but I'm not currently not trying to fix them. Feel free to submit a pull request if you wish. :)

### Linux
Fullscreen mode and Sound doesn't work in Ubuntu 12.10.

### Windows
Keys for shooting doesn't work on some keyboards under Windows.

## Requirements
Java 1.6+ is required. Everything else is distributed with the pre-compiled builds.

## Resources for hacking / building the game
http://www.slick2d.org/
http://www.lwjgl.org/

### java.lang.UnsatisfiedLinkError: no lwjgl in java.library.path
You must specify the native files, for OS X you should provide the following VM option:
    -Djava.library.path=lib/native/macosx

## BM Elite Force 1 (game from 2011)
https://github.com/AntonFagerberg/BM-Elite-Force

## License
GNU General Public License, version 2