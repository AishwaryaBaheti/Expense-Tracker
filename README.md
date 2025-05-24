💼 Java Expense Tracker

A command-line Expense Tracker built in Java, developed through multiple phases to demonstrate evolving software design — from in-memory storage to file-based persistence and finally a PostgreSQL database.

🚀 **Features**

Add, view, update, delete expenses
Monthly and total expense summaries
Unique UUID for each expense
Input validation & exception handling
Persistent storage (Phase 2 & 3)

🔄 Phase-wise Progress

🧪 Phase 1 – In-Memory
Core expense tracking logic using List<Expense>
Data lost on exit

📝 Phase 2 – JSON File Storage
Added persistence with Jackson
Expenses saved in expenses.json
Added custom exceptions

🗃️ Phase 3 – PostgreSQL with JDBC
Replaced file storage with PostgreSQL
Used JDBC for DB operations
Auto-creates expenses table on startup

🧰 Technologies Used
Java 17+
PostgreSQL 14+
JDBC
Jackson (Phase 2)
IntelliJ IDEA
Maven (optional)
