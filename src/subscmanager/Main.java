package subscmanager;

import java.util.Scanner;

import subscmanager.repository.SubscriptionRepository;
import subscmanager.service.SubscriptionService;
import subscmanager.ui.ConsoleController;
import subscmanager.ui.ConsoleView;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		SubscriptionRepository repository = new SubscriptionRepository();
		SubscriptionService service = new SubscriptionService(repository);
		ConsoleView view = new ConsoleView();

		ConsoleController controller = new ConsoleController(repository);

		System.out.println("=== サブスクリプション管理アプリへようこそ ===");
		while (true) {
			System.out.println("\n=== サブスク管理アプリ ===");
			System.out.println("1. 一覧表示 / 2. 新規登録 / 3. 更新 / 4. 解約 / 5. 登録済みサブスクの合計費用 / 6. 断捨離診断 / 0. 終了");
			System.out.print("選択 > ");

			int input;
			while (!scanner.hasNextInt()) {
				System.out.println("エラー: 数値以外の値が入力されました。");
				scanner.next();
				System.out.print("もう一度数値を入力してください: ");
			}
			input = scanner.nextInt();

			if (input == 1) {
				System.out.println("\n--- 一覧表示 ---");
				view.showSubscriptionTable(repository.findAll());
				continue;

			}
			if (input == 2) {
				controller.subscCreate();
				continue;

			}
			if (input == 3) {
				System.out.println("\n--- 更新 ---");
				System.out.print("更新したいサブスクのidを入力してください：");
				String update_id = scanner.next();
				repository.updateSubscription(update_id);
				continue;

			}
			if (input == 4) {
				System.out.println("\n--- 解約 ---");
				System.out.print("解約したいサブスクのidを入力してください：");
				String delete_id = scanner.next();
				repository.deleteSubscription(delete_id);
				continue;

			}
			if (input == 5) {
				System.out.println("\n--- 登録済みサブスクの合計費用 ---");
				System.out.println("月額費用：" + service.calculateMonthlyTotal() + "円 / 月");
				System.out.println("年額費用：" + service.calculateYearlyTotal() + "円 / 年");
				continue;

			}
			if (input == 6) {
				System.out.println("\n--- 断捨離診断 ---");
				int wastedCost = service.calculateWastedYearlyCost();
				System.out.println("使っていないサブスクによる年間ロス金額: " + wastedCost + "円");
				continue;

			}
			if (input == 0) {
				System.out.println("アプリを終了します。お疲れ様でした！");
				return;

			} else {
				System.out.println("無効な選択です。0〜6の番号を入力してください。");
				continue;
			}
		}

	}
}