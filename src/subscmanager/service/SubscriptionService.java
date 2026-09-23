package subscmanager.service;

import java.util.List;

import subscmanager.model.BillingCycle;
import subscmanager.model.Subscription;
import subscmanager.model.UsageFrequency;
import subscmanager.repository.SubscriptionRepository;

public class SubscriptionService {
	private final SubscriptionRepository repository;

	public SubscriptionService(SubscriptionRepository repository) {
		this.repository = repository;
	}

	// 月額費用
	public int calculateMonthlyTotal() {
		List<Subscription> subscriptions = repository.findAll();
		int monthlyTotal = 0;

		for (Subscription sub : subscriptions) {
			if (BillingCycle.月更新.equals(sub.getBillingCycle())) {
				monthlyTotal += sub.getPrice();
			}
		}
		return monthlyTotal;
	}

	// 年額費用
	public int calculateYearlyTotal() {
		List<Subscription> subscriptions = repository.findAll();
		int yearlyTotal = 0;

		for (Subscription sub : subscriptions) {
			if (BillingCycle.年更新.equals(sub.getBillingCycle())) {
				yearlyTotal += sub.getPrice();
			}
		}
		return yearlyTotal;
	}

	public int calculateWastedYearlyCost() {
		List<Subscription> subscriptions = repository.findAll();
		int totalWastedCost = 0;

		for (Subscription sub : subscriptions) {
			if (UsageFrequency.低.equals(sub.getFrequency())) {

				int yearlyPrice = 0;

				if (BillingCycle.月更新.equals(sub.getBillingCycle())) {
					yearlyPrice = sub.getPrice() * 12;
				} else {
					yearlyPrice = sub.getPrice();
				}

				totalWastedCost += yearlyPrice;
			}
		}
		return totalWastedCost;
	}

}