# BM Elite Force 2
This is the second game in the BM Elite Force series (not much related to each other). BM Elite Force 2 has been my autumn/winter project of 2012. I started this "tradition" in 2011 where I create a new game in a new (for me) language every year and release it around christmas dedicated to my good old friends when we meet up. This year the game was written in __Scala__ using the Java library Slick 2D (built on LWJGL).

## Video
You can see a video of the entire game here: http://vimeo.com/antonfagerberg/bm-elite-force-2

## Downloads
To download the game visit the Google Code page: http://code.google.com/p/bm-elite-force-2/downloads/list

## Builds & Requirements
Pre-compiled versions for OS X, Windows and Linux is found in the project root (in the folders named OS X, Windows and Linux). If you just want to download the game, use the link above (Google Code). GitHub will give an error message if you try to download it directly: "Error: blob is too big" (you will have to clone the entire thing to get it).

## Controls
This game is meant to be played with and __Xbox 360 controller__. You really should use one to enjoy this game (analog precision makes all the difference) and it has the correct color coding. There is however keyboard bindings avaliable if you just want to try it out.

### Xbox 360 controller
 * Steering: Left stick
 * Change color: Colored buttons A, B, X, Y or Right stick
 * Shoot: Right trigger
 * Toggle health: Press left stick

### Keyboard
 * Steering: Arrow keys
 * Change color: W, A, S, D
 * Shoot: Space or Left Shift
 * Toggle health: Right Shift

## Bugs
There are some bugs which I know of but I'm not currently not trying to fix them. Feel free to submit a pull request if you wish. :)

### Linux
Fullscreen mode and Sound doesn't work in Ubuntu 12.10 as far as I can tell?

## Requirements
Java 1.6+ is required. Everything else is distributed with the pre-compiled builds.

## Resources for hacking / building the game
http://www.slick2d.org/
http://www.lwjgl.org/

### java.lang.UnsatisfiedLinkError: no lwjgl in java.library.path
You must specify the native files, for OS X you should provide the following VM option:
    -Djava.library.path=lib/native/macosx

## BM Elite Force 1 (game from 2011)
This was the previous game which I coded in Ruby with LibGosu.
https://github.com/AntonFagerberg/BM-Elite-Force

## License
GNU General Public License, version 2

## Music (CC BY-NC-SA 3.0)
### Jimmypig
XS & GS - Game Over
www.newgrounds.com/audio/listen/469781

X Sentinel - Lift Off
www.newgrounds.com/audio/listen/498935

### nal1200
Defiance
www.newgrounds.com/audio/listen/500422

### Avizura
Avizura - Loyalty
www.newgrounds.com/audio/listen/500531

### MainStreamBeats
LED (SMB) - (wip)
www.newgrounds.com/audio/listen/476561

### magicalDANI13
Magically Winterland
www.newgrounds.com/audio/listen/476147

### unknown
Tangerine
www.newgrounds.com/audio/listen/481979

### Bezo
The Most Inspiring Song Ever
www.newgrounds.com/audio/listen/38773

## Graphics (CC BY-SA 3.0 / GPL 2.0 / GPL 3.0)
### Surt
http://opengameart.org/content/shmup-ships 

### WidgetWorx
www.widgetworx.com/widgetworx/portfolio/spritelib.html

### Wyverii
http://opengameart.org/content/unsealed-terrex

### FrenetikFred (Space Invaders icons)
http://frenetikfred.deviantart.com/art/Space-Invaders-icons-32203053