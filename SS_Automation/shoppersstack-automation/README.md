# ShoppersStack Automation (Selenium + Java + TestNG)

## Run
```
mvn clean test                      # full suite (Chrome full + Firefox smoke)
mvn clean test -Dheadless=true      # headless
mvn clean test -Dbrowser=edge       # force a browser for the default run
```
Needs JDK 17+, Maven, Chrome (and Firefox for the cross-browser run). Selenium Manager downloads drivers automatically.

## Output (after every run)
- `reports/AutomationResults.xlsx`  - all tests with PASS/FAIL/SKIP
- `reports/PassedTestCases.xlsx`    - only the passed ones (the "second file")
- `reports/screenshots/`            - screenshot per failure

## If a test fails with NoSuchElement / Timeout
Open DevTools on the page, copy the real id/xpath and update the matching line in
`src/test/java/com/shoppersstack/utils/Locators.java`. Nothing else needs to change.

## Coverage (testing types via TestNG groups)
smoke, functional, negative, integration, system (E2E), regression, ui, boundary, security, performance, compatibility (cross-browser + mobile viewport).
Each test carries its type(s) in the `groups` attribute and in the report's "Testing Type" column.
