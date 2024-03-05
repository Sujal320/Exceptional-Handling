class InsufficientFundsException extends Exception{
    public String toString(){
        return "Insufficient Funds!";
    }
}

class InvalidAccountNumberException extends Exception{
    public String toString(){
        return "Invalid Account NUmber!";
    }
}

class IncorrectPINException extends Exception{
    public String toString(){
        return "Incorrect Pin!";
    }
}


// This class represents a bank account
class Account{
    private int AccNo;
    private String AccHolderName;
    private int AccBalance;
    protected int PIN;

    //Constructor which will get details like Account No., Account Holder's name and PIN to proceed further
    public Account(int AccNo, String AccHolderName, int PIN){
        this.AccNo = AccNo;
        this.AccHolderName = AccHolderName;
        this.PIN = PIN;
    }
    //Constructor which can take Account Balance too
    public Account(int AccNo, String AccHolderName, int PIN, int AccBalance){
        this.AccNo = AccNo;
        this.AccHolderName = AccHolderName;
        this.PIN = PIN;
        this.AccBalance = AccBalance;
    }
    //to get Account Number
    public int getAccountNumber(){
        return AccNo;
    }
    //to get Account Holder's Balance
    public int getBalance() {
        return AccBalance;
    }
    //to set Account Holder's Pin
    public void setPin(int PIN) {
        this.PIN = PIN;
    }

    public void depositeMoney(int Amount)
        {AccBalance+=Amount;}

    public void withdrawMoney(int Amount) throws InsufficientFundsException{
        if(Amount>AccBalance){
            throw new InsufficientFundsException();
        }
        AccBalance-=Amount;
        System.out.println(Amount+" withdrawn"+"\n"+"Remaining balance: "+AccBalance);
    }
    public void verifyPIN(int PIN) throws IncorrectPINException{
        if(this.PIN!=PIN){
            throw new IncorrectPINException();
        }
    }
}
class Bank{
    private int MAX_ACCOUNTS = 100;
    private Account[] accounts;//declares an array named "accounts" that can hold objects of the "Account" class.
    private int numAccounts; //to keep track of the number of accounts stored in the array

    public Bank(){
        accounts = new Account[MAX_ACCOUNTS];
        numAccounts = 0;
    }
    //to create your account in the bank
    public void createAccount(int AccNo, String AccHolderName, int AccBalance, int PIN){
        if(numAccounts<MAX_ACCOUNTS){
            Account account = new Account(AccNo, AccHolderName, AccBalance, PIN);
            accounts[numAccounts] = account;
            numAccounts++;
        }
        else{
            System.out.println("Cannot create more accounts. Maximum limit reached.");
        }
    }
    //to find your Account public details
    public Account findAccount(int AccNo) {
        for (int i = 0; i < numAccounts; i++) {
            if (accounts[i].getAccountNumber() == AccNo) {
                return accounts[i];
            }
        }
        return null; // Account not found
    }
    //to deposite
    public void deposite(int AccNo, int AccBalance)throws InvalidAccountNumberException{
        Account account = findAccount(AccNo);
        if(account != null){
            account.depositeMoney(AccBalance);
        }
        else{
            throw new InvalidAccountNumberException();
        }
    }
    //to withdraw
    //here only InvalidAccountNumberException is thrown becoz InsufficientFundsException is already thrown when we do withdrawMoney(AccBalance) method
    public void withdraw(int AccNo, int AccBalance)throws InsufficientFundsException, InvalidAccountNumberException{
        Account account = findAccount(AccNo);
        if(account!=null){
            account.withdrawMoney(AccBalance);
        }  
        else{
            throw new InvalidAccountNumberException();
        }
    }
    //to check balance
    public int AccBalance(int AccNo) throws InvalidAccountNumberException{
        Account account = findAccount(AccNo);
        if(account!=null){
            return account.getBalance();
        }  
        else{
            throw new InvalidAccountNumberException();
        }
    }
    //to authenticate your account
    //here only InvalidAccountNumberException is thrown becoz IncorrectPINException is already thrown when we do verifyPIN(PIN) method 
    public void authenticate(int AccNo, int PIN) throws IncorrectPINException, InvalidAccountNumberException {
        Account account = findAccount(AccNo);
        if(account!=null){
            account.verifyPIN(PIN);
        }  
        else{
            throw new InvalidAccountNumberException();
        }
    }

}
public class BankingSystem{
    public static void main(String args[]){

        Bank bank = new Bank();

        //creating account
        bank.createAccount(23346, "Sujal", 20000, 1060);
        bank.createAccount(23419, "Kumar", 30000, 1061);

        //Depositing money
        try{
            bank.deposite(23346, 10000);
            bank.deposite(23419, 10000);
        }
        catch(InvalidAccountNumberException e){
            System.out.println("Error: "+e);
        }

        //wtihdrawing money
        try{
            bank.withdraw(23346, 100);
            bank.withdraw(23419, 100);
        }
        catch(InsufficientFundsException | InvalidAccountNumberException e){
            System.out.println("Error: "+e);
        }

        //checking balance
        try{
            int b1 = bank.AccBalance(23346);
            System.out.println("Balance for account 23346: " +b1);
            int b2 = bank.AccBalance(23419);
            System.out.println("Balance for account 23419: " +b2);

        }
        catch(InvalidAccountNumberException e){
            System.out.println("Error: "+e);
        }

        //Authenticating User
        try{
            bank.authenticate(23346, 1060);
            System.out.println("Authentication Successful for User 23346.");
        }
        catch(InvalidAccountNumberException | IncorrectPINException e){
            System.out.println("Error: "+e);
        }
    }
}