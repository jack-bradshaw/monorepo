/**
 * Automatically expands <details> elements when navigated via a URL hash anchor.
 *
 * When a user navigates to a specific ID (e.g., /journal/topics#spaces), this script
 * ensures that if that ID is inside a collapsed <details> section, the section
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

    // Traverse up to find any <details> parents and open them
    let parent = targetElement.parentElement;
    let expandedAny = false;
    while (parent) {
      if (parent.tagName === 'DETAILS' && !parent.open) {
        parent.open = true;
        expandedAny = true;
      }
      parent = parent.parentElement;
    }

    // If we expanded something, or just navigated, ensure we are at the right scroll position
    // Small delay to allow any layout shifts from opening the details to settle
    if (expandedAny) {
      setTimeout(() => {
        targetElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 50);
    }
  }

  // Initialize on load and listen for future hash changes
  window.addEventListener('DOMContentLoaded', expandTarget);
  window.addEventListener('hashchange', expandTarget);
})();
