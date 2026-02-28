/**
 * @fileoverview Handles the privacy notice banner logic.
 *
 * The banner is shown by default and hidden when the user accepts or declines consent. The user's
 * consent/denial is saved to local storage and used to determine whether to show the banner on
 * subsequent page loads. Google Analytics consent mode is updated when the user accepts or declines
 * consent to enable/disable analytics tracking.
 */
(function () {
  "use strict";

  const banner = document.getElementById("privacy-notice");

  /** Shows the privacy notice. Safe to call and has no effect if banner is already shown. */
  function showBanner() {
    banner.style.display = "flex";
  }

  /** Hides the privacy notice. Safe to call and has no effect if banner is already hidden. */
  function hideBanner() {
    banner.style.display = "none";
  }

  const storageKey = "analytics_consent_granted";

  /**
   * Updates the consent state in Google Analytics and local storage.
   *
   * @param {boolean} granted Whether the user granted consent.
   */
  function onResponse(granted) {
    gtag("consent", "update", {
      ad_storage: granted ? "granted" : "denied",
      ad_user_data: granted ? "granted" : "denied",
      ad_personalization: granted ? "granted" : "denied",
      analytics_storage: granted ? "granted" : "denied",
    });
    localStorage.setItem(storageKey, granted ? "true" : "false");
    hideBanner();
  }

  const savedConsent = localStorage.getItem(storageKey);
  if (savedConsent === null) {
    showBanner();
  } else {
    onResponse(savedConsent === "true");
  }

  document.getElementById("accept-privacy").addEventListener("click", function () {
    onResponse(true);
  });

  document.getElementById("decline-privacy").addEventListener("click", function () {
    onResponse(false);
  });
})();
