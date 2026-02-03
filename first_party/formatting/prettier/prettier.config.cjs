/**
 * @see https://prettier.io/docs/en/configuration.html
 */
const config = {
  tabWidth: 2,
  printWidth: 100,
  bracketSameLine: false,
  singleAttributePerLine: true,
  xmlSelfClosingSpace: true,
  plugins: ["prettier-plugin-sql", "@prettier/plugin-xml", "prettier-plugin-gherkin"],

  overrides: [
    {
      files: ["*.xml"],
      options: {
        parser: "xml",
        tabWidth: 4,
        xmlWhitespaceSensitivity: "ignore",
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
