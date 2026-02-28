/**
 * Opens and closes navigation menus as screen size changes to create a responsive menu UI.
 *
 * The navigation component CSS (`/static/css/components/navigation`) styles menus as dropdown
 * pickers on mobile devices, and horizontal button rows on desktop devices. This script closes the
 * mobile menu by default to avoid occluding the page content, while opening the desktop menu
 * permanently to avoid hiding it.
 *
 * The selection of mobile/desktop is driven by device.css which passes a --device-type to the
 * function via a pseudo-after element on the root HTML tag. The function runs on load and on every
 * window-size-changed event.
 *
 * Note on Resize Logic: This script tracks `previousDeviceType` to distinguish between genuine
 * device profile changes (e.g. rotation/resizing) and minor layout shifts
 * (e.g. scrollbar appearance). This prevents the menu from aggressively auto-closing during
 * harmless resize events, which was an issue previously (causing menu invisibility on taller pages).
 */
(function () {
  let previousDeviceType = null;

  function onWindowSizeChanged() {
    const deviceType = getComputedStyle(document.documentElement, "::after")
      .getPropertyValue("--device-profile")
      .trim()
      .replace(/"/g, ""); // Remove quotes

    if (previousDeviceType !== deviceType) {
      document.querySelectorAll("nav details").forEach((details) => {
        if (deviceType === "mobile") {
          details.removeAttribute("open");
        } else {
          details.setAttribute("open", true);
        }
      });
      previousDeviceType = deviceType;
    }
  }

  window.addEventListener("resize", onWindowSizeChanged);
  onWindowSizeChanged();
})();
