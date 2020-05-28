package com.development.myproject.creditCard.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.development.myproject.creditCard.dto.Account;
import com.development.myproject.creditCard.dto.Card;
import com.development.myproject.creditCard.dto.CreditProperty;
import com.development.myproject.creditCard.dto.EndOfTheMonthReport;
import com.development.myproject.creditCard.dto.FacilityTemplate;
import com.development.myproject.creditCard.dto.Registration;
import com.development.myproject.creditCard.dto.Transaction;
import com.development.myproject.creditCard.enums.Status;
import com.development.myproject.creditCard.enums.TransactionType;
import com.development.myproject.creditCard.enums.util.AccountNumberGenerator;
import com.development.myproject.creditCard.exception.ExceptionHandler;
import com.development.myproject.creditCard.repository.AccountRepository;
import com.development.myproject.creditCard.repository.CardRepository;
import com.development.myproject.creditCard.repository.CreditPropertyRepository;
import com.development.myproject.creditCard.repository.EndOfTheMonthReportRepository;
import com.development.myproject.creditCard.repository.FacilityTemplateRepository;
import com.development.myproject.creditCard.repository.TransactionRepository;
import com.development.myproject.creditCard.util.CommonUtil;


@Qualifier("CREDIT_CARD")
@Service
public class CreditCardAccountService implements IAccountService {
	@Resource
	AccountRepository accountRepository; 
	
	@Resource
	FacilityTemplateRepository facilityTemplateRepository;
	
	@Resource
	CardRepository cardRepository;
	
	@Resource
	CreditPropertyRepository creditPropertyRepository;
	
	
	@Resource
	TransactionRepository transactionRepository;
	
	@Resource 
	EndOfTheMonthReportRepository endOfTheMonthReportRepository;
	
	
	private static final double  DEFAULT_CREDIT_LIMIT = 100000;
	
	private static final double DEFAULT_CASH_LIMIT = 40;
	
	private static final int DEFAULT_VALID_YEARS=5;
	
	private static final int DEFAULT_LATE_PAYMENT_CHARGES_PER =5;
	
	private static final int DEFAULT_LATE_PAYMENT_CHARGES_AMOUNT =100;


	private static final String  DEFAULT_CREDIT_LIMIT_KEY = "DEFAULT_CREDIT_LIMIT";
	
	private static final String  DEFAULT_CASH_LIMIT_KEY = "DEFAULT_CASH_LIMIT";
	
	private static final String DEFAULT_VALID_YEARS_KEY="DEFAULT_VALID_YEARS";

 

	@Override
	public Account createAccount(Registration registration) {
		Account account = new Account();
		account.setCreatedDate(new Date());
		account.setFacilityTemplateId(registration.getFacilityTemplateId());;
		account.setCustomer(registration.getCustomer());
		account.setStatementGenerationDate(new Date());
		Card card = generateCardDetails(registration);
		card.setAccount(account);
		account.setCard(card);
		accountRepository.save(account);
		registration.setAccount(account);
        return account; 
	}	
	
	
	
	public Account generateStatement(long accountId) {
	Optional<Account> optionalAOptional = accountRepository.findById(accountId);
		if(optionalAOptional.isEmpty()) {
			throw new RuntimeException("no account found");
		}
		Account account = optionalAOptional.get();
		Card card = account.getCard();
	
		return account;
	}
	
	
	private Card generateCardDetails(Registration registration) {
		Optional<FacilityTemplate> fOptional= facilityTemplateRepository.findById(registration.getFacilityTemplateId());
		Card card = new Card();
		if(fOptional.isPresent()) {
			card.setCardType(fOptional.get().getType());
			card.setCvv(AccountNumberGenerator.GENERATE_CREDIT_CARD_DETAILS.generatorCvvNo(3));
			card.setCreditLimit(generateLimit(card, false, registration));
			card.setCashLimit(generateCashLimit(card, registration));
			card.setValidFrom(new Date());
			card.setCustomer(registration.getCustomer());
			card.setStatus(Status.ACTIVE.getStatus());
			card.setBalance(card.getCreditLimit());
			card.setCashBalance(card.getCashLimit());
			card.setValidTo(generateValidToDate());
			card.setCardPin(AccountNumberGenerator.GENERATE_CREDIT_CARD_DETAILS.generatorCvvNo(3));

			
		}else {
			throw new ExceptionHandler("No facility found");
		}
		
		
		return card;
	}
	
	private double generateLimit(Card card,boolean reviseLimit,Registration registration) {
		
		if(reviseLimit) {
			//get reviseLimit
		}
		
		if(registration.getProposedLimit()==0) {
			Optional<CreditProperty> cpOptional = Optional.ofNullable(creditPropertyRepository.findByName(DEFAULT_CREDIT_LIMIT_KEY));
			if(cpOptional.isPresent()) {
				return Double.parseDouble(cpOptional.get().getValue());
			}
			return DEFAULT_CREDIT_LIMIT;
		}
		
		return registration.getProposedLimit();
		
	}
	
