# JeoPyramid

## A retro-style Jeopardy learning pyramid — fun, modular quizzes with sound and background music

[Repository](#) · Java / Swing · Maven

---

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Installation](#installation)
- [Quick Start / Usage](#quick-start--usage)
- [Advanced Usage / API Notes](#advanced-usage--api-notes)
- [Configuration & Resources](#configuration--resources)
- [Contributing](#contributing)
- [License](#license)

---

## Description
JeoPyramid is a desktop, Jeopardy-style educational game built in Java with a retro arcade look and feel. It organizes questions into a pyramid of modules and difficulty categories, integrates sound effects and background music, and is intended for instructors, students, and educators who want an engaging way to run knowledge checks.

This project was built to make it simple to:
- run modular quizzes (by module/topic),
- present content in a game-like pyramid format,
- provide immediate feedback with audio and animations,
- easily add or update question CSVs and media assets.

(Logo / screenshot placeholder — include `assets/logo.png` or similar here if available.)

---

## Features
- 🎮 Retro Jeopardy-style pyramid UI built with Java Swing  
- 🔊 Centralized `SoundManager` for effects, background music, and ducking while effects play  
- 📂 Modular question data: CSV per module and per difficulty (easy → hard)  
- 🔁 Background music loop with ducking on sound effects for improved UX  
- 🧭 Simple resource layout — assets and sounds located under `src/main/resources/Assets/`  

---

## Installation

Prerequisites
- Java JDK 11+ (Java 17 or 21 recommended — project developed and tested on Java 21)
- Maven 3.6+ (for build lifecycle and packaging)

Clone and build:
```bash
git clone https://github.com/your-org/Jeopardy-Pyramid.git
cd JeoPyramid
mvn clean package
```

Run the app (from the project root):
```bash
# Run from compiled classes (during development)
java -cp target/classes com.noinc.bloomsjeopardy.App

# Or run the packaged jar (if the jar's manifest sets the Main-Class)
java -jar target/JeoPyramid-1.0-SNAPSHOT.jar
```

If you prefer Maven to run the app (exec plugin):
```bash
mvn compile exec:java -Dexec.mainClass="com.noinc.bloomsjeopardy.App"
```

---

## Quick Start / Usage

Minimal Java snippet to start the game programmatically (copy-paste into a small runner if needed):

```java
// Example: RunApp.java
public class RunApp {
    public static void main(String[] args) {
        // Start the packaged app class
        com.noinc.bloomsjeopardy.App.main(args);
    }
}
```

Typical interactive flow:
1. Launch game.
2. Click "Play" → select a module.
3. Answer questions; correct/wrong feedback plays with sounds.
4. Use the in-game menu to return to start, or exit (exit sound will play and the process will wait until it finishes).

---

## Advanced Usage / API Notes

Sound system
- The core audio API is available via `com.noinc.bloomsjeopardy.utils.SoundManager`.
- Key methods:
  - `SoundManager.getInstance().play("file.wav")` — plays a one-shot effect from classpath `Assets/sounds/`.
  - `playBackgroundMusic()` / `stopBackgroundMusic()` — control looping background music.
  - `duckBackgroundMusic()` / `restoreBackgroundMusic()` — lower/restore background volume.
  - `playExitPressAndWait()` — plays exit sound and blocks until it completes to avoid truncation.
- Resource path expected at runtime: `/Assets/sounds/<name>.wav` (packaged under `src/main/resources/Assets/sounds/`).

Game engine
- `com.noinc.bloomsjeopardy.controller.GameEngine` orchestrates game state and calls sound/UI hooks.
- Questions are loaded from `src/main/resources/data/Module X/*.csv` (one CSV per category such as Knowledge, Comprehension, Application, etc.)

Configuration & extension points
- Add new modules: drop CSV files under `src/main/resources/data/Module N/`.
- Add new sounds: add WAV files under `src/main/resources/Assets/sounds/` and call them by filename from `SoundManager`.
- UI hooks: attach `MouseListener` or `ActionListener` and call `SoundManager` methods for hover/click effects.

Documentation
- For detailed docs (gameflow, file format, sound API), point to the full docs page: `docs/` (placeholder — add detailed markdown files under `docs/` as needed).

---

## Configuration & Resources

Project resource layout (important paths)
- Java sources: `src/main/java/`
- Resources (images, fonts, sounds, data): `src/main/resources/Assets/` and `src/main/resources/data/`
  - Sounds: `src/main/resources/Assets/sounds/`
  - Images: `src/main/resources/Assets/Images/`
  - Fonts: `src/main/resources/Assets/Fonts/`
  - Question CSVs: `src/main/resources/data/Module X/`

Tips
- Keep WAVs 16-bit PCM for best cross-platform compatibility with the Java Sound API.
- If audio fails to play, check volume controls and whether the platform's Mixer supports `FloatControl.Type.MASTER_GAIN`. The `SoundManager` contains fallbacks when gain control is unavailable.

---

## Contributing
We welcome contributions! Areas that are especially helpful:
- improving test coverage (unit + integration),
- audio resilience (cross-platform testing, format conversions),
- polishing doc pages and `CONTRIBUTING.md`,
- UI/UX improvements and accessibility.

Suggested workflow:
1. Fork the repo.
2. Create a feature branch: `git checkout -b feat/your-feature`.
3. Implement tests and code changes.
4. Open a Pull Request with a clear description and testing notes.

See `CONTRIBUTING.md` for PR checklist and code style rules (create if not present).

---

## License
This project is offered under the MIT License — see `LICENSE` for details.

---

## Troubleshooting / FAQ

Q: The app crashes with "Could not find or load main class com.noinc..."  
A: Verify your `Main-Class` is correct or run with explicit main via `java -cp target/classes com.noinc.bloomsjeopardy.App`. Confirm `mvn package` succeeded and that classes were built under `target/classes/`.

Q: No audio plays on my machine  
A: Make sure:
- The WAVs are present under `target/classes/Assets/sounds/` (built from `src/main/resources`),
- Your Java runtime audio system is available,
- The project isn't exiting immediately (some exit flows may block until the exit sound completes; if process exits prematurely, check that `playExitPressAndWait()` is used),
- Try simple test playback using `SoundManager.getInstance().play("exit_hover.wav")` in a short test harness while the application is running.

---

## Acknowledgements
Built with Java, Swing, and the Java Sound API. Thanks to the core team for the passion of building this project.
