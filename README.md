# AnzCodingChallenge

Problem statment given in the pdf file - tech-programming-test-fxcalculator-1-0-7.pdf

Some details about the program -

1. Program contains a main class that will validate and consume input parameters, invoke calculator service and get the results
2. Calculator service needs conversion rates, decimal point details and cross currency matrix. CalculatorService will read all these attributes from csv files present in resources folder. Idea is to separate business rules from code.
3. Calculator service has been designed to calculate fx conversion recursively. Default currency for resolving cross currency conversions is USD. For using any other currency, an entry has to be made in the csv file - cross_currency.csv
4. New currencies and conversion rates can be added to csv files and Currency enum. However, since program is using a recursive function, it is imperative to write test cases for new currencies, otherwise without valid conversion_rate & cross_currency entries, program may fall in to never ending loop.
5. For the sake of simplicity, Spring is not used.
6. Code coverage stands at 100% for branch and 96% for line. Test coverage report can be obtained by running command - gradlew clean build. Report will be available at location - build/jacocoHtml/index.html

Assumptions -

1. While inverting a currency rate, value will be rounded off to 4th decimal place.
2. Program will throw exception if invalid/incorrect input parameters are used.
3. Conversion rates, Decimal point details and cross currency matrix details are to be read from a csv file. If validation fails while parsing csv file, then program will throw exception and not proceed further.

Further improvements -

1. Use of spring to use dependencies, use of component test cases to check integration of spring components.
2. Spring environment specific application properties can be used to read decimal points, input parameter values.
3. Line and branch code coverage can be enforced using jacocoTestCoverageVerification.
4. All the validation failure details in input csv file can be collected and returned back for further analysis.
5. Lombok annotations can be used to remove boilerplate code such as getter and setters, constructors.
6. With increase in conversion rates and currencies, values can be read from file and maintained in in-memory cache with expiry on availability of set of new values. Also, other options such as drools based rule engine can be used to maintain complex business rules.