	private double generateCashLimit(Card card, Registration registration) {
		if(registration.isAllowCashLimit()) {
			Optional<CreditProperty> cpOptional = Optional.ofNullable(creditPropertyRepository.findByName(DEFAULT_CASH_LIMIT_KEY));
             double percentage =0;
             if(cpOptional.isPresent()) {
 				percentage= Double.parseDouble(cpOptional.get().getValue());
 			}else {
 				percentage = DEFAULT_CASH_LIMIT;
 			}

			return CommonUtil.calculateAmountByPercentage(card.getCreditLimit(), percentage);
		}
		return 0;
		
	}
	
	private Date generateValidToDate() {
	
			Optional<CreditProperty> cpOptional = Optional.ofNullable(creditPropertyRepository.findByName(DEFAULT_VALID_YEARS_KEY));
             int years =0;
             if(cpOptional.isPresent()) {
            	 years= Integer.parseInt(cpOptional.get().getValue());
 			}else {
 				years = DEFAULT_VALID_YEARS;
 			}

			return CommonUtil.getDateByGivenYears(years);
		
		
	}
	
     public boolean validateCreditCard(Card card) {
		Optional<Card> cpOptional = Optional.ofNullable(cardRepository.findCard(card.getCardNo(),Status.ACTIVE.getStatus(),card.getCardType(),card.getValidTo(),card.getCvv(),card.getCardPin()));
        if(cpOptional.isPresent()) {
        	Card creditCard = cpOptional.get();
        	return CommonUtil.compareTwoDate(creditCard.getValidTo(), new Date());
        	
        }
        return false;
      }
     
        @Override
		@Transactional
		public Card doTransaction(Card card, Transaction transaction) {
			Card current = cardRepository.findCardByCardNo(card.getCardNo());
			int count =0;
			if (CommonUtil.hasAvalue(current) && CommonUtil.hasAvalue(transaction)) {
				if (TransactionType.CASH_WITHDRAW.getTransactionType().equals(transaction.getTransactionType())) {
                    count=  cardRepository.updateCashTransaction(transaction.getAmount(),card.getCardId(),card.getCardNo(),card.getCardPin());
				} else if (TransactionType.DEBIT.getTransactionType().equals(transaction.getTransactionType())) {
					count= cardRepository.updateDebitTransaction(transaction.getAmount(),card.getCardId(),current.getCardNo(),card.getCardPin());
				}else if (TransactionType.CREDIT.getTransactionType().equals(transaction.getTransactionType())) {
					double cashBalance = current.getCashBalance() + transaction.getAmount();
					count= cardRepository.updateCreditTransaction(transaction.getAmount(),cashBalance>current.getCashBalance()?current.getCashLimit():cashBalance,card.getCardId(),card.getCardNo(),card.getCardPin());
				}else {
					if(TransactionType.INTEREST_ON_CASH_WITHDRAW.getTransactionType().equals(transaction.getTransactionType())) {
						count= cardRepository.updateDebitTransaction(transaction.getAmount(),card.getCardId(),current.getCardNo(),card.getCardPin());
					}
				}
				if(count==0) {
					throw new ExceptionHandler("please check limit");
				}
				
				transaction.setTransactionDate(new Date());
				transaction.setCard(current);
				transactionRepository.save(transaction);
				return current;

			} else {
				throw new ExceptionHandler("Invalid details");
			}

		}



		@Override
		public String getType() {
			// TODO Auto-generated method stub
			return "CREDIT_CARD";
		}
		
		
     
		private  boolean isDelinquent(EndOfTheMonthReport endOfTheMonthReport) {
			if(endOfTheMonthReport.getOutstandingAmount()>=100) {
				Optional<List<Transaction>> traOptions = Optional.ofNullable(transactionRepository.findTransaction(endOfTheMonthReport.getCardId(), CommonUtil.getDateByGivenMonths(-3), new Date(), TransactionType.LATE_CHARGE_INTEREST.getTransactionType()));
                if(traOptions.isPresent()&&traOptions.get().size()>=3) {
                	return true;
                }
			}
			return false;
		}
		
		
		
		@Override
		public Card closeAccount(Card card){
			int count  = cardRepository.changeCardStatus(Status.INACTIVE.getStatus(),Status.ACTIVE.getStatus());
			
			if(count==0) {
				throw new ExceptionHandler("Either outstanding pending : " + card.getBalance()+  "or account is not in active state" + card.getStatus());
			}
			
			return cardRepository.findById(card.getCardId()).get();
			
		}
		
