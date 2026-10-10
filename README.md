# Expense Review Workflow - Aggroso Medium Task

Live: http://localhost:8080

## AI Workflow
- Classify expense description into Travel/Meals/Supplies/Software etc.
- Retrieve matching policy (Sec 4.2 Daily Limit, Sec 5.4 Receipt Required)
- Explain compliance decision with policy evidence citation
- Confidence score with uncertain handling
- Request missing information (receipt, dates, description)

## Deterministic Validation
- Duplicate detection by expenseId / same amount+date
- Totals validation and daily limit check
- Missing receipt detection
- Date validation (future date check)
- Amount limit validation per category

## Reviewer Actions
- Approve / Reject expense
- Request clarification with message
- Override AI category with reason
- Complete decision history timeline

## Run
mvn spring-boot:run
Open http://localhost:8080
Login: investigator / admin