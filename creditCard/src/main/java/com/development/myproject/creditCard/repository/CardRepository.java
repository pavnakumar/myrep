package com.development.myproject.creditCard.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.development.myproject.creditCard.dto.Card;

public interface CardRepository extends CrudRepository<Card, Long>{
	
	public Card findCardByCardNo(String cardNo);
	 @Modifying
	@Query("SELECT c FROM Card c WHERE c.cardNo=?1 and c.status = ?2 and c.cardType = ?3 and c.validTo=?4 and c.cvv=?5 and c.cardPin=?6")
	public Card findCard(String cardNo,String status,String cardType,Date validTo,String cvv,String cardPin);
	 @Modifying
	@Query("update Card c set c.balance = c.balance-?1 ,c.cashBalance=c.cashBalance-?1  where (c.balance-?1)>0  and (c.cashBalance-?1)>0 and c.cardId =?2 and c.cardNo=?3 and c.cardPin=?4")

	public int updateCashTransaction(double amount,long cardId ,String cardNo,String cardPin);
	 @Modifying
	@Query("update Card c set c.balance = c.balance-?1 where (c.balance-?1)>0 and c.cardId =?2 and c.cardNo=?3 and c.cardPin=?4")
	public int updateDebitTransaction(double amount,long cardId ,String cardNo,String cardPin);
	 
	 @Modifying
	 @Query("update Card c set c.balance = c.balance+?1,c.cashBalance=?2 where  c.cardId =?3 and c.cardNo=?4 and c.cardPin=?5" )
	 public int updateCreditTransaction(double amount,double cashAmount,long cardId ,String cardNo,String cardPin);
	 
	 @Modifying
	 @Query("update Card c set c.status =?1 where c.balance >=c.creditLimit and c.status=?2" )
	 public int changeCardStatus(String inactive,String active);

}
