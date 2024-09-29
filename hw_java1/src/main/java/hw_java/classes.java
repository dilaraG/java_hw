package hw_java;

class BankAccount {
    private String iban;
    private String bic;
    private String accountHolder;

    public BankAccount(String iban, String bic, String accountHolder) {
        this.iban = iban;
        this.bic = bic;
        this.accountHolder = accountHolder;
    }

    // Геттеры
    public String getIban() {
        return iban;
    }

    public String getBic() {
        return bic;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}

class Employee {
    private long id;
    private String email;
    private String phone;
    private String address;
    private BankAccount bankAccount;

    public Employee(long id, String email, String phone, String address, BankAccount bankAccount) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bankAccount = bankAccount;
    }

    // Геттеры
    public long getId() {
        return id;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}

class Individual extends Employee {
    private String firstName;
    private String lastName;
    private boolean hasChildren;
    private int age;

    public Individual(long id, String email, String phone, String address, BankAccount bankAccount,
                      String firstName, String lastName, boolean hasChildren, int age) {
        super(id, email, phone, address, bankAccount);
        this.firstName = firstName;
        this.lastName = lastName;
        this.hasChildren = hasChildren;
        this.age = age;
    }

    // Геттеры
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}

enum CompanyType {
    SARS,
    SARL
}

class Company extends Employee {
    private String companyName;
    private CompanyType type;

    public Company(long id, String email, String phone, String address, BankAccount bankAccount,
                   String companyName, CompanyType type) {
        super(id, email, phone, address, bankAccount);
        this.companyName = companyName;
        this.type = type;
    }

    // Геттеры
    public String getCompanyName() {
        return companyName;
    }

    public CompanyType getType() {
        return type;
    }
}