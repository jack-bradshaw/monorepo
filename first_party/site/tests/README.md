# Tests

Tests for [site](/first_party/site).

## Structure

The tests are divided into three main groups:

- Content tests, which are focused on the static content of the site.
- Menu tests, which are focused on the dynamic menus.
- Cookie banner tests, which are focused on the GDPR privacy banner.

Each group is further decomposed into:

- Appearance tests, which use screendiffing to compare the live appearance of the site against
  checked-in goldens.
- Behaviour tests, which use regular assertions to check the site functions correctly.

Furthermore, some groups have the following special tests:

- Presence tests, which use regular assertions to check the presence of specific elements in the
  DOM.
- Expansion tests, which verify the default expansion state of the UI via regular assertions on the
  DOM.

Further breakdown is used to organise code as required.

## Abstraction

The complexity of the site requires hundreds of tests for full coverage; therefore, the tests are
parameterised and implemented as abstract tests and concrete implementations. Abstract base classes
define parameterised cases and subclasses provide the specific parameters. This aids maintainability
and performance; however, the cookie banner tests are simple enough to not require this
optimisation, and are concrete tests without abstractions.

## Overlap

There is a considerable degree of overlap between the content appearance tests and the menu
appearance tests, which is intentional, because it allows the test suites to be explicit about what
is exercised and what is checked. While it does cost extra compute during test runs, it creates
independence between the test suites, which promotes maintainability and simplifies debugging.

# Goldens

The screendiff tests work by driving the UI to a target state, taking a screenshot, and comparing it
to a golden file. The goldens were generated on the presubmit machine, and the tests are highly
susceptible to minor host variation; therefore, the tests usually fail on other hosts (e.g. local
developer workstations), and should only be run via presubmit. Presubmit was chosen as the source of
truth because it represents the final gatekeeper to main that must be satisfied before submission.

Caveats:

- Screenshots are stored as JPG files to balance quality with size.
- Most tests check the entire page, but a few check only the default area because the full page is
  too long for an image.

## Utilities

This package contains various helpers and utilities that are used across tests. They are generic to
Playwright and Bazel, and could be moved to a separate 1P directory (with some minor improvements
for package structure). This was not done at time of submission to prioritise other work, but
nothing blocks the work.

## Parameterisation

The tests vary in how they are parameterised, with some using screen size, others using the
expansion/collapse state of menus, and others using a combination. In all cases the parameterisation
was chosen based on the conditions that need to be tested. For example, the
ContextualMenuAppearanceTest is parameterised by screen size, because screen size is a primary
contributor to appearance, whereas ContextualMenuBehaviourTest is parameterised by whether the menu
is expanded or collapsed, because expansion/collapsed state is the primary driver of behaviour. In
many cases screen size does affect test implementation indirectly; however, tests are parameterised
by the conditions under test, not the implementation details.

## Representation

Various tests check item pages (e.g. journal item, journal series, gallery item, gallery series) but
instead of checking every page they only check a single instance of each. This aids performance and
maintainability.

## Validation

Tests use screendiffing only when absolutely necessary since such tests are slow to run. Checking
the DOM often provides the same level of certainty and avoids a human needing to check changed
screendiffs.
