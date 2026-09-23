package subscmanager.ui;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

import subscmanager.model.BillingCycle;
import subscmanager.model.Category;
import subscmanager.model.Subscription;
import subscmanager.model.UsageFrequency;
import subscmanager.repository.SubscriptionRepository;

public class ConsoleController {
	Scanner scanner = new Scanner(System.in);
	private final SubscriptionRepository repository;

	public ConsoleController(SubscriptionRepository repository) {
		this.repository = repository;
	}

	public void subscCreate() {
		System.out.println("----- サブスク新規登録 -----");

		// サービス名
		System.out.print("サービス名 ：");
		String name = scanner.nextLine();

		// カテゴリ
		System.out.println("--- カテゴリ ---");

		Category[] categories = Category.values();

		for (int i = 0; i < categories.length; i++) {
			System.out.println(i + 1 + ". " + categories[i].name());
		}

		int catIndex = selectNumber("番号 > ", categories.length);

		Category category = categories[catIndex];

		// 契約サイクル（月更新/年更新）
		System.out.println("--- 契約サイクル ---");
		System.out.println("請求サイクルを選択してください:");

		BillingCycle[] cycles = BillingCycle.values();

		for (int i = 0; i < cycles.length; i++) {
			System.out.println(i + 1 + ". " + cycles[i].name());
		}

		int cycleIndex = selectNumber("番号 > ", cycles.length);

		BillingCycle billingCycle = cycles[cycleIndex];

		// 値段
		System.out.print("料金 (円): ");
		int price;
		while (true) {
			System.out.print("料金を入力してください：");
			price = Integer.parseInt(scanner.nextLine().trim());

			if (price >= 0) {
				break;
			}

			System.out.println("料金は0以上を入力してください。");
		}

		// 次回請求日
		LocalDate nextBillingDate;
		while (true) {
			System.out.print("次回請求日 (例: 2026-10-01): ");
			String dateStr = scanner.nextLine();

			if (Subscription.isDate(dateStr)) {
				nextBillingDate = Subscription.parseDateOrNull(dateStr);
				break;
			}

			System.out.println("正確な日付を入力してください");
		}

		// 利用頻度
		System.out.println("利用頻度を選択してください:");

		UsageFrequency[] frequencies = UsageFrequency.values();

		for (int i = 0; i < frequencies.length; i++) {
			System.out.println(i + 1 + ". " + frequencies[i].name());
		}

		int freqIndex = selectNumber("番号 > ", frequencies.length);

		UsageFrequency frequency = frequencies[freqIndex];

		// idの自動生成
		String id = UUID.randomUUID().toString().substring(0, 8);

		Subscription newSub = new Subscription(
				id, name, category, billingCycle, price, nextBillingDate, frequency);

		repository.saveAll(newSub);
		System.out.println("登録が完了しました！");
	}

	private int selectNumber(String message, int max) {

		while (true) {
			System.out.print(message);

			try {
				int index = Integer.parseInt(scanner.nextLine().trim()) - 1;

				if (index >= 0 && index < max) {
					return index;
				}

				System.out.println("正しい番号を入力してください。");

			} catch (NumberFormatException e) {
				System.out.println("番号を入力してください。");
			}
		}
	}
}
