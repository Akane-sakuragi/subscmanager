package subscmanager.repository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 各Enum型をインポート
import subscmanager.model.BillingCycle;
import subscmanager.model.Category;
import subscmanager.model.Subscription;
import subscmanager.model.UsageFrequency;

public class SubscriptionRepository {
	private final String filePath = "subscriptions.csv";

	public List<Subscription> findAll() {
		List<Subscription> subscriptions = new ArrayList<>();
		Path path = Paths.get(filePath);

		if (!Files.exists(path)) {
			return subscriptions;
		}

		try {
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

			for (int i = 1; i < lines.size(); i++) {
				String line = lines.get(i);
				if (line.trim().isEmpty()) {
					continue;
				}

				String[] values = line.split(",");
				if (values.length >= 7) {
					String id = values[0];
					String name = values[1];

					//  文字列から各 Enum 型へ変換
					Category category = Category.valueOf(values[2]);
					BillingCycle billingCycle = BillingCycle.valueOf(values[3]);
					int price = Integer.parseInt(values[4]);
					LocalDate nextBillingDate = LocalDate.parse(values[5]);
					UsageFrequency frequency = UsageFrequency.valueOf(values[6]);

					subscriptions.add(new Subscription(
							id, name, category, billingCycle, price, nextBillingDate, frequency));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return subscriptions;
	}

	public void saveAll(Subscription subscription) {
		List<Subscription> subscriptions = findAll();

		subscriptions.add(subscription);

		List<String> lines = new ArrayList<>();

		lines.add("id,name,category,billingCycle,price,nextBillingDate,frequency");

		for (Subscription sub : subscriptions) {
			String line = String.join(",",
					sub.getId(),
					sub.getName(),
					sub.getCategory().name(),
					sub.getBillingCycle().name(),
					String.valueOf(sub.getPrice()),
					sub.getNextBillingDate().toString(),
					sub.getFrequency().name());
			lines.add(line);
		}

		Path path = Paths.get(filePath);

		try {
			Files.write(path, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteSubscription(String delete_id) {
		List<Subscription> subscriptions = findAll();
		List<Subscription> NotDeleteSubscription = new ArrayList<>();

		for (Subscription subscription : subscriptions) {
			if (!subscription.getId().equals(delete_id)) {
				NotDeleteSubscription.add(subscription);
			} else {
				System.out.println(subscription.getName() + "を削除しました");
			}
		}

		List<String> lines = new ArrayList<>();

		lines.add("id,name,category,billingCycle,price,nextBillingDate,frequency");

		for (Subscription sub : NotDeleteSubscription) {
			String line = String.join(",",
					sub.getId(),
					sub.getName(),
					sub.getCategory().name(),
					sub.getBillingCycle().name(),
					String.valueOf(sub.getPrice()),
					sub.getNextBillingDate().toString(),
					sub.getFrequency().name());
			lines.add(line);
		}

		Path path = Paths.get(filePath);
		try {
			Files.write(path, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateSubscription(String update_id) {
		List<Subscription> subscriptions = findAll();
		boolean isFound = false;

		for (Subscription sub : subscriptions) {
			if (sub.getId().equals(update_id)) {
				isFound = true;
				break;
			}
		}

		if (!isFound) {
			System.out.println("ID: " + update_id + " のサブスクは見つかりませんでした。");
			return;
		}

		Scanner scanner = new Scanner(System.in);
		System.out.println("新しい情報を入力してください：");

		System.out.print("サービス名: ");
		String name = scanner.next();

		System.out.print("カテゴリ (趣味/仕事/生活/その他): ");
		Category category = Category.valueOf(scanner.next());

		System.out.print("請求サイクル (月更新/年更新): ");
		BillingCycle billingCycle = BillingCycle.valueOf(scanner.next());

		System.out.print("金額: ");
		int price = scanner.nextInt();

		System.out.print("次回請求日 (例: 2026-10-01): ");
		LocalDate nextBillingDate = LocalDate.parse(scanner.next());

		System.out.print("利用頻度 (高/中/低): ");
		UsageFrequency frequency = UsageFrequency.valueOf(scanner.next());

		Subscription updated = new Subscription(
				update_id, name, category, billingCycle, price, nextBillingDate, frequency);

		List<Subscription> newSubscriptions = new ArrayList<>();
		for (Subscription subscription : subscriptions) {
			if (subscription.getId().equals(update_id)) {
				newSubscriptions.add(updated);
			} else {
				newSubscriptions.add(subscription);
			}
		}

		List<String> lines = new ArrayList<>();
		lines.add("id,name,category,billingCycle,price,nextBillingDate,frequency");

		for (Subscription sub : newSubscriptions) {
			String line = String.join(",",
					sub.getId(),
					sub.getName(),
					sub.getCategory().name(),
					sub.getBillingCycle().name(),
					String.valueOf(sub.getPrice()),
					sub.getNextBillingDate().toString(),
					sub.getFrequency().name());
			lines.add(line);
		}

		Path path = Paths.get(filePath);
		try {
			Files.write(path, lines, Charset.forName("UTF-8"));
			System.out.println("ID: " + update_id + " のデータを更新しました。");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}