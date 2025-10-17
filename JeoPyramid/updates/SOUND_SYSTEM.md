# JeoPyramid Sound System Integration

# Date: October 11, 2025

# DISCLAIMER: This documentation is written with the use of GPT-5. However, this is reviewed to ensure information correctness

## Overview

The sound system enables context-sensitive audio feedback for key game actions, enhancing user experience. It is implemented via the `SoundManager` utility class, which loads and plays WAV files from the packaged resources. All sound triggers are wired into the game’s controllers and UI event handlers.

## Resource Structure

All sound files must be placed in:

```
src/main/resources/Assets/sounds/
```

On build, Maven copies these to:

```
target/classes/Assets/sounds/
```

**Supported file names:**
- `intro.wav` — App launch
- `intro_next.wav` — Play button click
- `lev1_intro.wav`, `lev2_intro.wav`, `lev3_intro.wav`, `lev4_intro.wav` — Level entry
- `correct1.wav` to `correct6.wav` — Correct answer (random selection)
- `wrong1.wav` to `wrong4.wav` — Wrong answer (random selection)
- `exit_hover.wav` — Hovering exit button
- `exit1.wav`, `exit2.wav` — Exit action (random selection)

**Format:**  
- All files must be standard WAV format for compatibility with Java Sound API.

## SoundManager API

Located at:  
`src/main/java/com/noinc/bloomsjeopardy/utils/SoundManager.java`

### Key Methods

- `SoundManager.getInstance()` — Singleton accessor
- `playIntro()` — Play app intro sound
- `playIntroNext()` — Play sound for Play button
- `playLevelIntro(int level)` — Play level entry sound (level: 1-based)
- `playCorrectRandom()` — Play a random correct answer sound
- `playWrongRandom()` — Play a random wrong answer sound
- `playExitHover()` — Play sound when hovering exit button
- `playExitPress()` — Play exit sound (random selection)
- `playExitPressAndWait()` — Play exit sound and block until finished (used before app termination)

### Usage Example

```java
SoundManager sm = SoundManager.getInstance();
sm.playIntro(); // On app start
sm.playLevelIntro(2); // Entering level 2
sm.playCorrectRandom(); // Correct answer
sm.playExitPressAndWait(); // Before System.exit(0)
```

## Event Wiring

- **App start:** `playIntro()` in `GameEngine` constructor
- **Play button:** `playIntroNext()` in `PlayerActionListener`
- **Level entry:** `playLevelIntro(n)` in `GameEngine.startMainGame()`
- **Correct/Wrong answer:** `playCorrectRandom()` / `playWrongRandom()` in `GameEngine.submitAnswer()`
- **Exit hover:** `playExitHover()` in `GUIStartScreen` and `GUIEndScreen` mouseEntered handlers
- **Exit press:** `playExitPressAndWait()` in `GameEngine.terminateGame()` (waits for sound before exit)

## Extending

- To add new sounds, place WAV files in `Assets/sounds/` and add corresponding methods to `SoundManager`.
- For global volume, use `setMasterVolume(float volume)` early in startup.

## Troubleshooting

- If sounds do not play, ensure files are in `src/main/resources/Assets/sounds/` and are copied to `target/classes/Assets/sounds/` after build.
- Resource path is case-sensitive and must match exactly.
- Only WAV files are supported out-of-the-box.

## Contact

For questions or issues, contact the backend team or refer to `SoundManager.java` for implementation details.
