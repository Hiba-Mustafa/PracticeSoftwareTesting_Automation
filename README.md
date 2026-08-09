# Automation_PaySky 🧪

A Selenium WebDriver test automation framework built in Java, testing the full shopping flow — registration, login, add to cart, and checkout — on [practicesoftwaretesting.com](https://practicesoftwaretesting.com).

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Selenium](https://img.shields.io/badge/Selenium-4.45.0-43B02A?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.12.0-yellow)
![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)
![Allure](https://img.shields.io/badge/Allure-Reporting-orange)

---

## 📸 Test Report



## ✨ Key Features

- **Page Object Model (POM)** — clean separation between page logic and test logic
- **Centralized explicit waits** — no `Thread.sleep`, all synchronization goes through a shared `Waits` utility
- **Data-driven testing** — test data pulled from external JSON files via JSONPath
- **Randomized test data** — auto-generated emails/passwords for registration tests, avoiding data collisions
- **Allure reporting** — rich, visual test execution reports with step-by-step breakdowns
- **Soft assertions** — multiple checks per test without stopping at the first failure

## ✅ Test Scenarios Covered

| Flow | Description |
|---|---|
| Registration | Creates a new account with randomized, valid data |
| Login | Verifies valid/invalid login scenarios |
| Add to Cart | Adds a product, verifies cart contents and quantity updates |
| Checkout | Completes the purchase flow end to end |

## 🗂️ Project Structure

```
src/
├── main/java/io.PaySky.pages/     → Page Objects (CartPage, LoginPage, ProductPage, RegistrationPage, CheckOutPage, LogoutPage)
│   └── utiles/                    → Waits, JsonReader, RandomData, AllureUtils
├── test/java/
│   ├── Base/BaseTest.java         → Driver setup & teardown
│   ├── pages/                     → Test classes
│   └── test-data/                 → JSON test data
└── resources/
    ├── testng.xml
    └── allure.properties
```

## 🚀 Getting Started

**Prerequisites:** JDK 25, Maven, Firefox (driver binary is handled automatically via Selenium Manager)

```bash
git clone <repo-url>
cd Automation_PaySky
mvn test
```

To run a specific suite, open `testng.xml` in your IDE and run it directly, or configure the classes you want in the `<test>` block.

**View the Allure report:**
```bash
allure serve target/allure-results
```
*(requires the [Allure CLI](https://allurereport.org/docs/install/) installed locally)*

## 🛠️ Tech Stack

| Tool | Purpose |
|---|---|
| Java | Core language |
| Selenium WebDriver | Browser automation |
| TestNG | Test execution & assertions |
| Maven | Build & dependency management |
| Allure | Test reporting |
| JSONPath | Test data parsing |

---

📫 Built by [Your Name](https://github.com/your-profile) — feel free to connect!
