# Audio Player

A modern, Compose-based audio player with a focus on **polished UX**, **accessibility**, and **internationalization**.  
The player supports real audio playback using **Media3 ExoPlayer** and includes smooth, tactile animations for user interactions.

---

## 🎧 Demo
| Recording |
| -- |
| <video width=350 src="https://github.com/user-attachments/assets/e56ca843-b885-486c-8217-4a56fb8f9756" /> |

---

## ✨ Features

### 🎵 Audio Playback
- Plays a **bundled, non-copyright audio track**
- Powered by **AndroidX Media3 ExoPlayer**
- Supports:
  - Play / Pause
  - Seeking via progress bar
  - Accurate duration & current position display
  - Buffered progress visualization

---

### ♿ Accessibility
- All interactive controls expose **content descriptions**
- Buttons and slider respect **minimum touch target sizes**
- Slider supports:
  - Keyboard focus
  - TalkBack / screen reader navigation
- Motion is kept subtle and non-essential animations can be gated if needed

---

### 🌍 RTL (Right-to-Left) Support
- Progress bar, buffer track, and thumb positioning automatically adapt to:
  - LTR layouts
  - RTL layouts
- Fraction-based drawing ensures correct visual mapping regardless of layout direction
- Icons and controls remain logically ordered and mirrored where appropriate

---

### 🎨 UX & Animations
- **Fallback placeholder** when missing album art:
  - <img width=350 src="https://github.com/user-attachments/assets/3536f74f-7b5a-4f67-ac87-f676c39f1bd4" />
- **Press animations** for all control buttons:
  - Immediate scale-down feedback on press
  - Smooth spring-back on release
- Slider thumb:
  - Grows slightly while seeking for improved affordance
  - Animates smoothly between states
- All animations are lightweight, performant, and interruption-safe

---

## 🛠 Tech Stack
- **Jetpack Compose**
- **Material 3**
- **AndroidX Media3 ExoPlayer**
- Kotlin
- Canvas-based custom drawing for progress & buffer tracks (for performance wins)

---

## 📁 Audio Asset
- The included audio file is:
  - Stored in `res/raw`
  - Free to use / non-copyright
- No network access or permissions are required for playback

---

## 🚀 Getting Started

1. Clone the project
2. Open in Android Studio
3. Run on a device or emulator (Compose Preview disables playback automatically)
4. Press play and interact with the progress bar and controls

---

## 📌 Notes
- All playback logic is lifecycle-aware and properly released.
- The architecture cleanly separates UI, playback state, and player control.

---

## 📄 License
This project is provided for demonstration and educational purposes.
The included audio asset is non-copyright and safe for reuse.
