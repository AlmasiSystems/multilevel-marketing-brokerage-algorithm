package com.tay.multilevelmarketingbrokerage;

import java.util.HashSet;

public class Market {
	
	public static final double CommissionChargeBaseRate = 2.0/3;
	
	
	public static void prepare(Member member, HashSet<String> set) {
		if(set.contains(member.getId())) {
			throw new RuntimeException("The member tree includes circle!");
		}
		else {
			set.add(member.getId());
		}
		if(member.getChildren().size() == 0) {
			member.setSubTreeCommissionCharge(member.getCommissionCharge());
			return;
		}
		else {
			member.setSubTreeCommissionCharge(member.getCommissionCharge());
			for(Member child : member.getChildren()) {
				prepare(child, set);
				member.addSubTreeCommissionCharge(child.getSubTreeCommissionCharge());
			}
		}
	}
	
	
	public static double calculateCommission(Member member, Ladder ladder) {
		double val = 0;
		double commissionChargeBase =  member.getSubTreeCommissionCharge() * CommissionChargeBaseRate;
		if(member.getChildren().size() == 0) {
			val = ladder.calculateBrokerage(commissionChargeBase);
			member.setBrokerage(val);
			if(member.getParent() != null) {
				member.getParent().addDescendantsBrokerage(val);
			}
			return val;
		}
		else {
			double totalBrokerage = ladder.calculateBrokerage(commissionChargeBase);
			double memberBrokerage = totalBrokerage;
			for(Member child : member.getChildren()) {
				double childBrokerage = calculateCommission(child, ladder);
				memberBrokerage = memberBrokerage - childBrokerage - child.getDescendantsBrokerage();
			}
			member.setBrokerage(memberBrokerage);
			if(member.getParent() != null) {
				member.getParent().addDescendantsBrokerage(memberBrokerage+member.getDescendantsBrokerage());
			}
			return memberBrokerage;
		}
	}
	public static void main(String[] args) {
		Ladder ladder = new Ladder();
		
		Member mt = new Member("T");
		mt.setCommissionCharge(21000);
		
		Member mx = new Member("X",mt);
//		Member mx = new Member("X");
		mx.setCommissionCharge(17000);
		mt.addChild(mx);
		
		Member ma = new Member("A", mx);
		ma.setCommissionCharge(42000);
		mx.addChild(ma);
		
		Member mb = new Member("B", mx);
		mb.setCommissionCharge(69000);		
		mx.addChild(mb);
		
		Member ma1 = new Member("A1",ma);
		ma1.setCommissionCharge(54000);
		ma.addChild(ma1);
		
		Member ma2 = new Member("A2",ma);
		ma2.setCommissionCharge(27000);
		ma.addChild(ma2);
		
		Member mb1 = new Member("B1",mb);
		mb1.setCommissionCharge(54000);
		mb.addChild(mb1);
		
		Member mb2 = new Member("B2",mb);
		mb2.setCommissionCharge(27000);		
		mb.addChild(mb2);
		
		prepare(mt, new HashSet<String>());
		
		System.out.println("Member T Commission="+calculateCommission(mt,ladder)+" sub:"+mt.getDescendantsBrokerage());
		System.out.println("Member X Commission="+mx.getBrokerage()+" sub:"+mx.getDescendantsBrokerage());
		System.out.println("Member A Commission="+ma.getBrokerage()+" sub:"+ma.getDescendantsBrokerage());
		System.out.println("Member A1 Commission="+ma1.getBrokerage()+" sub:"+ma1.getDescendantsBrokerage());
		System.out.println("Member A2 Commission="+ma2.getBrokerage()+" sub:"+ma2.getDescendantsBrokerage());
		System.out.println("Member B Commission="+mb.getBrokerage()+" sub:"+mb.getDescendantsBrokerage());
		System.out.println("Member B1 Commission="+mb1.getBrokerage()+" sub:"+mb1.getDescendantsBrokerage());
		System.out.println("Member B2 Commission="+mb2.getBrokerage()+" sub:"+mb2.getDescendantsBrokerage());
	}
}
