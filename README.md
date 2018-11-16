# Coding Challenge for New Grad
a simple java project work as virtual wallet consisting of multiple bank accounts, linked with single holder. In sake of this coding challenge, I make some assumptions,

#### ID & Timestamp
- Precision of transaction timestamp is millisecond. If two transaction occurs within one millisecond, they will have a same timestamp.
- My program will generate random UUID for each transaction, atomic counter-like account id for each user. A user may have at most $2^{63}-1$ accounts. However I didn't develop the unique user id generator. If necessary, I'm willing to add a Bank class contains user id generator.

#### Account
- Account can be generated either with or without initial balance. User can top up one account from another account, or deposit cash into it.
- User only have access to their own bank account. They can not get the balance or records of other user's account.

#### Transfer
- Transferring between accounts under same user is a natural thing, given enough balance in the source account.
- Transferring between two users may happen, with support of default account. First of all, both users must set their own default account. Next, they will transfer money between two default accounts.
- Based on two basic transferring method mentioned above, I can derive more complicated transferring function, such as, transferring between two non-default accounts of different users.
