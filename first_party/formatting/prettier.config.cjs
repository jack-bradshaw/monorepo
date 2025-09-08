/**
 * @see https://prettier.io/docs/en/configuration.html
 */
const config = {
  tabWidth: 2,
  printWidth: 100,
  bracketSameLine: false,
  singleAttributePerLine: true,
  xmlSelfClosingSpace: true,
  plugins: [
    require("prettier-plugin-sql"),
    require("@prettier/plugin-xml"),
    require("prettier-plugin-gherkin"),
  ],

  overrides: [
    {
      // XML files don't format properly with a tab width of 2.
      files: ["*.xml"],
      options: {
        tabWidth: 4,
        xmlWhitespaceSensitivity: "strict",
      },
    },
    {
      files: ["*.md"],
      options: {
        proseWrap: "always",
        printWidth: 100,
      },
    },
  ],
};

module.exports = config;
