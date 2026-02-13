/**
 * Automatically expands collapsed sections when linking to their contents.
 *
 * When a user navigates to a specific section (e.g., /journal/topics#spaces), this script
 * ensures that if the section exists inside a collapsed <details> block, the block
 * is opened so the content is visible. It also ensures the target is scrolled into view.
 */
(function () {
  /**
   * Finds and expands all parent <details> elements for a given target.
   */
  function expandTarget() {
    const hash = window.location.hash;
    if (!hash) return;

    const targetId = decodeURIComponent(hash.substring(1));
    const targetElement = document.getElementById(targetId);
    if (!targetElement) return;

    const expandedAny = expandDetailsParents(targetElement);
    if (expandedAny) {
      // Ensures the scroll happens after block opening (first frame) and rendering (second frame).
      requestAnimationFrame(() => {
        requestAnimationFrame(() => {
          targetElement.scrollIntoView({ behavior: "instant", block: "start" });
        });
      });
    }
  }

  /**
   * Opens all collapsed <details> blocks containing the target element, and returns whether
   * any changes actually occured.
   */
  function expandDetailsParents(targetElement) {
    let expandedAny = false;
    let parent = targetElement.parentElement;
    while (parent) {
      if (parent.tagName === "DETAILS" && !parent.open) {
        parent.open = true;
        expandedAny = true;
      }
      parent = parent.parentElement;
    }
    return expandedAny;
  }

  window.addEventListener("DOMContentLoaded", expandTarget);
  window.addEventListener("hashchange", expandTarget);
})();