		public void doEOMProcess(Account account) {
			Optional<List<EndOfTheMonthReport>> optional = Optional.ofNullable(endOfTheMonthReportRepository.findRecordByAccountId(account.getId()));
			Card card = account.getCard();
			double cashWithAmountOfCurrentMonth = 0.0;

			if (optional.isPresent()) {
				EndOfTheMonthReport lastMonthEndOfTheMonthReport = optional.get().get(0);
				cashWithAmountOfCurrentMonth = calculateCurrentMonthCashWithDraw(card, lastMonthEndOfTheMonthReport);
				double payment = calculateBillPayment(card, lastMonthEndOfTheMonthReport);
				if (payment == 0) {
					
					// lateFee charges
					double lateFeeChargePer = CommonUtil.calculateAmountByPercentage(lastMonthEndOfTheMonthReport.getOutstandingAmount(), DEFAULT_LATE_PAYMENT_CHARGES_PER);
					double lateFee = lateFeeChargePer > DEFAULT_LATE_PAYMENT_CHARGES_AMOUNT ? lateFeeChargePer: DEFAULT_LATE_PAYMENT_CHARGES_AMOUNT;
					Transaction lateFeeTransaction = generateTransactionType(lateFee, card,TransactionType.LATE_CHARGE_INTEREST.getTransactionType());
					doTransaction(card, lateFeeTransaction);
					
				}
				
				
				// outstanding interest on lastmoontEom
				
				Transaction outStandingInterest = generateTransactionType(CommonUtil.calculateAmountByPercentage(cashWithAmountOfCurrentMonth, 2), card,TransactionType.INTEREST_ON_UN_PAID_OUTSTANDING.getTransactionType());
				doTransaction(card, outStandingInterest);
				
				
				//Delinquent change status
				 boolean isDelinquent = isDelinquent(lastMonthEndOfTheMonthReport);
				 if(isDelinquent){
					 cardRepository.changeCardStatus(Status.DELiNQUENT.getStatus(),Status.ACTIVE.getStatus());
				 }
				 

			} else {
				cashWithAmountOfCurrentMonth = card.getCashLimit() - card.getCashBalance();
			}
			
			
			// current month money withdraw charges
			Transaction transaction = generateTransactionType(CommonUtil.calculateAmountByPercentage(cashWithAmountOfCurrentMonth, 2), card,TransactionType.INTEREST_ON_CASH_WITHDRAW.getTransactionType());
			doTransaction(card, transaction);
			
			
			//Current EOM Report 
			
			Card current = cardRepository.findCardByCardNo(card.getCardNo());
			account.setCard(current);
			EndOfTheMonthReport currentEOMReport = generateEodEndOfTheMonthReport(account);
			endOfTheMonthReportRepository.save(currentEOMReport);
			
			//Completed
			
			
		}
        
        
        private double calculateCurrentMonthCashWithDraw(Card card,EndOfTheMonthReport lastMonthReport) {
        	List<Transaction> currentMonthCashTransactions = getCurrentCycleTransactions(card, lastMonthReport.getCreatedDate(), new Date(),TransactionType.CASH_WITHDRAW.getTransactionType());
        	double cashWithDraw =  0d;
        	if(CommonUtil.hasAvalue(currentMonthCashTransactions)) {
        		for (Transaction transaction : currentMonthCashTransactions) {
					 cashWithDraw  = cashWithDraw + transaction.getAmount();
				}
        	}
        	
        	return cashWithDraw;
        		
        }
        
        private double calculateBillPayment(Card card,EndOfTheMonthReport lastMonthReport) {
        	List<Transaction> currentMonthCashTransactions = getCurrentCycleTransactions(card, lastMonthReport.getCreatedDate(), new Date(),TransactionType.CREDIT.getTransactionType());
        	double payment =  0d;
        	if(CommonUtil.hasAvalue(currentMonthCashTransactions)) {
        		for (Transaction transaction : currentMonthCashTransactions) {
        			payment  = payment + transaction.getAmount();
				}
        	}
        	
        	return payment;
        		
        }
        
       
        
        private Transaction generateTransactionType(double amount,Card card,String transactionType) {
			Transaction transaction = new Transaction();
			transaction.setTransactionDate(new Date());
			transaction.setCard(card);
			transaction.setTransactionType(transactionType);
			transaction.setAmount(amount);
			return transaction;
        }
        
        private EndOfTheMonthReport generateEodEndOfTheMonthReport(Account account) {
        	Card card  = account.getCard();
    		EndOfTheMonthReport endOfTheMonthReport = new EndOfTheMonthReport();
    		endOfTheMonthReport.setAccountId(account.getId());
    		endOfTheMonthReport.setCashWithDrawAmount(card.getCashLimit()-card.getCashBalance());
    		endOfTheMonthReport.setOutstandingAmount(card.getCreditLimit()-card.getBalance());
    		endOfTheMonthReport.setCustomerId(account.getCustomer().getCustomerId());
    		endOfTheMonthReport.setCreatedDate(new Date());
    		return endOfTheMonthReport;
        }
        
        private List<Transaction> getCurrentCycleTransactions(Card card,Date lastMonthDate , Date todayDate,String transactionType){
		     Optional<List<Transaction>> traOptions = Optional.ofNullable(transactionRepository.findTransaction(card.getCardId(),  lastMonthDate,todayDate, transactionType));
		      return traOptions.get();
			}
        }
     
    

