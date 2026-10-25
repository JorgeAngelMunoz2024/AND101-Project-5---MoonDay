// Builds each moon-stack with the same 8 phase frames, then cycles through them.
// Every stack uses the same timing; only the CSS transition differs per .style-* class,
// so the transition style is the only variable between demos.
const PHASE_FILES = [
  "01-new-moon.svg",
  "02-waxing-crescent.svg",
  "03-first-quarter.svg",
  "04-waxing-gibbous-full.svg",
  "05-full-moon.svg",
  "06-waning-gibbous-full.svg",
  "07-last-quarter.svg",
  "08-waning-crescent.svg",
];

const FRAME_INTERVAL_MS = 1000; // time each phase stays fully visible

function parseDurationMs(value) {
  const trimmed = value.trim();
  if (trimmed.endsWith("ms")) return parseFloat(trimmed);
  if (trimmed.endsWith("s")) return parseFloat(trimmed) * 1000;
  return 0;
}

function buildStack(stack) {
  PHASE_FILES.forEach((file) => {
    const img = document.createElement("img");
    img.className = "moon-frame";
    img.src = `svgs/${file}`;
    img.alt = file.replace(".svg", "").replace(/^\d+-/, "");
    stack.appendChild(img);
  });

  const frames = stack.querySelectorAll(".moon-frame");
  const holdMs = parseDurationMs(getComputedStyle(stack).getPropertyValue("--hold-duration"));
  let currentIndex = 0;
  frames[currentIndex].classList.add("active");

  setInterval(() => {
    const previous = frames[currentIndex];
    currentIndex = (currentIndex + 1) % frames.length;
    const next = frames[currentIndex];

    // hold the current frame fully static, then grow the next one on top of
    // it so the reveal reads as "growing", not a simultaneous cross-fade
    setTimeout(() => {
      stack.appendChild(next); // reorder to the end so it stacks above every other frame
      next.classList.add("active");
      next.addEventListener("transitionend", function onGrown(event) {
        if (event.propertyName !== "clip-path") return;
        next.removeEventListener("transitionend", onGrown);
        previous.classList.remove("active");
      });
    }, holdMs);
  }, FRAME_INTERVAL_MS);
}

document.querySelectorAll(".moon-stack").forEach(buildStack);

