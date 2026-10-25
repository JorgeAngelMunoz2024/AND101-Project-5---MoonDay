# AND101 Project 5 - Choose Your Own API

Submitted by: **Jorge Muñoz**

Time spent: **5** hours spent in total

## Summary

**MoonDay** is an android app that **queries the FreeAstroAPI for any selected date in the year and displays the animated moon phase, SVG graphic, illumination percentage, traditional full moon name, astronomical metrics, tropical zodiac sign, spiritual guidance, and upcoming lunar forecasts.**

If I had to describe this project in three (3) emojis, they would be: **🌕 🔮 ✨**

## Application Features

The following REQUIRED features are completed:

- [x] Make an API call to an API of your choice using AsyncHTTPClient / Retrofit & Coroutines
- [x] Display at least three (3) pieces of data for each API entry retrieved
- [x] A working Button requests a new API entry and updates the data displayed

The following STRETCH features are implemented:

- [x] Add a query to the API request
  - The query I added is **`date` (ISO date string YYYY-MM-DD), `include_visuals=true`, `include_zodiac=true`, `include_traditional_moon=true`, and `include_interpretation=true`**
- [x] Build a UI to allow users to add that query

The following EXTRA features are implemented:

- [x] **Native Compose Moon Phase Animation**: Custom 60fps vector animation cycling through moon phase frames on the home screen.
- [x] **Bubbly Fredoka Typography**: Integrated Google's Fredoka font for a rounded, friendly aesthetic across all screens.
- [x] **Cosmic Starry Header**: Custom starry night sky header banner with a functional top-left Back Arrow button.
- [x] **Tabbed Data Navigation Bar**: Organized the rich API response into 4 clean tabs (`Phase`, `Astronomy`, `Guidance`, `Forecast`).

## API Choice

My chosen API for this project is **FreeAstroAPI** (`https://api.freeastroapi.com/api/v1/moon/phase`).

## Video Demo

Here's a video / GIF that demos all of the app's implemented features:

![Video Demo](Peek%202026-10-24%2020-10.gif)

GIF created with **Peek**

## Notes

During the creation of this app, I learned how to integrate Retrofit with OkHttp header interceptors for secure API key handling, render dynamic SVG responses in Jetpack Compose, build a Material 3 DatePicker, and organize complex API payloads cleanly using a Bottom Navigation Bar and custom starry headers.

## License

Copyright **2026** **Jorge Muñoz**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
