package com.tay.multilevelmarketingbrokerage;

import java.util.HashSet;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static void main(String[] args) {
    	Ladder ladder = new Ladder();
		
		Member mt = new Member("T");
		mt.setCommissionCharge(21000);
		
		Member mx = new Member("X",mt);
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
		
		Market.prepare(mt, new HashSet<String>());
		
		System.out.println("Member T Commission="+Market.calculateCommission(mt,ladder)+" sub:"+mt.getDescendantsBrokerage());
		System.out.println("Member X Commission="+mx.getBrokerage()+" sub:"+mx.getDescendantsBrokerage());
		System.out.println("Member A Commission="+ma.getBrokerage()+" sub:"+ma.getDescendantsBrokerage());
		System.out.println("Member A1 Commission="+ma1.getBrokerage()+" sub:"+ma1.getDescendantsBrokerage());
		System.out.println("Member A2 Commission="+ma2.getBrokerage()+" sub:"+ma2.getDescendantsBrokerage());
		System.out.println("Member B Commission="+mb.getBrokerage()+" sub:"+mb.getDescendantsBrokerage());
		System.out.println("Member B1 Commission="+mb1.getBrokerage()+" sub:"+mb1.getDescendantsBrokerage());
		System.out.println("Member B2 Commission="+mb2.getBrokerage()+" sub:"+mb2.getDescendantsBrokerage());
	}
}
