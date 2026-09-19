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
		//		, service, view

		System.out.println("=== サブスクリプション管理アプリへようこそ ===");
		while (true) {
			System.out.println("\n=== サブスク管理アプリ ===");
			System.out.println("1. 一覧表示 / 2. 新規登録 / 3. 更新・解約 / 4. 登録済みサブスクの合計費用 / 5. 断捨離診断 / 0. 終了");
			System.out.print("選択 > ");

			String input = scanner.nextLine().trim();

			switch (input) {
			case "1":
				System.out.println("\n--- 一覧表示 ---");
				view.showSubscriptionTable(repository.findAll());
				break;

			case "2":
				controller.subscCreate();
				break;

			case "3":
				System.out.println("\n--- 更新・解約 ---");
				// TODO: 更新・解約の処理を呼び出す
				break;

			case "4":
				System.out.println("\n--- 登録済みサブスクの合計費用 ---");
				System.out.println("月額費用：" + service.calculateMonthlyTotal() + "円");
				System.out.println("年額費用：" + service.calculateYearlyTotal() + "円");
				break;

			case "5":
				System.out.println("\n--- 断捨離診断 ---");
				int wastedCost = service.calculateWastedYearlyCost();
				System.out.println("使っていないサブスクによる年間ロス金額: " + wastedCost + "円");
				break;

			case "0":
				System.out.println("アプリを終了します。お疲れ様でした！");
				return;

			default:
				System.out.println("無効な選択です。0〜4の番号を入力してください。");
				break;
			}
			scanner.close();
		}
	}
}