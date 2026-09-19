package subscmanager.ui;

import java.util.List;

import subscmanager.model.Subscription;
import subscmanager.repository.SubscriptionRepository;

public class ConsoleView {
	private final SubscriptionRepository repository = new SubscriptionRepository();

	public void showSubscriptionTable(List<Subscription> list) {
		List<Subscription> subscriptions = repository.findAll();
		if (subscriptions.isEmpty()) {
			System.out.println("登録されているサブスクリプションはありません。");
			return;
		}

		System.out.println("\n=== サブスクリプション一覧 ===");
		System.out.printf("%-4s | %-15s | %-10s | %-8s | %-8s | %-12s | %-6s%n",
				"ID", "サブスク名", "カテゴリー", "サイクル", "料金", "次回更新日", "頻度");
		System.out.println("-".repeat(75));

		for (Subscription sub : subscriptions) {
			System.out.printf("%-4s | %-15s | %-10s | %-8s | %,8d円 | %-12s | %-6s%n",
					sub.getId(),
					sub.getName(),
					sub.getCategory().name(),
					sub.getBillingCycle().name(),
					sub.getPrice(),
					sub.getNextBillingDate().toString(),
					sub.getFrequency().name());
		}
		System.out.println();
	}

	public void showError(String message) {
		System.out.println("【入力エラー】: " + message);
	}
}
