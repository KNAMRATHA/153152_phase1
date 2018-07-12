
package com.cg.mypaymentapp.service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;


public class WalletServiceImpl implements WalletService{

private WalletRepo repo=new WalletRepoImpl();
	
	/*public WalletServiceImpl(Map<String, Customer> data){
		repo= new WalletRepoImpl(data);
	}*/
	public WalletServiceImpl(WalletRepo repo) 
	{
		
	}

	public WalletServiceImpl() {
		
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) {
		Customer customer1 = new Customer();
		customer1.setName(name);
		customer1.setMobileNo(mobileNo);
		Wallet wallet = new Wallet();
		wallet.setBalance(amount);
		customer1.setWallet(wallet);
		boolean b =repo.save(customer1);
		if(b)
		return customer1;
		else
			throw new InvalidInputException("Account is already created");

	}
	public Customer showBalance(String mobileNo) {
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		Customer customer2=repo.findOne(sourceMobileNo);
		Customer customer3=repo.findOne(targetMobileNo);
		BigDecimal total2;
		BigDecimal total3;
		total2 = customer2.getWallet().getBalance();
		total3 = customer3.getWallet().getBalance();
		total2=total2.subtract(amount);
		total3=total3.add(amount);
		customer2.setWallet(new Wallet(total2));
		customer3.setWallet(new Wallet(total3));
		return customer2;
		
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		Customer customer=repo.findOne(mobileNo);
		BigDecimal total;
		
		total = customer.getWallet().getBalance();
		total=total.add(amount);
		System.out.println(total);
		customer.setWallet(new Wallet(total));
		return customer;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		Customer customer=repo.findOne(mobileNo);
		BigDecimal total;
		
		total = customer.getWallet().getBalance();
		total=total.subtract(amount);
		System.out.println(total);
		customer.setWallet(new Wallet(total));
		return customer;
		
		
		
}
}
