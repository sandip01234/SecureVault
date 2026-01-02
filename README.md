# Secure Vault

Secure Vault is a **Java Swing-based desktop application** that allows users to securely store, retrieve, and manage files with **version control** and **encryption**. All files are encrypted before storage, and multiple versions are tracked automatically.

This project demonstrates practical use of **core Java concepts**, including:

- **OOP:** Abstraction, Encapsulation, Inheritance, Polymorphism
- **File Handling:** `java.io` & `java.nio.file`, metadata, atomic operations
- **Collections:** `HashMap`, `TreeMap`, `List`, `ConcurrentHashMap`
- **Multithreading:** Background tasks, thread-safe operations, Producer–Consumer pattern
- **Serialization:** Save and restore user data and file metadata
- **Exception Handling:** Custom exceptions, graceful recovery
- **Security:** SHA-256 password hashing, AES file encryption
- **Java 8+ Features:** Streams, Lambdas, Optional, Functional Interfaces

It’s a **hands-on demonstration of real-world Java skills**, combining GUI, backend logic, and secure file management.

---

## Features

- **File Upload**: Upload files securely to the vault.
- **File Download**: Download any version of a file.
- **Delete Files**: Delete a file and all its versions.
- **Version Management**: Automatically tracks file versions.
- **Encryption**: AES-based encryption ensures files are securely stored.
- **User Interface**:
    - Table view showing file name, latest version, and upload date.
    - Buttons for Upload, Download, and Delete.
    - Confirmation dialogs for critical actions.

---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/sandip01234/SecureVault.git
2. Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse).
3. Ensure you have Java 8 or higher installed.
4. Run the main class `SecureVaultApp`.java to start the application.

### Usage
1. Launch the application.
2. Use the "Upload" button to add files to the vault.
3. Select a file from the table and use the "Download" button to retrieve it.
4. Select a file and use the "Delete" button to remove it from the vault.
5. Confirm actions in the dialog boxes that appear.
6. Enjoy secure file management!
### DataStorage
- Files are stored in an encrypted format in the `vault` directory.
- Metadata is stored in vault/metadata.ser.

### License
MIT License

Copyright (c) 2026 Sandip Kumar Shah

Permission is hereby granted, free of charge, to any person obtaining a copy.
